Application de Transfert d’Argent
1. Introduction
•	Présentation du projet :
Ce projet consiste à développer une application de transfert d’argent permettant aux utilisateurs d’envoyer, recevoir, déposer et retirer de l’argent de manière sécurisée.
•	Objectifs :
o	Digitaliser les transactions financières locales.
o	Permettre une gestion simple et rapide des comptes.
o	Générer des reçus PDF pour chaque opération.
2. Cahier des charges
•	Fonctionnalités principales :
o	Création et gestion de comptes utilisateurs.
o	Dépôt et retrait d’argent.
o	Transfert entre utilisateurs.
o	Consultation de l’historique des transactions.
o	Génération de reçus PDF.
o	Abonnement (types d’abonnement avec avantages).
•	Exigences techniques :
o	Backend : Spring Boot (Java, Spring Security, JPA/Hibernate).
o	Frontend :  Angular.
o	Base de données : MySQL.
o	Authentification : JWT (JSON Web Token).
o	API REST pour communication front-back.
3. Conception du système
•	Architecture générale :
o	Architecture en couches (Controller → Service → Repository → Database).
o	Sécurité avec Spring Security.
o	Pagination pour les listes (transactions, abonnements).
•	Diagrammes :
o	Cas d’utilisation : Transfert, dépôt, retrait, génération de reçu.
o	Diagramme de classes : Utilisateur, Transaction, Abonnement, etc.
4. Implémentation

•	Backend :
o	Model :
       Enumeration : Role, Statut, TypeAbonnement, TypeTransaction
 UtilisateurModel, TransactionModel, AbonnementModel, AbstractModel.
o	Repositories : UtilisateurRepository, TransactionRepository, AbonnementRepository.
o	Services :
          DTO: Request: LoginUserDTO, RegisterDTO, TransferRequestDTO
         Response: LoginRespons, AbonnementDTO, TransactionDTO, ProfilUtilisateurDTO  
         Implementation: AbonnementService, TransactionService, UtilisateurService        
 TransactionService, AbonnementService, UtilisateurService.
o	Utils: QRCODEGENERSTEUR.
o	Web :
     Filtre: ResponseFormattingFilter, ResponseWrapper
     Response: PageResponse
 TransactionController, AbonnementController, UtilisateurController, AuthenticationController.
o	Configuration :
                ApplicationConfiguration, FilterConfig,
               JpaAuditingConfig, JwtAuthenticationFilter, OpenAPIConfig, SecurityConfiguration.
            
•	Frontend :
o	Formulaire de connexion et d’inscription.
o	Interface utilisateur pour dépôt/retrait/transfert.
o	Tableau de bord des transactions.
o	Téléchargement du reçu PDF.
5. Résultats 
il est fait sous format vidéo
6. Difficultés rencontrées
•	Gestion des erreurs HTTP (403 Forbidden, 401 Unauthorized).
•	Intégration front-back avec JWT.
•	Problèmes liés à la génération de PDF (compatibilité des bibliothèques).
7. Conclusion et perspectives

•	Conclusion :
L’application répond aux besoins essentiels de transfert d’argent avec sécurité et traçabilité.
•	Améliorations futures :
o	Intégration avec un service de paiement mobile (Orange Money, Wave, PayPal).
o	Notifications par SMS/email.
o	Application mobile native (React Native / Flutter).
o	Ajout d’analytics (statistiques sur les transactions).
