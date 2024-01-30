#!/bin/bash
#
# Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
# der Landeshauptstadt München, 2024
#

mvn clean spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=local"
