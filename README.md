# 📚 Library Management


![Build](https://img.shields.io/github/actions/workflow/status/M1-UNCHK/library-management-backend/maven.yml?branch=main)
![Coverage](https://img.shields.io/codecov/c/github/M1-UNCHK/library-management-backend)
![License](https://img.shields.io/github/license/M1-UNCHK/library-management-backend)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)

Une application web de gestion de bibliothèque développée avec **Spring Boot 3.5.4**. Elle permet aux bibliothèques de gérer les livres, les auteurs et les emprunts. Les administrateurs peuvent ajouter, modifier ou supprimer des livres et des auteurs, tandis que les utilisateurs peuvent emprunter des ouvrages.

## 🚀 Fonctionnalités

- 🔐 Authentification et autorisation avec Spring Security + OAuth2
- 📖 Gestion des livres, auteurs et emprunts
- 📬 Envoi d'e-mails (confirmation, notifications)
- 📊 Monitoring avec Spring Boot Actuator
- 🧾 Documentation API avec SpringDoc OpenAPI
- 🧠 Validation des données avec Hibernate Validator
- 🛡️ Résilience avec Resilience4j
- 🖼️ Interface utilisateur avec Thymeleaf
- 🐘 Persistance avec PostgreSQL et Spring Data JPA

## 🧱 Architecture

Le projet suit une architecture modulaire et bien organisée :

```bash
src/
├── main
│   ├── java/sn.unchk.librarymanagement
│   │   ├── config
│   │   ├── constant
│   │   ├── domain
│   │   ├── exceptions
│   │   ├── models
│   │   ├── validation
│   │   ├── event
│   │   ├── presentation
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── exceptions
│   │   │   ├── security
│   │   │   └── validation
│   │   ├── repository
│   │   ├── service
│   │   └── LibraryManagementApplication.java
│   └── resources
│       ├── certificates
│       ├── mail.templates
│       ├── static
│       ├── templates
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── banner
└── test
└── java/sn.unchk.librarymanagement
├── config
├── controller
├── domain
└── service
```

## 🛠️ Technologies

| Technologie              | Version       |
|--------------------------|---------------|
| Java                     | 17            |
| Spring Boot              | 3.5.4         |
| PostgreSQL               | Runtime       |
| Maven                    | 3.9.6         |
| Docker                   | Multi-stage   |
| Spring Security          | OAuth2        |
| SpringDoc OpenAPI        | 2.8.9         |
| Resilience4j             | 2.2.0         |
| Thymeleaf                | Starter       |

## 🐳 Docker
L'application peut être conteneurisée avec Docker :

## Build de l'image
```bash
docker build -t library-management-api .
```

## Exécution du conteneur
```bash
docker run -p 9090:9090 library-management-api
```

## 📄 API Documentation :
```bash
En local : http://localhost:9090/swagger-ui.html
```
```bash
En prod : http://51.83.71.17:9090/swagger-ui.html
```

## 🧪 Tests

Les tests unitaires et d'intégration sont disponibles dans le dossier src/test/java. Le projet utilise :

JUnit

Spring Boot Test

Spring Security Test

## 📦 Installation locale

## Cloner le projet
```bash
git clone https://github.com/votre-utilisateur/library-management.git
```

# Lancer l'application en profil dev
```bash
cd library-management
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## 📬 Contributeurs

Développé par le Groupe 6 UNCHM :

![Mouhamad DIACK](https://github.com/Mouhamed-git)
![Makhtar Saré](https://github.com/mathi0u)
![Beni Djongnabe](https://github.com/supervaiki)
![Ngoné Ndiaye](https://github.com)

## 📄 Licence
Ce projet est sous licence MIT - voir le fichier LICENSE
pour plus de détails.