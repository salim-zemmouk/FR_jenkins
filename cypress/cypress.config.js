const { defineConfig } = require("cypress");
const { Client } = require('pg');
require('dotenv').config()
module.exports = defineConfig({
  reporter: 'mochawesome',
  reporterOptions: {
    reportDir: 'cypress/screenshots',
    overwrite: true,
    html: true,
    json: false
  },
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
                client.end();
                return res.rows;
              })
              .catch((err) => {
                client.end();
                throw err;
              });
        },
      });
      return config;
    },
  },
});