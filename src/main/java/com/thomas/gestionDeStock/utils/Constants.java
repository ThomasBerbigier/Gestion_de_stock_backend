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

}
