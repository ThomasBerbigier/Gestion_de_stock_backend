package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.ChangerMotDePasseUtilisateurDto;
import com.thomas.gestionDeStock.dto.UtilisateurDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.exception.InvalidOperationException;
import com.thomas.gestionDeStock.model.Utilisateur;
import com.thomas.gestionDeStock.repository.UtilisateurRepository;
import com.thomas.gestionDeStock.services.UtilisateurService;
import com.thomas.gestionDeStock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(
            UtilisateurRepository utilisateurRepository
    ) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {
        List<String> errors = UtilisateurValidator.validate(utilisateurDto);
        if (!errors.isEmpty()) {
            log.error("Utilisateur is not valid {}", utilisateurDto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDto.toEntity(utilisateurDto)
                )
        );
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null) {
            log.error("Utilisateur ID is null");
            return null;
        }
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID = " + id + " n'a été trouvé dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Utilisateur ID is null");
            return;
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'email = " + email + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()) {
            log.error("Aucun utilisateur n'a été trouvé avec l'ID = " + dto.getId());
            throw new EntityNotFoundException("Aucun utilisateur n'a été trouvé avec l'ID = " + dto.getId(),
                    ErrorCodes.UTILISATEUR_NOT_FOUND);
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(dto.getMotDePasse());
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }
    private void validate(ChangerMotDePasseUtilisateurDto dto) {
        if (dto == null) {
            log.error("Impossible de modifier le mot de passe ayant un objet nul");
            throw new InvalidOperationException("Aucune information n'a été fournie pour changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if(dto.getId() == null) {
            log.error("Impossible de modifier le mot de passe ayant un ID nul");
            throw new InvalidOperationException("ID utilisateur nul, impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if(!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
            log.error("Impossible de modifier un mot de passe nul");
            throw new InvalidOperationException("Mot de passe utilisateur nul",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if(!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
            log.error("Impossible de modifier le mot de passe car différent");
            throw new InvalidOperationException("Mot de passe utilisateur non conforme",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }
}
