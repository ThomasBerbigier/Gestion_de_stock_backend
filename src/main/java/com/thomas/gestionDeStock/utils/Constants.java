package com.thomas.gestionDeStock.utils;

/**
 * Interface contenant les constantes utilisées dans l'application.
 * Cette interface est utilisée pour centraliser les valeurs constantes qui sont
 * utilisées à travers l'application, telles que les chemins d'URL pour les endpoints API.
 * Les constantes définies ici sont utilisées pour éviter les chaînes de caractères en dur
 * et garantir une uniformité dans l'application.
 */
public interface Constants {

    /**
     * La racine des chemins d'URL pour les endpoints API de la version 1 de l'application.
     * Ce chemin est utilisé pour définir les préfixes des URL dans les contrôleurs et
     * les appels d'API, ce qui facilite la gestion des versions et des chemins d'API.
     */
    String APP_ROOT = "gestiondestock/v1";

    String COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT + "/commandesfournisseurs";
    String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/create";
    String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{idCommandeFournisseur}";
    String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{codeCommandeFournisseur}";
    String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idCommandeFournisseur}";

    String ENTREPRISE_ENDPOINT = APP_ROOT + "/entreprises";
    String CREATE_ENTREPRISE_ENDPOINT = ENTREPRISE_ENDPOINT + "/create";
    String FIND_ENTREPRISE_BY_ID_ENDPOINT = ENTREPRISE_ENDPOINT + "/{idEntreprise}";
    String FIND_ALL_ENTREPRISE_ENDPOINT = ENTREPRISE_ENDPOINT + "/all";
    String DELETE_ENTREPRISE_ENDPOINT = ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}";

    String FOURNISSEUR_ENDPOINT = APP_ROOT + "/fournisseurs";
    String CREATE_FOURNISSEUR_ENDPOINT = FOURNISSEUR_ENDPOINT + "/create";
    String FIND_FOURNISSEUR_BY_ID_ENDPOINT = FOURNISSEUR_ENDPOINT + "/{idFournisseur}";
    String FIND_ALL_FOURNISSEUR_ENDPOINT = FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_FOURNISSEUR_ENDPOINT = FOURNISSEUR_ENDPOINT + "/delete/{idFournisseur}";

    String UTILISATEUR_ENDPOINT = APP_ROOT + "/utilisateurs";
    String CREATE_UTILISATEUR_ENDPOINT = UTILISATEUR_ENDPOINT + "/create";
    String FIND_UTILISATEUR_BY_ID_ENDPOINT = UTILISATEUR_ENDPOINT + "/{idUtilisateur}";
    String FIND_ALL_UTILISATEUR_ENDPOINT = UTILISATEUR_ENDPOINT + "/all";
    String DELETE_UTILISATEUR_ENDPOINT = UTILISATEUR_ENDPOINT + "/delete/{idUtilisateur}";

    String VENTES_ENDPOINT = APP_ROOT + "/ventes";
    String CREATE_VENTES_ENDPOINT = VENTES_ENDPOINT + "/create";
    String FIND_VENTES_BY_ID_ENDPOINT = VENTES_ENDPOINT + "/{idVentes}";
    String FIND_VENTES_BY_CODE_ENDPOINT = VENTES_ENDPOINT + "/{codeVentes}";
    String FIND_ALL_VENTES_ENDPOINT = VENTES_ENDPOINT + "/all";
    String DELETE_VENTES_ENDPOINT = VENTES_ENDPOINT + "/delete/{idVentes}";
}
