package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.ArticleDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.model.Article;
import com.thomas.gestionDeStock.repository.ArticleRepository;
import com.thomas.gestionDeStock.services.ArticleService;
import com.thomas.gestionDeStock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//
//Implémentation du service pour la gestion des articles.
//Cette classe fournit la logique métier pour les opérations CRUD sur les articles.
//
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    // Injection du repository via le constructeur pour suivre les bonnes pratiques de Spring
    @Autowired
    public ArticleServiceImpl(
            ArticleRepository articleRepository
    ) {
        this.articleRepository = articleRepository;
    }

    // Méthode pour sauvegarder un article
    @Override
    public ArticleDto save(ArticleDto articleDto) {

        // Validation de l'article avec un validateur custom
        List<String> errors = ArticleValidator.validate(articleDto);
        if(!errors.isEmpty()) {
            log.error("Article invalide {}", articleDto);
            // Lancer une exception personnalisée si l'article n'est pas valide
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        // Sauvegarder l'entité et retourner un DTO
        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(articleDto)
                )
        );
    }
    // Méthode pour trouver un article par son ID
    @Override
    public ArticleDto findById(Integer id) {
        if(id == null) {
            log.error("Article Id n'existe pas ");
            return null;
        }
        // Utilisation de Optional pour gérer le résultat de la recherche
        Optional<Article> article = articleRepository.findById(id);

        // Si l'article est trouvé, le convertir en DTO, sinon lancer une exception
        return article.map(ArticleDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec l'ID = " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }
    // Méthode pour trouver un article par son code
    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)) {
            log.error("Article CODE n'existe pas ");
            return null;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);

        // Si l'article est trouvé, le convertir en DTO, sinon lancer une exception
        return article.map(ArticleDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec le CODE = " + codeArticle + "n'a été trouvé dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }
    // Méthode pour récupérer la liste de tous les articles
    @Override
    public List<ArticleDto> findAll() {
        // Récupérer la liste des articles, les convertir en DTO, puis les collecter dans une liste
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }
    // Méthode pour supprimer un article par son ID
    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("\"L'identifiant de l'article à supprimer est nul");
            return;
        }
        articleRepository.deleteById(id);
    }
}
