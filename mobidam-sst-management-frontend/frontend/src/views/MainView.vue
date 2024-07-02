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
        <v-row class="text-center">
            <v-col class="mb-4">
                <h1 class="text-h3 font-weight-bold mb-3">
                    Willkommen bei MobidaM
                </h1>
            </v-col>
        </v-row>
        <v-row>
            <h2>
                Schnittstellen &nbsp;
                <v-btn
                    small
                    outlined
                    @click="showAddSchnittstelleDialog = true"
                >
                    <v-icon>mdi-plus</v-icon>
                </v-btn>
            </h2>
        </v-row>
        <br />
        <v-list>
            <v-list-item
                v-for="schnittstelle in sortedSchnittstellen"
                :key="schnittstelle.id"
                @click="
                    $router.push({
                        name: 'schnittstelleDetail',
                        params: {
                            id: schnittstelle.id,
                        },
                    })
                "
            >
                <v-tooltip top>
                    <template #activator="{ on }">
                        <v-col v-on="on">
                            {{ schnittstelle.name }}
                        </v-col>
                    </template>
                    Name der Schnittstelle
                </v-tooltip>
                <v-tooltip top>
                    <template #activator="{ on }">
                        <v-col
                            v-if="schnittstelle.aenderungsdatum"
                            v-on="on"
                        >
                            <v-icon>mdi-calendar-edit</v-icon>
                            {{ schnittstelle.aenderungsdatum }}
                        </v-col>
                        <v-col
                            v-else
                            v-on="on"
                        >
                            <v-icon>mdi-calendar-edit</v-icon>
                            -
                        </v-col>
                    </template>
                    Änderungsdatum
                </v-tooltip>

                <v-tooltip top>
                    <template #activator="{ on }">
                        <v-col
                            v-if="schnittstelle.status"
                            v-on="on"
                        >
                            <v-icon
                                v-if="schnittstelle.status == 'AKTIVIERT'"
                                color="green"
                                >mdi-check</v-icon
                            >
                            <v-icon
                                v-else
                                color="red"
                                >mdi-window-close</v-icon
                            >
                            {{ schnittstelle.status }}
                        </v-col>
                    </template>
                    Status
                </v-tooltip>

                <v-tooltip left>
                    <template #activator="{ on }">
                        <v-col v-on="on">
                            <v-icon>mdi-google-analytics</v-icon>
                            {{ getDatentransferEreignis(schnittstelle) }}
                        </v-col>
                    </template>
                    Letzter Datentransfer
                </v-tooltip>

                <v-tooltip left>
                    <template #activator="{ on }">
                        <v-col v-on="on">
                            {{ getDatentransferZeitstempel(schnittstelle) }}
                        </v-col>
                    </template>
                    Zeitstempel des letzten Datentransfers
                </v-tooltip>
            </v-list-item>
        </v-list>
        <manage-schnittstelle-dialog
            :show-dialog.sync="showAddSchnittstelleDialog"
            :verb="'hinzufügen'"
            :schnittstelle="undefined"
            :zuordnungen="[]"
            :is-edit="false"
            @schnittstelle-saved="refreshList"
        ></manage-schnittstelle-dialog>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted, computed, ref } from "vue";
import SchnittstelleService from "@/api/SchnittstelleService";
import Datentransfer from "@/types/Datentransfer";
import DatentransferService from "@/api/DatentransferService";
import SchnittstelleWithDatentransfer from "@/types/SchnittstelleWithDatentransfer";
import ManageSchnittstelleDialog from "@/components/ManageSchnittstelleDialog.vue";

const snackbarStore = useSnackbarStore();
const showAddSchnittstelleDialog = ref(false);
const schnittstellen = ref<SchnittstelleWithDatentransfer[]>([]);
const sortedSchnittstellen = computed(() => {
    let sorted = ref<SchnittstelleWithDatentransfer[]>([]);
    schnittstellen.value.forEach((schnittstelle) =>
        sorted.value.push(Object.assign({}, schnittstelle))
    );
    return sorted.value.sort((schnittstelle1, schnittstelle2) =>
        schnittstelle1.name.toLowerCase() < schnittstelle2.name.toLowerCase()
            ? -1
            : 1
    );
});

onMounted(() => {
    HealthService.checkHealth().catch((error) => {
        snackbarStore.showMessage(error);
    });
    getSchnittstellen();
});

function getSchnittstellen() {
    SchnittstelleService.getAllSchnittstelle().then((fetchedSchnittstellen) => {
        let datentransfer: Datentransfer | undefined = undefined;
        for (const fetchedSchnittstelle of fetchedSchnittstellen) {
            const schnittstelle: SchnittstelleWithDatentransfer =
                new SchnittstelleWithDatentransfer(
                    fetchedSchnittstelle.name,
                    fetchedSchnittstelle.anlagedatum,
                    fetchedSchnittstelle.id,
                    datentransfer,
                    fetchedSchnittstelle.aenderungsdatum,
                    fetchedSchnittstelle.status,
                    fetchedSchnittstelle.begruendung
                );
            DatentransferService.getLatestResultStateBySchnittstelle(
                fetchedSchnittstelle.id
            ).then((fetchedDatentransfer) => {
                if (fetchedDatentransfer)
                    schnittstelle.datentransfer = fetchedDatentransfer;
                schnittstellen.value.push(schnittstelle);
            });
        }
    });
}

function getDatentransferEreignis(
    schnittstelle: SchnittstelleWithDatentransfer
): string {
    if (schnittstelle.datentransfer)
        return schnittstelle.datentransfer.ereignis;
    return "-";
}

function getDatentransferZeitstempel(
    schnittstelle: SchnittstelleWithDatentransfer
): string {
    if (schnittstelle.datentransfer)
        return schnittstelle.datentransfer.zeitstempel;
    return "";
}

function refreshList(): void {
    schnittstellen.value = [];
    getSchnittstellen();
}
</script>

