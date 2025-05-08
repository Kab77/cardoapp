# Connecteam - Application de Gestion Ferroviaire

## Déploiement sur Replit

1. Cliquez sur le bouton "Run" pour démarrer l'application
2. L'application sera accessible sur : `https://<votre-repl-name>.<votre-username>.repl.co`
3. La console H2 est accessible sur : `https://<votre-repl-name>.<votre-username>.repl.co/h2-console`

### Accès à la console H2
- URL JDBC : `jdbc:h2:mem:connecteamdb`
- Username : `sa`
- Password : `password`

### Points importants
- La base de données est en mémoire (H2)
- Les données sont réinitialisées à chaque redémarrage
- Les fichiers uploadés sont stockés dans le dossier `uploads`

## API Endpoints

### Gestion des connaissances locomotives
- `GET /api/locomotive-knowledge` : Liste toutes les connaissances
- `POST /api/locomotive-knowledge` : Crée une nouvelle connaissance
- `GET /api/locomotive-knowledge/{id}` : Récupère une connaissance spécifique
- `PUT /api/locomotive-knowledge/{id}` : Met à jour une connaissance
- `DELETE /api/locomotive-knowledge/{id}` : Supprime une connaissance

### Gestion de la validité
- `GET /api/locomotive-knowledge/validity/expired` : Liste les connaissances expirées
- `GET /api/locomotive-knowledge/validity/needing-reminder` : Liste les connaissances nécessitant un rappel
- `POST /api/locomotive-knowledge/{id}/renew` : Renouvelle une connaissance
- `POST /api/locomotive-knowledge/send-reminders` : Envoie les rappels 