package com.thomas.gestionDeStock.services.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.thomas.gestionDeStock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.InputStream;

// Annotation pour indiquer que cette classe est un service Spring et pour activer le logging avec SLF4J
@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {

    // Déclaration d'une variable pour stocker l'instance de Flickr
    private final Flickr flickr;

    // Injection de dépendance de l'instance Flickr via le constructeur
    @Autowired
    public FlickrServiceImpl(Flickr flickr) {
        this.flickr = flickr;
    }

    // Implémentation de la méthode savePhoto pour sauvegarder une photo sur Flickr
    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {

        // Création d'un objet UploadMetaData pour définir les métadonnées de la photo, ici le titre
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);

        // Téléchargement de la photo sur Flickr et récupération de l'ID de la photo téléchargée
        String photoId = flickr.getUploader().upload(photo, uploadMetaData);

        // Récupération de l'URL de la photo en utilisant l'ID de la photo et renvoi de cette URL
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }

}
