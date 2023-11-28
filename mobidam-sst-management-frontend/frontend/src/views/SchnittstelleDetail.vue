<template>
    <v-container>
        <h1>Schnittstelle {{ schnittstelleID }}</h1>
        Hier findet man später weitere Informationen zur Schnittstelle.
        <v-row>
            <v-col> <h3>Zugewiesene Personen</h3></v-col>
            <v-col>
                <v-btn
                    small
                    @click="showAddPersonDialog = true"
                >
                    <v-icon>mdi-account-plus</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <v-list lines="two">
            <v-list-item
                v-for="zuordnung in zuordnungen"
                :key="zuordnung.id"
            >
                <v-col> {{ zuordnung.userID }} </v-col>
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
        <add-person-dialog
            :show-dialog.sync="showAddPersonDialog"
            @zuordnung-saved="refreshTasks"
        ></add-person-dialog>
        <yes-no-dialog
            dialogtext="Sicher, dass Du die Zuordnung löschen möchtest?"
            dialogtitle="Zuordnung löschen"
            :value.sync="showYesNoDialog"
            @no="showYesNoDialog = false"
            @yes="deleteZuordnung"
        ></yes-no-dialog>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import HealthState from "@/types/HealthState";
import AddPersonDialog from "@/components/AddPersonDialog.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router/composables";
import ZuordnungService from "@/api/ZuordnungService";
import Zuordnung from "@/types/Zuordnung";
import { Levels } from "@/api/error";
import YesNoDialog from "@/components/common/YesNoDialog.vue";

const snackbarStore = useSnackbarStore();
const status = ref("DOWN");
let schnittstelleID = useRouter().currentRoute.params.id;
const showAddPersonDialog = ref(false);
const zuordnungen = ref<Zuordnung[]>([]);
const showYesNoDialog = ref(false);

let zuordnungToDeleteId: string | undefined = undefined;

onMounted(() => {
    HealthService.checkHealth()
        .then((content: HealthState) => (status.value = content.status))
        .catch((error) => {
            snackbarStore.showMessage(error);
        });
    refreshTasks();
});

function tryToDeleteZuordnung(zuordnung: Zuordnung) {
    zuordnungToDeleteId = zuordnung.id;
}

function deleteZuordnung() {
    ZuordnungService.delete(zuordnungToDeleteId)
        .then(() => {
            useSnackbarStore().showMessage({
                message: "Zuordnung wurde erfolgreich gelöscht.",
                level: Levels.INFO,
            });
            refreshTasks();
        })
        .catch(() => {
            useSnackbarStore().showMessage({
                message: "Es gab einen Fehler beim Löschen der Zuordnung.",
                level: Levels.ERROR,
            });
        })
        .finally(() => {
            showYesNoDialog.value = false;
        });
}

function refreshTasks() {
    ZuordnungService.getZuordnungenByID(schnittstelleID)
        .then((fetchedZuordnungen) => {
            zuordnungen.value = [...fetchedZuordnungen];
        })
        .catch(() => {
            useSnackbarStore().showMessage({
                message: "Es gab einen Fehler beim Laden der Zuordnungen!",
                level: Levels.ERROR,
            });
        });
}
</script>

<style scoped>
.UP {
    color: limegreen;
}

.DOWN {
    color: lightcoral;
}
</style>
