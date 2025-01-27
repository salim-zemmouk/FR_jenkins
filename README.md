API de Gestion d'une Bibliothèque 📚
Contexte
Une bibliothèque souhaite mettre en place une API pour gérer son catalogue de livres et permettre aux utilisateurs d'effectuer des emprunts. L'API doit permettre d'ajouter des livres, de les consulter, de gérer leur disponibilité et de suivre les emprunts.

📌 Objectifs
L'API doit permettre de :

Gérer le catalogue des livres

Ajouter un livre au catalogue
Lister tous les livres disponibles
Rechercher un livre par titre ou par auteur
Gérer les emprunts de livres

Un utilisateur peut emprunter un livre s’il est disponible
Un utilisateur peut retourner un livre
Lister les emprunts en cours
Gérer les utilisateurs

Ajouter un utilisateur
Lister les utilisateurs
📌 Modèle de données
⿡ Livre (Book)
Chaque livre possède :

id (identifiant unique)
title (titre)
author (auteur)
publicationYear (année de publication)
available (disponibilité du livre : true ou false)
⿢ Utilisateur (User)
Chaque utilisateur possède :

id (identifiant unique)
name (nom complet)
email (email)
⿣ Emprunt (Loan)
Un emprunt est caractérisé par :

id (identifiant unique)
bookId (livre emprunté)
userId (utilisateur qui emprunte)
loanDate (date d’emprunt)
returnDate (date de retour prévue ou effective)
📌 Endpoints API
⿡ Gestion des livres
Méthode	Endpoint	Description
POST	/books/add	Ajouter un nouveau livre au catalogue
GET	/books	Lister tous les livres
GET	/books/available	Lister les livres disponibles
GET	/books/search?title=X	Rechercher un livre par titre
GET	/books/search?author=Y	Rechercher un livre par auteur
⿢ Gestion des emprunts
Méthode	Endpoint	Description
POST	/loans/borrow	Un utilisateur emprunte un livre
POST	/loans/return	Un utilisateur retourne un livre
GET	/loans	Liste des emprunts en cours
⿣ Gestion des utilisateurs
Méthode	Endpoint	Description
POST	/users/add	Ajouter un utilisateur
GET	/users	Lister les utilisateurs
📌 Règles Métier
Un livre ne peut être emprunté que s’il est disponible (available = true).
Lorsqu'un livre est emprunté, sa disponibilité passe à false.
Lorsqu’un livre est retourné, il redevient disponible (available = true).
On ne peut pas emprunter un livre qui est déjà en cours d’emprunt.
On peut récupérer la liste des emprunts en cours, triés par date d’emprunt.