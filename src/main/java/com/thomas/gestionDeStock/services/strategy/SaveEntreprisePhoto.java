package com.thomas.gestionDeStock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.thomas.gestionDeStock.dto.EntrepriseDto;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidOperationException;
import com.thomas.gestionDeStock.services.EntrepriseService;
import com.thomas.gestionDeStock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

    private FlickrService flickrService;
    private EntrepriseService entrepriseService;

    @Autowired
    public SaveEntreprisePhoto(FlickrService flickrService,
                               EntrepriseService entrepriseService)
    {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entreprise = entrepriseService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'entreprise",
                    ErrorCodes.SAVE_PHOTO_EXCEPTION);
        }
        entreprise.setPhoto(urlPhoto);
        return entrepriseService.save(entreprise);
    }
}
