<!--

    The MIT License
    Copyright © 2023 Landeshauptstadt München | it@M

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

-->
<template>
    <v-container fluid>
        <h1>
            Schnittstelle {{ schnittstelle.name }} &nbsp;
            <v-btn
                size="small"
                variant="outlined"
                @click="showManageSchnittstelleDialog = true"
            >
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
        </h1>
        <br />
        <v-row>
            <v-col cols="2">
                <v-text-field
                    v-model="schnittstelle.anlagedatum"
                    label="Anlagedatum"
                    variant="plain"
                    readonly
                >
                </v-text-field>
            </v-col>
            <v-col cols="2">
                <v-text-field
                    v-model="schnittstelle.aenderungsdatum"
                    label="Änderungsdatum"
                    variant="plain"
                    readonly
                >
                </v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-tooltip location="top">
                <template #activator="{ props }">
                    <v-col
                        v-if="schnittstelle.status"
                        cols="2"
                        v-bind="props"
                    >
                        <v-icon
                            v-if="schnittstelle.status == 'AKTIVIERT'"
                            color="green"
                            >mdi-check</v-icon
                        >
                        <v-icon
                            v-else
                            color="red"
                            v-bind="props"
                            >mdi-window-close</v-icon
                        >
                        {{ schnittstelle.status }}
                    </v-col>
                </template>
                Schnittstellen Status
            </v-tooltip>
            <v-col>
                <v-textarea
                    v-model="schnittstelle.begruendung"
                    label="Begründung der Statussetzung"
                    variant="outlined"
                    readonly
                >
                </v-textarea>
            </v-col>
        </v-row>
        <br />
        <v-divider />
        <br />
        <v-row>
            <v-col>
                <h3>Zugewiesene Personen</h3>
            </v-col>
        </v-row>
        <v-list lines="two">
            <v-list-item
                v-for="zuordnung in zuordnungen"
                :key="zuordnung.id"
            >
                <v-row>
                    <v-tooltip location="top">
                        <template #activator="{ props }">
                            <v-col
                                class="cols_"
                                md="3"
                                sm="3"
                                v-bind="props"
                            >
                                <v-icon>mdi-account</v-icon>
                                {{ zuordnung.benutzerkennung }}
                            </v-col>
                        </template>
                        Benutzerkennung
                    </v-tooltip>
                    <v-tooltip location="top">
                        <template #activator="{ props }">
                            <v-col
                                cols="2"
                                v-bind="props"
                            >
                                <v-icon>mdi-home</v-icon>
                                {{ zuordnung.fachbereich }}</v-col
                            >
                        </template>
                        Fachbereich
                    </v-tooltip>
                    <v-tooltip location="top">
                        <template #activator="{ props }">
                            <v-col
                                cols="3"
                                md="3"
                                sm="3"
                                v-bind="props"
                            >
                                <v-icon>mdi-email</v-icon>
                                {{ zuordnung.funktionsadresse }}</v-col
                            >
                        </template>
                        Funktionspostfach
                    </v-tooltip>
                    <v-tooltip location="top">
                        <template #activator="{ props }">
                            <v-col
                                cols="2"
                                v-bind="props"
                            >
                                <v-icon>mdi-calendar-start</v-icon>
                                {{ zuordnung.gueltigAb }}
                            </v-col>
                        </template>
                        Gültig Ab
                    </v-tooltip>
                    <v-tooltip location="top">
                        <template #activator="{ props }">
                            <v-col
                                cols="2"
                                v-bind="props"
                                :style="[
                                    validationRules.isExpired(
                                        zuordnung.gueltigBis
                                    )
                                        ? { color: 'red' }
                                        : {},
                                ]"
                            >
                                <v-icon>mdi-calendar-end</v-icon>
                                {{ zuordnung.gueltigBis }}
                            </v-col>
                        </template>
                        Gültig Bis
                    </v-tooltip>
                </v-row>
            </v-list-item>
        </v-list>
        <v-divider />
        <br />
        <v-row>
            <v-col>
                <h3>Datentransfer Log-Tabelle</h3>
                <datentransfer-table :schnittstelle="schnittstelleID" />
            </v-col>
        </v-row>
        <manage-schnittstelle-dialog
            v-model:show-dialog="showManageSchnittstelleDialog"
            :verb="'bearbeiten'"
            :schnittstelle="schnittstelle"
            :zuordnungen="zuordnungen"
            :is-edit="true"
            @schnittstelle-saved="getSchnittstelle"
            @update-exited="reload"
        ></manage-schnittstelle-dialog>
        <v-divider class="divider"></v-divider>
        <v-card-actions>
            <v-btn @click="goBack">Zurück</v-btn>
        </v-card-actions>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import { useSnackbarStore } from "@/stores/snackbar";
import { ref, onMounted } from "vue";
import ZuordnungService from "@/api/ZuordnungService";
import Zuordnung from "@/types/Zuordnung";
import DatentransferTable from "@/components/DatentransferTable.vue";
import SchnittstelleService from "@/api/SchnittstelleService";
import Schnittstelle from "@/types/Schnittstelle";
import ManageSchnittstelleDialog from "@/components/ManageSchnittstelleDialog.vue";
import router from "@/router";
import { useRules } from "@/composables/rules";

const snackbarStore = useSnackbarStore();
const schnittstelleID = router.currentRoute.value.params.id as string;
const zuordnungen = ref<Zuordnung[]>([]);
const showManageSchnittstelleDialog = ref(false);
const validationRules = useRules();

const schnittstelle = ref<Schnittstelle>({
    name: "",
    anlagedatum: "",
    id: schnittstelleID,
});

onMounted(() => {
    HealthService.checkHealth().catch((error) => {
        snackbarStore.showMessage(error);
    });
    getSchnittstelle();
});

function getZuordnungen() {
    ZuordnungService.getZuordnungenByID(schnittstelleID).then(
        (fetchedZuordnungen) => {
            zuordnungen.value = [...fetchedZuordnungen];
        }
    );
}

function getSchnittstelle() {
    SchnittstelleService.getSchnittstelle(schnittstelleID).then(
        (fetchedSchnittstelle) => {
            schnittstelle.value = fetchedSchnittstelle;
            getZuordnungen();
        }
    );
}

function goBack() {
    router.push("/");
}

function reload() {
    location.reload();
}
</script>
