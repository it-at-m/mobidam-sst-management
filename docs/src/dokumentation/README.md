# Einführung

Für die Administration und Überwachung der Vielzahl von Schnittstellen in der MobidaM-Plattform wird eine Weboberfläche bereitgestellt, 
in welcher diese Tätigkeiten durchgeführt werden. 

Primär wird über diese Komponente das Aktivieren und Deaktivieren einzelner Schnittstellen ermöglicht. 
Dies kann nötig sein, wenn ein Datenlieferant eine längere Wartung der Schnittstelle durchführt und diese nicht verfügbar ist 
oder die bereitgestellten Daten fehlerhaft sind und nicht verarbeitet werden können. 
Um in solchen Fällen die Anzahl der Fehlermeldungen zu minimieren, können die betroffenen Schnittstellen deaktiviert und die Deaktivierung protokolliert werden. 
Zudem ist eine einfache Auswertung der Logdateien möglich.

An der Webanwendung kann sich eine berechtigte Person anmelden und sieht dann in der Oberfläche die Schnittstellen samt ihrem aktuellen Status.

![Screenshot Übersicht Schnittstellen](~@source/images/MobidaM_SST-Management-Komponente_Screenshot1.png)
*Screenshot Übersicht Schnittstellen*

---

![Screenshot Ansicht Schnittstelle](~@source/images/MobidaM_SST-Management-Komponente_Screenshot2.png)
*Screenshot Ansicht Schnittstellen*

---

## Datenmodell

![Datenmodell](~@source/images/Datenmodell.drawio.png)

## Fachliche Beschreibung

### Anlegen von Schnittstellen

![Screenshot Anlegen Schnittstellen](~@source/images/MobidaM_SST-Management-Komponente_Screenshot3.png)

--- 

![Screenshot Anlegen Schnittstellen](~@source/images/MobidaM_SST-Management-Komponente_Screenshot4.png)

### Speichern der Log-Informationen

Sobald von einen Datengeber Daten abgerufen werden, werden in der Management-Komponente folgende Informationen zum Abruf gespeichert:

    Welche Schnittstelle (z. B. S05.1)
    Datum, Beginn und Ende Uhrzeit
    Status
    Anzahl der Datensätze
    Fehlermeldung im Fehlerfall

### Auswerten der Loginformationen

Innerhalb der Steuerungsoberfläche gibt es die Möglichkeit, die Logeinträge einzusehen. 

## API

![API](~@source/images/API.png)

## Komponenten
Die MobidaM SST-Management-Komponente wird in der Microservice Architektur umgesetzt und in Github als Multi-Repo verwaltet.

![Komponenten](~@source/images/Komponenten.png)

Derzeit werden die folgenden Services in Github unter der MIT Lizenz Open Source zur Verfügung gestellt:

### Frontend

Das [Frontend](https://github.com/it-at-m/mobidam-sst-management/mobidam-sst-management-frontend) liefert die Weboberfläche der Anwendung incl. Authentifizierung aus.

### Backend

Das [Backend](https://github.com/it-at-m/mobidam-sst-management/mobidam-sst-management-backend) ist der zentrale Microservice zur Verarbeitung der Daten
der Anwendung. 

### Integration

Mit dem mobidam-sst-management-integration-starter wird eine allgemeine Komponente zu bereitgestellt, die in die Schnittstellen eingebaut werden kann,
sodass hier ein Großteil an Entwicklungsarbeit gespart werden kann.


## Stack

Als Infrastruktur wird eine Postgresql-Datenbank sowie als Identity Provider Keycloak eingesetzt.


## Build & Deployment

### Github-Actions

| Auslöser                                          | Action                  | Build                                              |
|---------------------------------------------------|-------------------------|----------------------------------------------------|
| on push (Feature-Branch)<br>on PR (sprint-Branch) | **build-*app***	       | Backend / Frontend compliance check and build test |
| manuell                                           | **publish-feature**	    | Build and release to dev                           |
| on push (sprint-Branch)                           | **publish-test**        | Build and release to test                          |
| manuell<br>scheduled (jede Woche)                 | **publish-demo**	       | Build and release to demo                          |
| manuell                                           | **release-build-*app*** | Build backend/frontend release                     |
| manuell (Input: release-version)                  | **publish-prod-*app***  | Build and release to prod                          |
| manuell                                           | **release-docs**        | Release docs                                       |


## Reguläre Builds

+ **Backend / Frontend compliance check and build test**: Nach jedem Push auf einen Feature-Branch bzw. bei jedem Öffnen oder Wiedereröffnen von Pull Requests auf sprint / main wird ein Maven Build des entsprechenden Moduls inklusive der Unit-Tests und Code-Checks und ein Compliance-Check ausgeführt.

## Image Builds

Bei jedem der folgenden Szenarien werden immer die gleichen Schritte jeweils für Frontend und Backend ausgeführt: ein Maven Build inkl. Unit-Tests und Code-Check, Image Build und Image Push auf Dockerhub. 

+ **Build and release to dev**: Dieser Workflow kann manuell auf beliebigen GitHub Branches ausgeführt werden. Nach einem regulären Maven Build wird das Image erstellt und auf Dockerhub gepusht unter den Tags mobidam:mobidam-sst-management-*frontend/backend*-dev.
+ **Build and release to test**: Dieser Workflow wird nur automatisch nach einem Push auf den sprint GitHub Branch ausgeführt. Die erstellten Tags lauten mobidam:mobidam-sst-management-*frontend/backend*-test
+ **Build and release to demo**: Dieser Workflow wird wöchentlich am Samstag um 13:00 Uhr ausgelöst, wird aber nur jede 3. Woche ausgeführt. Zusätzlich dazu kann er auch manuell gestartet werden. In diesem Workflow wird der Stand des main GitHub Branches gebaut und gepusht mit den Tags mobidam:mobidam-sst-management-*frontend/backend*-demo.
+ **Release docs**: Dieser Workflow kann manuell gestartet werden. Er führt ein Build und Release des /docs Ordners aus auf dem sprint Branch. Die dort umgesetzte Vuepress-Seite wird gebaut und die gebauten HTML-Dateien werden auf den gh-pages Branch gepusht, um dann automatisch auf die für GitHub-Pages angegebene URL deployed zu werden.

## Snapshots und Releases

+ **Build backend/frontend release**: Diese Workflows betrachten primär den Codestand des main GitHub Branch und können manuell gestartet werden. Innerhalb der Workflows erfolgt ein Maven Build inkl. Unit-Tests und Code-Check, Image Build und Image Push auf Dockerhub. Zusätzlich wird die vorhandene Snapshot-Version zu einer Nicht-Snapshot-Version (Release) geändert. Zudem werden ein GitHub Release (Release Backend/Frontend *Version*) und ein Git Tag (backend/frontend-*version*) für den Release erstellt. Als nächstes werden Images mit den Tags mobidam:mobidam-sst-management-*frontend/backend*-*version* und mobidam:mobidam-sst-management-*frontend/backend*-demo erstellt und gepusht. Ein GitHub-Bot veröffentlicht die pom.xml mit der neuen, hochgezählten Snapshot-Version auf dem Default-Branch. Es wird grundsätzl. immer nur die Patch-Version automatisiert hochgezählt. Ist eine Major/Minor-Erhöhung nötig, wird dies im Feature-Branch gemacht.
