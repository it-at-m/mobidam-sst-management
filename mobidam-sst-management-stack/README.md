# Stack

- Services
  - benötigt Keycloak für Single Sign On
- DBs:
   - Postgres für Keycloak
- Keycloak

## Docker
Nutze docker compose um die Infrastruktur zu starten

```
# Statet die Infrastrukturservices, wie keycloak und db für das Backend
docker compose up -d

# Startet alle Services
docker compose --profile full up -d

# Inkludiert Backendservices beim Starten 
docker compose --profile backend up -d

# Inkludiert Frontend Services beim Starten
docker compose --profile frontend up -d
```

Weitere Konfigurationen für den Service können mit Hilfe der `*.env`-Files getätigt werden.


### Keycloak

Ein Admin- und ein Test-User werden mit `init-keycloak` generiert. 
Die Konfiguration kann mittels der Keycloak UI angepasst werden.

#### Konfigurations Migration

Realm, Client User und andere Konfigurationen werden vom Migration Client übernommen.  Die zugehörigen Konfigurationsfiles sind unter
`keycloak\migartion` zu finden. Im Hauptfile (`keycloak-changelog.yml`) ist eine Liste an Migrations Files zu finden, 
die angewendet werden sollen. Weitere Informationen sind unter folgendem Linke zu finden: 
https://mayope.github.io/keycloakmigration/migrations/client/
