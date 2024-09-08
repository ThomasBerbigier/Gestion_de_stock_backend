package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.CategorieDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.services.CategorieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategorieServiceImplTest {

    @Autowired
    private CategorieService service;

    @Test
    public void shouldSaveCategorieWithSuccess() {
        CategorieDto expectedCategorie = CategorieDto.builder()
                .code("Cat test")
                .designation("Designation test")
                .idEntreprise(1)
                .build();

        CategorieDto savedCategorie = service.save(expectedCategorie);

        assertNotNull(savedCategorie);
        assertNotNull(savedCategorie.getId());
        assertEquals(expectedCategorie.getCode(), savedCategorie.getCode());
        assertEquals(expectedCategorie.getDesignation(), savedCategorie.getDesignation());
        assertEquals(expectedCategorie.getIdEntreprise(), savedCategorie.getIdEntreprise());
    }

    @Test
    public void shouldUpdateCategorieWithSuccess() {
        CategorieDto expectedCategorie = CategorieDto.builder()
                .code("Cat test")
                .designation("Designation test")
                .idEntreprise(1)
                .build();

        CategorieDto savedCategorie = service.save(expectedCategorie);

        CategorieDto categorieToUpdate = savedCategorie;
        categorieToUpdate.setCode("Cat update");

        savedCategorie = service.save(categorieToUpdate);

        assertNotNull(categorieToUpdate);
        assertNotNull(categorieToUpdate.getId());
        assertEquals(categorieToUpdate.getCode(), savedCategorie.getCode());
        assertEquals(categorieToUpdate.getDesignation(), savedCategorie.getDesignation());
        assertEquals(categorieToUpdate.getIdEntreprise(), savedCategorie.getIdEntreprise());
    }

    @Test
    public void shouldThrowInvalidEntityException() {
        CategorieDto expectedCategorie = CategorieDto.builder().build();

        InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, () -> service.save(expectedCategorie));

        assertEquals(ErrorCodes.CATEGORY_NOT_VALID, expectedException.getErrorCode());
        assertEquals(1, expectedException.getErrors().size());
        assertEquals("Veuillez renseigner le code de la catégorie", expectedException.getErrors().get(0));
    }

    @Test
    public void shouldThrowEntityNotFoundException() {

        EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> service.findById(0));

        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCode());
        assertEquals("Aucune catégorie avec l'ID = 0 n'a été trouvée dans la BDD", expectedException.getMessage());
    }

    @Test
    public void shouldThrowEntityNotFoundException2() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(0));
    }
}