package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.CategorieDto;

import java.util.List;

public interface CategorieService {

    CategorieDto save(CategorieDto categorieDto);

    CategorieDto findByCode(String code);

    CategorieDto findById(Integer id);

    List<CategorieDto> findAll();

    void delete(Integer id);
}
