package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.CategorieDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.exception.InvalidOperationException;
import com.thomas.gestionDeStock.model.Article;
import com.thomas.gestionDeStock.repository.ArticleRepository;
import com.thomas.gestionDeStock.repository.CategorieRepository;
import com.thomas.gestionDeStock.services.CategorieService;
import com.thomas.gestionDeStock.validator.CategorieValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CategorieServiceImpl(
            CategorieRepository categorieRepository, ArticleRepository articleRepository
    ) {
        this.categorieRepository = categorieRepository;
        this.articleRepository = articleRepository;
    }


    @Override
    public CategorieDto save(CategorieDto categorieDto) {

        List<String> errors = CategorieValidator.validate(categorieDto);
        if (!errors.isEmpty()) {
            log.error("Catégorie invalide {}", categorieDto);
            throw new InvalidEntityException("La catégorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategorieDto.fromEntity(
                categorieRepository.save(
                        CategorieDto.toEntity(categorieDto)
                )
        );
    }

    @Override
    public CategorieDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Le CODE catégorie est nul");
            return null;
        }
        return categorieRepository.findCategorieByCode(code)
                .map(CategorieDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune catégorie avec le CODE = " + code + " n'a été trouvée dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
            );
    }

    @Override
    public CategorieDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID catégorie est nul");
            return null;
        }
        return categorieRepository.findById(id)
                .map(CategorieDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune catégorie avec l'ID = " + id + " n'a été trouvée dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    @Override
    public List<CategorieDto> findAll() {
        return categorieRepository.findAll().stream()
                .map(CategorieDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de la catégorie à supprimer est nul");
            return;
        }
        List<Article> articles = articleRepository.findAllByCategorieId(id);
        if (!articles.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une catégorie présente dans des commandes clients ou fournisseurs",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categorieRepository.deleteById(id);
    }

}
