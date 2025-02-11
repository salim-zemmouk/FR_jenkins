const { defineConfig } = require("cypress");
const { Client } = require('pg');
require('dotenv').config()
module.exports = defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      // Définir la tâche `queryDatabase` ici
      on('task', {
        queryDatabase({ query, values = [] }) {
          const client = new Client({
            user: process.env.DB_USER,
            host: process.env.DB_HOST,
            database: process.env.DB_NAME,
            password: process.env.DB_PASSWORD,
            port: process.env.DB_PORT,
          });

          return client.connect()
              .then(() => client.query(query, values))
              .then((res) => {
                client.end(); // Fermer la connexion après la requête
                return res.rows; // Retourner les résultats
              })
              .catch((err) => {
                client.end(); // Fermer la connexion en cas d'erreur
                throw err; // Propager l'erreur pour que Cypress la capture
              });
        },
      });

      // Retournez la configuration mise à jour
      return config;
    },
  },
});