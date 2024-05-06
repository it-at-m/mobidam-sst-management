(window.webpackJsonp=window.webpackJsonp||[]).push([[27],{318:function(e,n,t){"use strict";t.r(n);var s=t(10),a=Object(s.a)({},(function(){var e=this,n=e._self._c;return n("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[n("h1",{attrs:{id:"technisches-setup"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#technisches-setup"}},[e._v("#")]),e._v(" Technisches Setup")]),e._v(" "),n("h2",{attrs:{id:"vorbereitung"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#vorbereitung"}},[e._v("#")]),e._v(" Vorbereitung")]),e._v(" "),n("p",[e._v("Zum Starten der Anwendung muss zunächst das lokale Setup mit den umgebungsspezifischen yml-Dateien bereit gestellt werden.")]),e._v(" "),n("h2",{attrs:{id:"swagger-ui"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#swagger-ui"}},[e._v("#")]),e._v(" Swagger UI")]),e._v(" "),n("p",[e._v("Die Swagger UI gibt eine Übersicht über die API und ermöglicht eine einfache Testung des Backends.\nSo können beispielsweise über den jeweiligen Controller "),n("code",[e._v("POST")]),e._v(", "),n("code",[e._v("GET")]),e._v(" und "),n("code",[e._v("DELETE")]),e._v(" Anfragen getätigt und die Antworten ausgelesen werden.")]),e._v(" "),n("p",[e._v("Die API kann nach dem Starten des Backends über den Link http://localhost:39146/swagger-ui/index.html#/ erreicht werden.")]),e._v(" "),n("p",[e._v("Weitere Informationen zur Swagger UI sind unter https://swagger.io/tools/swagger-ui/ zu finden.")]),e._v(" "),n("h2",{attrs:{id:"integration-starter"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#integration-starter"}},[e._v("#")]),e._v(" Integration-starter")]),e._v(" "),n("ul",[n("li",[e._v("Client-Projekt erstellen mit Dependency:")])]),e._v(" "),n("div",{staticClass:"language- line-numbers-mode"},[n("pre",{pre:!0,attrs:{class:"language-text"}},[n("code",[e._v("<dependency>\n    <groupId>de.muenchen.mobidam</groupId>\n    <artifactId>mobidam-sst-management-integration-starter</artifactId>\n    <version>0.0.1-SNAPSHOT</version>\n</dependency>\n")])]),e._v(" "),n("div",{staticClass:"line-numbers-wrapper"},[n("span",{staticClass:"line-number"},[e._v("1")]),n("br"),n("span",{staticClass:"line-number"},[e._v("2")]),n("br"),n("span",{staticClass:"line-number"},[e._v("3")]),n("br"),n("span",{staticClass:"line-number"},[e._v("4")]),n("br"),n("span",{staticClass:"line-number"},[e._v("5")]),n("br")])]),n("ul",[n("li",[e._v("Konfiguration")])]),e._v(" "),n("div",{staticClass:"language- line-numbers-mode"},[n("pre",{pre:!0,attrs:{class:"language-text"}},[n("code",[e._v("spring.security:\n  ...\n  \n...  \nde.muenchen.mobidam.integration:\n    baseUrl: http://localhost:39146\n")])]),e._v(" "),n("div",{staticClass:"line-numbers-wrapper"},[n("span",{staticClass:"line-number"},[e._v("1")]),n("br"),n("span",{staticClass:"line-number"},[e._v("2")]),n("br"),n("span",{staticClass:"line-number"},[e._v("3")]),n("br"),n("span",{staticClass:"line-number"},[e._v("4")]),n("br"),n("span",{staticClass:"line-number"},[e._v("5")]),n("br"),n("span",{staticClass:"line-number"},[e._v("6")]),n("br")])]),n("ul",[n("li",[e._v("Verwendung")])]),e._v(" "),n("div",{staticClass:"language- line-numbers-mode"},[n("pre",{pre:!0,attrs:{class:"language-text"}},[n("code",[e._v('@Autowired\nprivate SstManagementIntegrationService sstManagementIntegrationService;\n...\nboolean status = sstManagementIntegrationService.isActivated("04415b59-a35f-47e1-9567-4373713ad908");\t\n')])]),e._v(" "),n("div",{staticClass:"line-numbers-wrapper"},[n("span",{staticClass:"line-number"},[e._v("1")]),n("br"),n("span",{staticClass:"line-number"},[e._v("2")]),n("br"),n("span",{staticClass:"line-number"},[e._v("3")]),n("br"),n("span",{staticClass:"line-number"},[e._v("4")]),n("br")])])])}),[],!1,null,null,null);n.default=a.exports}}]);