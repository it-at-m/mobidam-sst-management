(window.webpackJsonp=window.webpackJsonp||[]).push([[15],{297:function(e,n,t){e.exports=t.p+"assets/img/MobidaM_SST-Management-Komponente_Screenshot1.05630f0a.png"},298:function(e,n,t){e.exports=t.p+"assets/img/MobidaM_SST-Management-Komponente_Screenshot2.3976da79.png"},299:function(e,n,t){e.exports=t.p+"assets/img/Datenmodell.drawio.8c257d76.png"},300:function(e,n,t){e.exports=t.p+"assets/img/MobidaM_SST-Management-Komponente_Screenshot3.8c00d338.png"},301:function(e,n,t){e.exports=t.p+"assets/img/MobidaM_SST-Management-Komponente_Screenshot4.bca5276d.png"},302:function(e,n,t){e.exports=t.p+"assets/img/Komponenten.ef3c1d61.png"},314:function(e,n,t){"use strict";t.r(n);var a=t(10),r=Object(a.a)({},(function(){var e=this,n=e._self._c;return n("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[n("h1",{attrs:{id:"dokumentation"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#dokumentation"}},[e._v("#")]),e._v(" Dokumentation")]),e._v(" "),n("h2",{attrs:{id:"einfuhrung"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#einfuhrung"}},[e._v("#")]),e._v(" Einführung")]),e._v(" "),n("p",[e._v("Für die Administration und Überwachung der Vielzahl von Schnittstellen in der MobidaM-Plattform wird eine Weboberfläche bereitgestellt,\nin welcher diese Tätigkeiten durchgeführt werden.")]),e._v(" "),n("p",[e._v("Primär wird über diese Komponente das Aktivieren und Deaktivieren einzelner Schnittstellen ermöglicht.\nDies kann nötig sein, wenn ein Datenlieferant eine längere Wartung der Schnittstelle durchführt und diese nicht verfügbar ist\noder die bereitgestellten Daten fehlerhaft sind und nicht verarbeitet werden können.\nUm in solchen Fällen die Anzahl der Fehlermeldungen zu minimieren, können die betroffenen Schnittstellen deaktiviert und die Deaktivierung protokolliert werden.\nZudem ist eine einfache Auswertung der Logdateien möglich.")]),e._v(" "),n("p",[e._v("An der Webanwendung kann sich eine berechtigte Person anmelden und sieht dann in der Oberfläche die Schnittstellen samt ihrem aktuellen Status.")]),e._v(" "),n("p",[n("img",{attrs:{src:t(297),alt:"Screenshot Übersicht Schnittstellen"}}),e._v(" "),n("em",[e._v("Screenshot Übersicht Schnittstellen")])]),e._v(" "),n("hr"),e._v(" "),n("p",[n("img",{attrs:{src:t(298),alt:"Screenshot Ansicht Schnittstelle"}}),e._v(" "),n("em",[e._v("Screenshot Ansicht Schnittstelle")])]),e._v(" "),n("hr"),e._v(" "),n("h2",{attrs:{id:"datenmodell"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#datenmodell"}},[e._v("#")]),e._v(" Datenmodell")]),e._v(" "),n("p",[n("img",{attrs:{src:t(299),alt:"Datenmodell"}})]),e._v(" "),n("h2",{attrs:{id:"fachliche-beschreibung"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#fachliche-beschreibung"}},[e._v("#")]),e._v(" Fachliche Beschreibung")]),e._v(" "),n("h3",{attrs:{id:"anlegen-von-schnittstellen"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#anlegen-von-schnittstellen"}},[e._v("#")]),e._v(" Anlegen von Schnittstellen")]),e._v(" "),n("p",[n("img",{attrs:{src:t(300),alt:"Screenshot Anlegen Schnittstellen"}})]),e._v(" "),n("hr"),e._v(" "),n("p",[n("img",{attrs:{src:t(301),alt:"Screenshot Anlegen Schnittstellen"}})]),e._v(" "),n("h3",{attrs:{id:"speichern-der-log-informationen"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#speichern-der-log-informationen"}},[e._v("#")]),e._v(" Speichern der Log-Informationen")]),e._v(" "),n("p",[e._v("Sobald von einen Datengeber Daten abgerufen werden, werden in der Management-Komponente folgende Informationen zum Abruf gespeichert:")]),e._v(" "),n("div",{staticClass:"language- extra-class"},[n("pre",[n("code",[e._v("Welche Schnittstelle (z. B. S05.1)\nDatum, Beginn und Ende Uhrzeit\nStatus\nAnzahl der Datensätze\nFehlermeldung im Fehlerfall\n")])])]),n("h3",{attrs:{id:"auswerten-der-loginformationen"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#auswerten-der-loginformationen"}},[e._v("#")]),e._v(" Auswerten der Loginformationen")]),e._v(" "),n("p",[e._v("Innerhalb der Steuerungsoberfläche gibt es die Möglichkeit, die Logeinträge einzusehen.")]),e._v(" "),n("h2",{attrs:{id:"komponenten"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#komponenten"}},[e._v("#")]),e._v(" Komponenten")]),e._v(" "),n("p",[e._v("Die MobidaM SST-Management-Komponente wird in der Microservice Architektur umgesetzt und in Github als Multi-Repo verwaltet.")]),e._v(" "),n("p",[n("img",{attrs:{src:t(302),alt:"Komponenten"}})]),e._v(" "),n("p",[e._v("Derzeit werden die folgenden Services in Github unter der MIT Lizenz Open Source zur Verfügung gestellt:")]),e._v(" "),n("h3",{attrs:{id:"frontend"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#frontend"}},[e._v("#")]),e._v(" Frontend")]),e._v(" "),n("p",[e._v("Das "),n("a",{attrs:{href:"https://github.com/it-at-m/mobidam-sst-management/mobidam-sst-management-frontend",target:"_blank",rel:"noopener noreferrer"}},[e._v("Frontend"),n("OutboundLink")],1),e._v(" liefert die Weboberfläche der Anwendung incl. Authentifizierung aus.")]),e._v(" "),n("h3",{attrs:{id:"backend"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#backend"}},[e._v("#")]),e._v(" Backend")]),e._v(" "),n("p",[e._v("Das "),n("a",{attrs:{href:"https://github.com/it-at-m/mobidam-sst-management/mobidam-sst-management-backend",target:"_blank",rel:"noopener noreferrer"}},[e._v("Backend"),n("OutboundLink")],1),e._v(" ist der zentrale Microservice zur Verarbeitung der Daten\nder Anwendung.")]),e._v(" "),n("h3",{attrs:{id:"integration"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#integration"}},[e._v("#")]),e._v(" Integration")]),e._v(" "),n("p",[e._v("Mit dem mobidam-sst-management-integration-starter wird eine allgemeine Komponente zu bereitgestellt, die in die Schnittstellen eingebaut werden kann,\nsodass hier ein Großteil an Entwicklungsarbeit gespart werden kann.")]),e._v(" "),n("h2",{attrs:{id:"stack"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#stack"}},[e._v("#")]),e._v(" Stack")]),e._v(" "),n("p",[e._v("Als Infrastruktur wird eine Postgresql-Datenbank sowie als Identity Provider Keycloak eingesetzt.")]),e._v(" "),n("h2",{attrs:{id:"build-deployment"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#build-deployment"}},[e._v("#")]),e._v(" Build & Deployment")]),e._v(" "),n("h3",{attrs:{id:"github-actions"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#github-actions"}},[e._v("#")]),e._v(" Github-Actions")]),e._v(" "),n("table",[n("thead",[n("tr",[n("th",[e._v("Auslöser")]),e._v(" "),n("th",[e._v("Action")]),e._v(" "),n("th",[e._v("Build")])])]),e._v(" "),n("tbody",[n("tr",[n("td",[e._v("on push (Feature-Branch)"),n("br"),e._v("on PR (sprint-Branch)")]),e._v(" "),n("td",[n("strong",[e._v("build-"),n("em",[e._v("app")])])]),e._v(" "),n("td",[e._v("Backend / Frontend compliance check and build test")])]),e._v(" "),n("tr",[n("td",[e._v("manuell")]),e._v(" "),n("td",[n("strong",[e._v("publish-feature")])]),e._v(" "),n("td",[e._v("Build and release to dev")])]),e._v(" "),n("tr",[n("td",[e._v("on push (sprint-Branch)")]),e._v(" "),n("td",[n("strong",[e._v("publish-test")])]),e._v(" "),n("td",[e._v("Build and release to test")])]),e._v(" "),n("tr",[n("td",[e._v("manuell"),n("br"),e._v("scheduled (jede Woche)")]),e._v(" "),n("td",[n("strong",[e._v("publish-demo")])]),e._v(" "),n("td",[e._v("Build and release to demo")])]),e._v(" "),n("tr",[n("td",[e._v("manuell")]),e._v(" "),n("td",[n("strong",[e._v("release-build-"),n("em",[e._v("app")])])]),e._v(" "),n("td",[e._v("Build backend/frontend release")])]),e._v(" "),n("tr",[n("td",[e._v("manuell (Input: release-version)")]),e._v(" "),n("td",[n("strong",[e._v("publish-prod-"),n("em",[e._v("app")])])]),e._v(" "),n("td",[e._v("Build and release to prod")])]),e._v(" "),n("tr",[n("td",[e._v("manuell")]),e._v(" "),n("td",[n("strong",[e._v("release-docs")])]),e._v(" "),n("td",[e._v("Release docs")])])])]),e._v(" "),n("h2",{attrs:{id:"regulare-builds"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#regulare-builds"}},[e._v("#")]),e._v(" Reguläre Builds")]),e._v(" "),n("ul",[n("li",[n("strong",[e._v("Backend / Frontend compliance check and build test")]),e._v(": Nach jedem Push auf einen Feature-Branch bzw. bei jedem Öffnen oder Wiedereröffnen von Pull Requests auf sprint / main wird ein Maven Build des entsprechenden Moduls inklusive der Unit-Tests und Code-Checks und ein Compliance-Check ausgeführt.")])]),e._v(" "),n("h2",{attrs:{id:"image-builds"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#image-builds"}},[e._v("#")]),e._v(" Image Builds")]),e._v(" "),n("p",[e._v("Bei jedem der folgenden Szenarien werden immer die gleichen Schritte jeweils für Frontend und Backend ausgeführt: ein Maven Build inkl. Unit-Tests und Code-Check, Image Build und Image Push auf Dockerhub.")]),e._v(" "),n("ul",[n("li",[n("strong",[e._v("Build and release to dev")]),e._v(": Dieser Workflow kann manuell auf beliebigen GitHub Branches ausgeführt werden. Nach einem regulären Maven Build wird das Image erstellt und auf Dockerhub gepusht unter den Tags mobidam:mobidam-sst-management-"),n("em",[e._v("frontend/backend")]),e._v("-dev.")]),e._v(" "),n("li",[n("strong",[e._v("Build and release to test")]),e._v(": Dieser Workflow wird nur automatisch nach einem Push auf den sprint GitHub Branch ausgeführt. Die erstellten Tags lauten mobidam:mobidam-sst-management-"),n("em",[e._v("frontend/backend")]),e._v("-test")]),e._v(" "),n("li",[n("strong",[e._v("Build and release to demo")]),e._v(": Dieser Workflow wird wöchentlich am Samstag um 13:00 Uhr ausgelöst, wird aber nur jede 3. Woche ausgeführt. Zusätzlich dazu kann er auch manuell gestartet werden. In diesem Workflow wird der Stand des main GitHub Branches gebaut und gepusht mit den Tags mobidam:mobidam-sst-management-"),n("em",[e._v("frontend/backend")]),e._v("-demo.")]),e._v(" "),n("li",[n("strong",[e._v("Release docs")]),e._v(": Dieser Workflow kann manuell gestartet werden. Er führt ein Build und Release des /docs Ordners aus auf dem sprint Branch. Die dort umgesetzte Vuepress-Seite wird gebaut und die gebauten HTML-Dateien werden auf den gh-pages Branch gepusht, um dann automatisch auf die für GitHub-Pages angegebene URL deployed zu werden.")])]),e._v(" "),n("h2",{attrs:{id:"snapshots-und-releases"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#snapshots-und-releases"}},[e._v("#")]),e._v(" Snapshots und Releases")]),e._v(" "),n("ul",[n("li",[n("strong",[e._v("Build backend/frontend release")]),e._v(": Diese Workflows betrachten primär den Codestand des main GitHub Branch und können manuell gestartet werden. Innerhalb der Workflows erfolgt ein Maven Build inkl. Unit-Tests und Code-Check, Image Build und Image Push auf Dockerhub. Zusätzlich wird die vorhandene Snapshot-Version zu einer Nicht-Snapshot-Version (Release) geändert. Zudem werden ein GitHub Release (Release Backend/Frontend "),n("em",[e._v("Version")]),e._v(") und ein Git Tag (backend/frontend-"),n("em",[e._v("version")]),e._v(") für den Release erstellt. Als nächstes werden Images mit den Tags mobidam:mobidam-sst-management-"),n("em",[e._v("frontend/backend")]),e._v("-"),n("em",[e._v("version")]),e._v(" und mobidam:mobidam-sst-management-"),n("em",[e._v("frontend/backend")]),e._v("-demo erstellt und gepusht. Ein GitHub-Bot veröffentlicht die pom.xml mit der neuen, hochgezählten Snapshot-Version auf dem Default-Branch. Es wird grundsätzl. immer nur die Patch-Version automatisiert hochgezählt. Ist eine Major/Minor-Erhöhung nötig, wird dies im Feature-Branch gemacht.")])])])}),[],!1,null,null,null);n.default=r.exports}}]);