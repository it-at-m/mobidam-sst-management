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
        <v-row>
            <v-col>
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
                        {{ schnittstelle.name }}
                    </v-list-item>
                </v-list>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import HealthState from "@/types/HealthState";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted, ref } from "vue";
import Schnittstelle from "@/types/Schnittstelle";
import SchnittstelleService from "@/api/SchnittstelleService";

const snackbarStore = useSnackbarStore();
const status = ref("DOWN");
const schnittstellen = ref<Schnittstelle[]>([]);

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
        schnittstellen.value = [...fetchedSchnittstellen];
    });
}
</script>

