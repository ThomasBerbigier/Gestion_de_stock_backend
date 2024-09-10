package com.thomas.gestionDeStock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.thomas.gestionDeStock.dto.UtilisateurDto;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidOperationException;
import com.thomas.gestionDeStock.services.FlickrService;
import com.thomas.gestionDeStock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto> {

    private FlickrService flickrService;
    private UtilisateurService utilisateurService;

    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'utilisateur",
                    ErrorCodes.SAVE_PHOTO_EXCEPTION);
        }
        utilisateur.setPhoto(urlPhoto);
        return utilisateurService.save(utilisateur);
    }
}
