package com.thomas.gestionDeStock.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Configuration
public class FlickrConfiguration {

    // Injection des valeurs des propriétés API dans les variables correspondantes
    @Value("${flickr.apiKey}")
    private String apiKey;

    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.appKey}")
    private String appKey;

    @Value("${flickr.appSecret}")
    private String appSecret;

    // Code commenté : Cette méthode était utilisée pour configurer et initialiser une connexion à l'API Flickr en utilisant l'authentification OAuth.
    // Elle demandait à l'utilisateur de visiter une URL d'autorisation, d'entrer un code fourni par Flickr, puis de l'utiliser pour obtenir un token d'accès.
    // Ce code est désactivé (commenté) car une autre méthode est maintenant utilisée.

    /** //@Bean
    public Flickr getFlickr() throws IOException, ExecutionException, InterruptedException, FlickrException {
        // Initialisation de Flickr avec les clés API
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());

        // Création du service OAuth avec les permissions nécessaires
        OAuth10aService service = new ServiceBuilder(apiKey)
                .apiSecret(apiSecret)
                .build(FlickrApi.instance(FlickrApi.FlickrPerm.DELETE));

        // Scanner pour lire les entrées utilisateur
        final Scanner scanner = new Scanner(System.in);

        // Obtenir le token de demande (Request Token)
        OAuth1RequestToken requestToken = service.getRequestToken();

        // Générer l'URL d'autorisation
        final String authUrl = service.getAuthorizationUrl(requestToken);

        // Afficher l'URL d'autorisation pour que l'utilisateur puisse l'utiliser
        System.out.println("Visitez cette URL pour autoriser l'application : " + authUrl);
        System.out.println("Collez ici le code fourni par Flickr après autorisation >> ");

        // Lire le code d'autorisation entré par l'utilisateur
        final String authVerifier = scanner.nextLine();

        // Obtenir le token d'accès à partir du code d'autorisation
        OAuth1AccessToken accessToken = service.getAccessToken(requestToken, authVerifier);

        // Afficher les tokens obtenus pour vérification
        System.out.println("Token: " + accessToken.getToken());
        System.out.println("Token Secret: " + accessToken.getTokenSecret());

        // Vérifier le token et obtenir les informations d'authentification
        Auth auth = flickr.getAuthInterface().checkToken(accessToken);

        // Afficher les informations d'authentification obtenues
        System.out.println("--------------------------------------");
        System.out.println("Token: " + auth.getToken());
        System.out.println("Token Secret: " + auth.getTokenSecret());

        // Retourner l'instance Flickr avec les permissions accordées
        return flickr;
    }*/

    // Cette méthode configure et initialise une instance de l'API Flickr avec des informations d'authentification prédéfinies.
  @Bean
  public Flickr getFlickr() {
      // Création d'une instance de Flickr avec les clés API
      Flickr flickr = new Flickr(apiKey, apiSecret, new REST());

      // Création d'une instance d'authentification et définition des permissions
      Auth auth = new Auth();
      auth.setPermission(Permission.DELETE);

      // Définition des tokens d'accès pour l'authentification
      auth.setToken(appKey);
      auth.setToken(appSecret);

      // Configuration du contexte de la requête avec les informations d'authentification
      RequestContext requestContext = RequestContext.getRequestContext();
      requestContext.setAuth(auth);

      // Retourner l'instance Flickr configurée
      flickr.setAuth(auth);

      return flickr;
  }
}
