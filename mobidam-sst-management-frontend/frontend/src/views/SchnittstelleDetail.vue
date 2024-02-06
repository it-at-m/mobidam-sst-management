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
        <h1>Schnittstelle {{ props.schnittstelleName }}</h1>
        <v-row>
            <v-col>
                <h3>
                    Zugewiesene Personen &nbsp;
                    <v-btn
                        small
                        outlined
                        @click="showAddPersonDialog = true"
                    >
                        <v-icon>mdi-account-plus</v-icon>
                    </v-btn>
                </h3>
            </v-col>
        </v-row>
        <v-list lines="two">
            <v-list-item
                v-for="zuordnung in zuordnungen"
                :key="zuordnung.id"
            >
                <v-col>
                    <v-icon>mdi-account</v-icon> {{ zuordnung.userID }}
                </v-col>
                <v-col>
                    <v-icon>mdi-home</v-icon> {{ zuordnung.department }}</v-col
                >
                <v-col>
                    <v-icon>mdi-email</v-icon>
                    {{ zuordnung.functionAddress }}</v-col
                >
                <v-col>
                    <v-icon>mdi-calendar-start</v-icon>
                    {{ zuordnung.validFrom }}
                </v-col>
                <v-col>
                    <v-icon>mdi-calendar-end</v-icon>
                    {{ zuordnung.validUntil }}
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
        <add-person-dialog
            :show-dialog.sync="showAddPersonDialog"
            :schnittstelle-i-d="schnittstelleID"
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
import AddPersonDialog from "@/components/AddPersonDialog.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router/composables";
import ZuordnungService from "@/api/ZuordnungService";
import Zuordnung from "@/types/Zuordnung";
import YesNoDialog from "@/components/common/YesNoDialog.vue";

const snackbarStore = useSnackbarStore();
let schnittstelleID = useRouter().currentRoute.params.id;
const showAddPersonDialog = ref(false);
const zuordnungen = ref<Zuordnung[]>([]);
const showYesNoDialog = ref(false);

let zuordnungToDeleteId: string | undefined = undefined;

interface Props {
    schnittstelleName: string;
}

const props = withDefaults(defineProps<Props>(), {
    schnittstelleName: "-",
});

onMounted(() => {
    HealthService.checkHealth().catch((error) => {
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
            refreshTasks();
        })
        .finally(() => {
            showYesNoDialog.value = false;
        });
}

function refreshTasks() {
    ZuordnungService.getZuordnungenByID(schnittstelleID).then(
        (fetchedZuordnungen) => {
            zuordnungen.value = [...fetchedZuordnungen];
        }
    );
}
</script>
