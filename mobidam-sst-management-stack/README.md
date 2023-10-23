# Stack

- Services
    - require Keycloak for single sign on
- DBs:
    - Postgres for Keycloak
- Keycloak

## Docker

Use docker compose to start the infrastructure

```
# Starts the infrastructure services like keycloak and db for backend
docker compose up -d

# Starts all services
docker compose --profile full up -d

# Includes the backend service on startup
docker compose --profile backend up -d

# Includes the frontend service on startup
docker compose --profile frontend up -d
```

additional configuration for the services is done via `*.env`-Files


### Keycloak

An admin user and a test user are created by `init-keycloak`. You can change the configuration via the keycloak ui.

#### Configuration Migration

Realm, client user and other configuration should be done by the migration client. Its config files are located in
`keycloak\migartion`. The main file is `keycloak-changelog.yml`. It contains the list of migration files that
should be applied. For more information check https://mayope.github.io/keycloakmigration/migrations/client/.