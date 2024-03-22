# Technisches Setup
## Vorbereitung
Zum Starten der Anwendung muss zunächst das lokale Setup mit den umgebungsspezifischen yml-Dateien bereit gestellt werden.

## Swagger UI
Die Swagger UI gibt eine Übersicht über die API und ermöglicht eine einfache Testung des Backends.
So können beispielsweise über den jeweiligen Controller `POST`, `GET` und `DELETE` Anfragen getätigt und die Antworten ausgelesen werden.

Die API kann nach dem Starten des Backends über den Link http://localhost:39146/swagger-ui/index.html#/ erreicht werden. 

Weitere Informationen zur Swagger UI sind unter https://swagger.io/tools/swagger-ui/ zu finden.

## Integration-starter
* Client-Projekt erstellen mit Dependency: 
```
<dependency>
    <groupId>de.muenchen.mobidam</groupId>
    <artifactId>mobidam-sst-management-integration-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
* Konfiguration
```
spring.security:
  ...
  
...  
de.muenchen.mobidam.integration:
    baseUrl: http://localhost:39146
```
* Verwendung
```
@Autowired
private SstManagementIntegrationService sstManagementIntegrationService;
...
boolean status = sstManagementIntegrationService.isActivated("04415b59-a35f-47e1-9567-4373713ad908");	
```