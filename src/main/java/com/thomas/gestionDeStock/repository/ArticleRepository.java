package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    /**
     * Recherche un article par son code unique.
     *
     * @param codeArticle le code unique de l'article à rechercher.
     * @return un Optional contenant l'article s'il est trouvé, ou un Optional vide sinon.
     */
    Optional<Article> findArticleByCodeArticle(String codeArticle);

    List<Article> findAllByCategorieId(Integer idCategorie);
}
