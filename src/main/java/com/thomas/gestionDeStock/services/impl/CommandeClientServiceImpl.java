package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.CommandeClientDto;
import com.thomas.gestionDeStock.dto.LigneCommandeClientDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.model.Article;
import com.thomas.gestionDeStock.model.Client;
import com.thomas.gestionDeStock.model.CommandeClient;
import com.thomas.gestionDeStock.model.LigneCommandeClient;
import com.thomas.gestionDeStock.repository.ArticleRepository;
import com.thomas.gestionDeStock.repository.ClientRepository;
import com.thomas.gestionDeStock.repository.CommandeClientRepository;
import com.thomas.gestionDeStock.repository.LigneCommandeClientRepository;
import com.thomas.gestionDeStock.services.CommandeClientService;
import com.thomas.gestionDeStock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Implémentation du service pour gérer les opérations liées aux commandes clients.
 * Cette classe gère la création, la recherche, la liste et la suppression des commandes clients,
 * tout en effectuant des validations des données d'entrée et des vérifications de cohérence.
 *<p>
 * Les erreurs rencontrées durant les opérations, telles que les entités non trouvées ou les entités invalides,
 * déclenchent des exceptions personnalisées pour gérer les cas d'erreurs de manière appropriée.
 *</p>
 * Cette classe repose sur des repositories pour l'accès aux données :
 * - CommandeClientRepository : Pour les opérations CRUD sur les commandes clients.
 * - LigneCommandeClientRepository : Pour gérer les lignes des commandes clients.
 * - ClientRepository : Pour vérifier l'existence des clients liés aux commandes.
 * - ArticleRepository : Pour vérifier l'existence des articles associés aux commandes.
 *<p>
 * La gestion des logs est assurée par l'annotation Lombok @Slf4j.
 *<p>
 * Cette classe est annotée avec @Service pour indiquer qu'elle est un composant de service dans le contexte Spring.
 */
@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    private final CommandeClientRepository commandeClientRepository;
    private final LigneCommandeClientRepository ligneCommandeClientRepository;
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;

    /**
     * Constructeur avec injection des dépendances.
     * @param commandeClientRepository Repository pour la gestion des commandes clients.
     * @param clientRepository Repository pour la gestion des clients.
     * @param articleRepository Repository pour la gestion des articles.
     * @param ligneCommandeClientRepository Repository pour la gestion des lignes de commande client.
     */
    @Autowired
    public CommandeClientServiceImpl(
            CommandeClientRepository commandeClientRepository,
            ClientRepository clientRepository,
            ArticleRepository articleRepository,
            LigneCommandeClientRepository ligneCommandeClientRepository
    ) {
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
    }

    /**
     * Enregistre une nouvelle commande client après validation des données.
     * @param commandeClientDto Le DTO de la commande client à enregistrer.
     * @return Le DTO de la commande client enregistrée.
     * @throws InvalidEntityException Si la commande client ou ses articles ne sont pas valides.
     * @throws EntityNotFoundException Si le client associé à la commande n'existe pas.
     */
    @Override
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {
        // Validation des données de la commande client
        List<String> errors = CommandeClientValidator.validate(commandeClientDto);
        if(!errors.isEmpty()) {
            log.error("Cette commande client n'est pas valide");
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }
        // Vérification de l'existence du client associé à la commande
        Optional<Client> client = clientRepository.findById(commandeClientDto.getClient().getId()); // Vérifier si clientDto si erreur
        if(client.isEmpty()) {
            log.warn("Ce client avec l'ID {} n'existe pas", commandeClientDto.getClient().getId());
            throw new EntityNotFoundException("Aucun client ayant l'ID" + commandeClientDto.getClient().getId() + " n'a été trouvé dans la BDD", ErrorCodes.CLIENT_NOT_FOUND);
        }
        // Vérification de l'existence des articles associés aux lignes de commande
        List<String> articleErrors = new ArrayList<>();

        if(commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(ligneCmdClt -> {
                if(ligneCmdClt.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligneCmdClt.getArticle().getId());
                    if(article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligneCmdClt.getArticle().getId() + "n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enregistrer la commande avec un article nul");
                }
            });
        }
        if(!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("L'article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }
        // Enregistrement de la commande client et des lignes de commande associées
        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));

        if(commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(lignCmdClt -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(lignCmdClt);
                ligneCommandeClient.setCommandeClient(savedCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);
            });
        }
        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    /**
     * Recherche une commande client par son code.
     * @param code Le code de la commande client.
     * @return Le DTO de la commande client trouvée.
     * @throws EntityNotFoundException Si aucune commande client avec le code donné n'est trouvée.
     */
    @Override
    public CommandeClientDto findByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            log.error("Le code de la commande client n'existe pas");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client avec le code " + code + " n'a été trouvée",
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    /**
     * Recherche une commande client par son identifiant.
     * @param id L'identifiant de la commande client.
     * @return Le DTO de la commande client trouvée.
     * @throws EntityNotFoundException Si aucune commande client avec l'identifiant donné n'est trouvée.
     */
    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null) {
            log.error("L'id de la commande client n'existe pas");
            return null;
        }

        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client avec l'id " + id + " n'a été trouvée",
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    /**
     * Récupère la liste de toutes les commandes clients.
     * @return La liste des DTO de toutes les commandes clients.
     */
    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une commande client par son identifiant.
     * @param id L'identifiant de la commande client à supprimer.
     */
    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("L'id de la commande client n'existe pas");
            return;
        }
        commandeClientRepository.deleteById(id);
    }
}
