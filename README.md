# 🗃️ Gestion de Stock - Backend

## Description
Ce projet est le back-end de l'application de gestion de stock, développé avec **Spring Boot** et exposant une API REST documentée via **OpenAPI**. Le système permet de gérer les profils d'entreprise, les catégories d'articles, les mouvements de stock, ainsi que les commandes clients et fournisseurs. L'authentification et la sécurité sont gérées par **Spring Security** et **JWT**, assurant un accès sécurisé aux données sensibles.

## Stack Technique

- **Backend** :  
  ![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
  ![OpenAPI](https://img.shields.io/badge/OpenAPI-85EA2D?style=for-the-badge&logo=openapi-initiative&logoColor=black)

- **Sécurité** :  
  ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
  ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

- **Base de données** :  
  ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

## Fonctionnalités Principales

- **Gestion des entreprises** : Créer et gérer des profils d'entreprise.
- **Gestion des articles et des stocks** : Suivi des catégories et mouvements de stock.
- **Commandes clients et fournisseurs** : Création et gestion des commandes.
- **Gestion des utilisateurs et des rôles** : Contrôle d'accès basé sur des rôles.
- **Documentation de l'API** : Documentation dynamique avec **Swagger** via OpenAPI.

## Prérequis

- **Java 21** : Pour exécuter le projet Spring Boot.
- **Maven** : Pour gérer les dépendances et la construction du projet.
- **MariaDB** : Base de données relationnelle pour stocker les informations.

## Installation et Configuration

1. **Cloner le dépôt** :
   - Clonez le dépôt GitHub sur votre machine locale avec la commande suivante :
     ```bash
     git clone https://github.com/ThomasBerbigier/Gestion_de_stock_backend
     ```

2. **Configurer la base de données MariaDB** :
   - Créez une base de données MariaDB pour l'application. Vous pouvez utiliser phpMyAdmin ou la ligne de commande MariaDB.
   - Configurez les informations d'accès à la base de données dans le fichier `application.yml` (répertoire `src/main/resources`). Exemple de configuration :
     
     ```yaml
     spring:
       datasource:
         url: jdbc:mariadb://localhost:3306/votre_base
         username: votre_utilisateur
         password: votre_mot_de_passe
       jpa:
         hibernate:
           ddl-auto: update
         show-sql: true
     ```

3. **Construire le projet avec Maven** :
   - Accédez au répertoire du projet cloné, puis exécutez la commande Maven suivante pour construire l'application :
     ```bash
     mvn clean
     mvn install
     ```

4. **Lancer l’application** :
   - Une fois le projet construit, démarrez l'application en utilisant la commande suivante :
     ```bash
     mvn spring-boot:run
     ```

5. **Accéder à l'API** :
   - Une fois l'application démarrée, vous pouvez accéder à l'API REST via votre navigateur ou un outil comme Postman à l'adresse suivante : `http://localhost:8081`.

6. **Documentation de l'API** :
   - La documentation de l'API générée via **Springdoc-OpenAPI** est accessible en local en naviguant vers :
     ```url
     http://localhost:8081/swagger-ui/index.html
     ```
---
