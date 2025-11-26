#  - Application de Gestion de Tâches

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

##  Mode "Sans Installation"

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

## 🧪 Test avec Postman (ou cURL)

Voici les commandes pour tester l'API.

### 1. Authentification

**Inscription**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "email": "user1@example.com",
    "password": "password123"
  }'
```

**Connexion (Récupérer le Token)**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "password123"
  }'
```
*Réponse attendue : Un JSON contenant le `token`. Copiez ce token pour les requêtes suivantes.*

### 2. Gestion des Tâches

**⚠️ Important** : Remplacez `VOTRE_TOKEN_ICI` par le token obtenu à l'étape précédente.

**Créer une tâche**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer VOTRE_TOKEN_ICI" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Ma première tâche",
    "description": "Description de la tâche",
    "dueDate": "2023-12-31",
    "priority": "HIGH"
  }'
```

**Lister toutes les tâches**
```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer VOTRE_TOKEN_ICI"
```

**Mettre à jour une tâche** (Remplacer `{id}`)
```bash
curl -X PUT http://localhost:8080/api/tasks/{id} \
  -H "Authorization: Bearer VOTRE_TOKEN_ICI" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Titre modifié",
    "description": "Nouvelle description",
    "dueDate": "2023-12-31",
    "priority": "MEDIUM"
  }'
```

**Changer le statut** (Remplacer `{id}`)
```bash
curl -X PATCH http://localhost:8080/api/tasks/{id}/status \
  -H "Authorization: Bearer VOTRE_TOKEN_ICI" \
  -H "Content-Type: application/json" \
  -d '{ "status": "IN_PROGRESS" }'
```

**Supprimer une tâche** (Remplacer `{id}`)
```bash
curl -X DELETE http://localhost:8080/api/tasks/{id} \
  -H "Authorization: Bearer VOTRE_TOKEN_ICI"
```

## Structure du Projet

*   `src/main/java` : Code source Java
*   `src/main/resources` : Fichiers de configuration et ressources statiques
*   `pom.xml` : Fichier de configuration Maven
