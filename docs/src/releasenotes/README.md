# Release-Notes

## Sprint 17 (23.10.2024 - 12.11.2024)
### Verbessert
- SSO Namenskonvention umgesetzt

## Sprint 17 (10.09.2024 - 01.10.2024)
## Hinzugefügt
- Vue 3 und Vuetify 3 Hebung
- SST-Management Optimierung (Frontend)
    - Keine Eingabe GueltigBis Datum
    - Tooltip
    - Prüfung EMail Format
    - Spaltenbreite
    - Zurück Springen
    - Abgelaufene Personen markieren
    - Einheitliche Bennenung Abbrechen Button
    - Anzeige Anlage-, Aenderungsdatum
    - Prüfung GueltigAb < GueltigBis Datum

## Sprint 15 (30.07.2024 - 20.08.2024)
## Hinzugefügt
- Datentransfer Info Spalte auf 512 Zeichen vergrößert

## Entfernt
- Demo Workflows

## Sprint 13 (18.06.2024 - 09.07.2024)
## Hinzugefügt
- Detailansicht der Schnittstellen
- Bearbeitungsmöglichkeit der Schnittstellen
- von Dockerhub auf GitHub Registry migriert

## Sprint 12 (28.05.2024 - 18.06.2024)
### Hinuzgefügt
- Datentransfer-Log-Tabelle pro Schnittstelle hinzugefügt

## Sprint 10 (16.04.2024 - 07.05.2024)
### Hinuzgefügt
- Dokumentation wurde veröffenticht

### Verbessert
- auto-publish docs
- baseUrl config bug fixed (#48)

## Sprint 9 (26.03.2024 - 16.04.2024)
### Verbessert
- integration-starter publishing

## Sprint 8 (05.03.2024 - 26.03.2024)
### Hinzugefügt
- SSO aktiviert und Backend abgesichert
- integration-starter

### Verbessert
- Tabellen auf deutsch

## Sprint 7 (13.02.2024 - 05.03.2024)
### Hinzugefügt
- GET Endpoint zur Statusabfrage
- POST Schnittstelle fürs Erstellen von Datentransferen
- Ersterfassung von Schnittstellen (mit ggf. Zuordnungen)

## Sprint 6 (23.01.2024 - 13.02.2024)
### Hinzugefügt
- License-Headers hinzugefügt
- Tabelle LOG_Datentransfer erstellt
- API zum Lesen aller Datentransfer-LOGs für die gegebene Schnittstelle
- Listenübersicht der Schnittstellen mit den entsprechenden Informationen erstellt

## Sprint 5 (09.01.2024 - 23.01.2024)
### Hinzugefügt
- Tabelle Schnittstelle mit `Create`, `Update`, `GetAll`, `Delete`

### Verbessert
- Beziehung zwischen Zuordnungen und Schnittstellen eingefügt

## Sprint 4 (12.12.2023 - 09.01.2024)
### Hinzugefügt
- Frontend auf Mobidam angepasst
- Dummy-Frontend: Schnittstellen Detail-View
- Zuordnung (Erstellung), Anzeigen und Löschen von Personen zu Schnittstellen in der UI

## Sprint 3 (21.11.2023 - 12.12.2023)
### Hinzugefügt
- Tabelle Zuordnung mit `Put`, `Delete`, `GetAll(schnittstellenID)`
- Build, Test und Deployment Workflows erstellt