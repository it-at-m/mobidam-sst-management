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
            <h2>Schnittstellen</h2>
        </v-row>
        &nbsp;
        <v-list lines="two">
            <v-list-item
                v-for="schnittstelle in schnittstellen"
                :key="schnittstelle.id"
                @click="
                    $router.push({
                        name: 'schnittstelleDetail',
                        params: {
                            id: schnittstelle.id,
                            schnittstelleName: schnittstelle.name,
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
                            v-if="schnittstelle.editDate"
                            v-on="on"
                        >
                            <v-icon>mdi-calendar-edit</v-icon>
                            {{ schnittstelle.editDate }}
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
                            <v-icon v-if="schnittstelle.status == 'AKTIVIERT'"
                                >mdi-check</v-icon
                            >
                            <v-icon v-else>mdi-window-close</v-icon>
                            {{ schnittstelle.status }}
                        </v-col>
                        <v-col
                            v-else
                            v-on="on"
                        >
                            <v-icon>mdi-window-close</v-icon>
                            -
                        </v-col>
                    </template>
                    Status
                </v-tooltip>

                <v-tooltip left>
                    <template #activator="{ on }">
                        <v-col v-on="on">
                            <v-icon>mdi-google-analytics</v-icon>
                            {{ schnittstelle.datentransfer.ereignis }}
                        </v-col>
                    </template>
                    Letzter Datentransfer
                </v-tooltip>

                <v-tooltip left>
                    <template #activator="{ on }">
                        <v-col
                            v-if="
                                schnittstelle.datentransfer.zeitstempel !== '-'
                            "
                            v-on="on"
                        >
                            {{ schnittstelle.datentransfer.zeitstempel }}
                        </v-col>
                        <v-col
                            v-else
                            v-on="on"
                        />
                    </template>
                    Zeitstempel des letzten Datentransfers
                </v-tooltip>
            </v-list-item>
        </v-list>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import HealthState from "@/types/HealthState";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted, ref } from "vue";
import SchnittstelleService from "@/api/SchnittstelleService";
import Datentransfer from "@/types/Datentransfer";
import DatentransferService from "@/api/DatentransferService";
import SchnittstelleWithDatentransfer from "@/types/SchnittstelleWithDatentransfer";

const snackbarStore = useSnackbarStore();
const status = ref("DOWN");
const schnittstellen = ref<SchnittstelleWithDatentransfer[]>([]);

onMounted(() => {
    HealthService.checkHealth()
        .then((content: HealthState) => (status.value = content.status))
        .catch((error) => {
            snackbarStore.showMessage(error);
        });
    getSchnittstellen();
});

function getSchnittstellen() {
    SchnittstelleService.getAllSchnittstelle().then((fetchedSchnittstellen) => {
        let datentransfer: Datentransfer = new Datentransfer(
            "-",
            "-",
            "-",
            "-",
            "-"
        );
        for (const fetchedSchnittstelle of fetchedSchnittstellen) {
            const schnittstelle: SchnittstelleWithDatentransfer =
                new SchnittstelleWithDatentransfer(
                    fetchedSchnittstelle.name,
                    fetchedSchnittstelle.creationDate,
                    fetchedSchnittstelle.id,
                    datentransfer,
                    fetchedSchnittstelle.editDate,
                    fetchedSchnittstelle.status,
                    fetchedSchnittstelle.explanation
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
</script>

