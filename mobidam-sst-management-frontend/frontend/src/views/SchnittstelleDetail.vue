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
    <v-container>
        <h1>
            Schnittstelle {{ schnittstelle.name }} &nbsp;
            <v-btn
                small
                outlined
                @click="showEditSchnittstelleDialog = true"
            >
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
        </h1>
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
                <v-col>
                    <v-icon>mdi-account</v-icon>
                    {{ zuordnung.benutzerkennung }}
                </v-col>
                <v-col>
                    <v-icon>mdi-home</v-icon>
                    {{ zuordnung.fachbereich }}</v-col
                >
                <v-col>
                    <v-icon>mdi-email</v-icon>
                    {{ zuordnung.funktionsadresse }}</v-col
                >
                <v-col>
                    <v-icon>mdi-calendar-start</v-icon>
                    {{ zuordnung.gueltigAb }}
                </v-col>
                <v-col>
                    <v-icon>mdi-calendar-end</v-icon>
                    {{ zuordnung.gueltigBis }}
                </v-col>
                <v-col>
                    <v-btn
                        small
                        @click="
                            showYesNoDialog = true;
                            tryToDeleteZuordnung(zuordnung);
                        "
                    >
                        <v-icon>mdi-delete</v-icon>
                    </v-btn>
                </v-col>
            </v-list-item>
        </v-list>
        <v-divider />
        <br />
        <v-row>
            <v-col>
                <h3>Datentransfer Log-Tabelle</h3>
                <DatentransferTable :schnittstelle="schnittstelleID" />
            </v-col>
        </v-row>
        <add-person-dialog
            :show-dialog.sync="showAddPersonDialog"
            confirm-button="Speichern"
            @zuordnung-saved="saveZuordnung"
        ></add-person-dialog>
        <yes-no-dialog
            dialogtext="Sicher, dass Du die Zuordnung löschen möchtest?"
            dialogtitle="Zuordnung löschen"
            :value.sync="showYesNoDialog"
            @no="showYesNoDialog = false"
            @yes="deleteZuordnung"
        ></yes-no-dialog>
        <edit-schnittstelle-dialog
            :show-dialog.sync="showEditSchnittstelleDialog"
            :schnittstelle-i-d="schnittstelleID"
            @schnittstelle-saved="getSchnittstelle"
        ></edit-schnittstelle-dialog>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import AddPersonDialog from "@/components/AddPersonDialog.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router/composables";
import ZuordnungService from "@/api/ZuordnungService";
import Zuordnung from "@/types/Zuordnung";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import DatentransferTable from "@/components/DatentransferTable.vue";
import SchnittstelleService from "@/api/SchnittstelleService";
import Schnittstelle from "@/types/Schnittstelle";
import EditSchnittstelleDialog from "@/components/EditSchnittstelleDialog.vue";

const snackbarStore = useSnackbarStore();
let schnittstelleID = useRouter().currentRoute.params.id;
const showAddPersonDialog = ref(false);
const zuordnungen = ref<Zuordnung[]>([]);
const showYesNoDialog = ref(false);
const showEditSchnittstelleDialog = ref(false);

let zuordnungToDeleteId: string | undefined = undefined;
const schnittstelle = ref<Schnittstelle>({
    name: "",
    anlagedatum: "",
    id: schnittstelleID,
});

onMounted(() => {
    HealthService.checkHealth().catch((error) => {
        snackbarStore.showMessage(error);
    });
    refreshTasks();
    getSchnittstelle();
});

function tryToDeleteZuordnung(zuordnung: Zuordnung) {
    zuordnungToDeleteId = zuordnung.id;
}

function deleteZuordnung() {
    ZuordnungService.delete(zuordnungToDeleteId)
        .then(() => {
            refreshTasks();
        })
        .finally(() => {
            showYesNoDialog.value = false;
        });
}

function saveZuordnung(zuordnung: Zuordnung) {
    zuordnung.schnittstelle = schnittstelleID;
    ZuordnungService.create(zuordnung).then(() => {
        refreshTasks();
    });
}

function refreshTasks() {
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
        }
    );
}
</script>
