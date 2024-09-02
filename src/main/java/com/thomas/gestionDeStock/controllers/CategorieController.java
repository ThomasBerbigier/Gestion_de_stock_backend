package com.thomas.gestionDeStock.controllers;

import com.thomas.gestionDeStock.controllers.api.CategorieApi;
import com.thomas.gestionDeStock.dto.CategorieDto;
import com.thomas.gestionDeStock.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategorieController implements CategorieApi {

    @Autowired
    private CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public CategorieDto save(CategorieDto categorieDto) {
        return categorieService.save(categorieDto);
    }

    @Override
    public CategorieDto findByCode(String codeCategorie) {
        return categorieService.findByCode(codeCategorie);
    }

    @Override
    public CategorieDto findById(Integer idCategorie) {
        return categorieService.findById(idCategorie);
    }

    @Override
    public List<CategorieDto> findAll() {
        return categorieService.findAll();
    }

    @Override
    public void delete(Integer id) {
        categorieService.delete(id);
    }
}
