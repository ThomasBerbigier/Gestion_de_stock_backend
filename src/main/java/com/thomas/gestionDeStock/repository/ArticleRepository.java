package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {



}
