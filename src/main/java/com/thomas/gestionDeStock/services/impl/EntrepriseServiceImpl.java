package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.EntrepriseDto;
import com.thomas.gestionDeStock.dto.RolesDto;
import com.thomas.gestionDeStock.dto.UtilisateurDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.repository.EntrepriseRepository;
import com.thomas.gestionDeStock.repository.RolesRepository;
import com.thomas.gestionDeStock.services.EntrepriseService;

import com.thomas.gestionDeStock.services.UtilisateurService;
import com.thomas.gestionDeStock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository,
                                 UtilisateurService utilisateurService,
                                 RolesRepository rolesRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {

        List<String> errors = EntrepriseValidator.validate(entrepriseDto);
        if (!errors.isEmpty()) {
            log.error("Entreprise is not valid {}", entrepriseDto);
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(entrepriseDto))
        );

        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);

        UtilisateurDto savedUser = utilisateurService.save(utilisateur);

        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();

        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return  savedEntreprise;
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto entrepriseDto) {
        return UtilisateurDto.builder()
                .adresse(entrepriseDto.getAdresse())
                .nom(entrepriseDto.getNom())
                .prenom(entrepriseDto.getCodeFiscal())
                .email(entrepriseDto.getEmail())
                .motDePasse(generateRandomPassword())
                .entreprise(entrepriseDto)
                .dateDeNaissance(Instant.now())
                .photo(entrepriseDto.getPhoto())
                .build();
    }

    private String generateRandomPassword() {
        return "som3R@nd0mP@$$word";
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return null;
        }
        return entrepriseRepository.findById(id)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
                );
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}
