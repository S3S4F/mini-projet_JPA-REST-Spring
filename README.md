# GestionDeTache - Application de Gestion de Tâches

Ce projet est une application Spring Boot pour la gestion de tâches, utilisant MongoDB comme base de données.

## Prérequis

Avant de lancer l'application, assurez-vous d'avoir les éléments suivants installés sur votre machine :

*   **Java 17** ou supérieur
*   **MongoDB** (installé et en cours d'exécution)

## Configuration

L'application est configurée pour se connecter à une base de données MongoDB locale par défaut.

*   **Fichier de configuration** : `src/main/resources/application.properties`
*   **Port du serveur** : `8080`
*   **URI MongoDB** : `mongodb://localhost:27017/taskdb`

Assurez-vous que votre instance MongoDB est en cours d'exécution sur le port 27017.

## 🚀 Mode "Sans Installation" (Pour le Professeur / Test)

Si vous n'avez pas MongoDB installé sur votre machine, vous pouvez lancer l'application avec une **base de données temporaire embarquée** (similaire à SQLite mais pour Mongo).

**Note importante** : Dans ce mode, les données sont stockées en mémoire et seront perdues à l'arrêt de l'application.

Pour lancer ce mode, utilisez le profil `demo` :

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

Ou si vous lancez le JAR :

```bash
java -jar -Dspring.profiles.active=demo target/gestiondetache-0.0.1-SNAPSHOT.jar
```

## Installation et Exécution

### 1. Cloner le projet (si ce n'est pas déjà fait)

### 2. Compiler le projet

Utilisez le wrapper Maven inclus pour compiler le projet et télécharger les dépendances :

```bash
./mvnw clean install
```

### 3. Lancer l'application

Vous pouvez lancer l'application directement avec le plugin Spring Boot Maven :

```bash
./mvnw spring-boot:run
```

Ou exécuter le fichier JAR généré après la compilation :

```bash
java -jar target/gestiondetache-0.0.1-SNAPSHOT.jar
```

## Accès à l'application

Une fois l'application démarrée, elle sera accessible à l'adresse suivante :

*   http://localhost:8080

### Console H2 (si activée)
*   http://localhost:8080/h2-console

## Sécurité

L'application utilise JWT pour l'authentification.
*   **Secret JWT** : Configuré dans `application.properties`

## Structure du Projet

*   `src/main/java` : Code source Java
*   `src/main/resources` : Fichiers de configuration et ressources statiques
*   `pom.xml` : Fichier de configuration Maven
