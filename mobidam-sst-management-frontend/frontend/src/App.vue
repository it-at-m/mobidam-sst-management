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
    <v-app>
        <the-snackbar />
        <v-app-bar
            app
            clipped-left
            dark
            color="primary"
        >
            <v-col
                cols="4"
                class="d-flex align-center justify-start"
            >
                <v-toolbar-title class="font-weight-bold">
                    <span class="white--text">MobidaM</span>
                </v-toolbar-title>
            </v-col>
            <v-row align="center">
                <v-col
                    cols="6"
                    class="d-flex align-center justify-center"
                >
                    <v-img
                        class="my-3"
                        :src="require('./assets/logo.png')"
                        max-height="60"
                        max-width="100"
                        contain
                    ></v-img>
                </v-col>
                <v-col
                    cols="6"
                    class="d-flex align-center justify-end"
                >
                    <v-btn
                        text
                        fab
                    >
                        <v-icon class="white--text">
                            mdi-account-circle
                        </v-icon>
                    </v-btn>
                </v-col>
            </v-row>
        </v-app-bar>
        <v-main>
            <v-container fluid>
                <v-fade-transition mode="out-in">
                    <router-view />
                </v-fade-transition>
            </v-container>
        </v-main>
    </v-app>
</template>

<script setup lang="ts">
import InfoService from "@/api/InfoService";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router/composables";
import { useSnackbarStore } from "@/stores/snackbar";
import TheSnackbar from "@/components/TheSnackbar.vue";

const query = ref("");

const route = useRoute();
const snackbarStore = useSnackbarStore();

onMounted(() => {
    /* eslint-disable  @typescript-eslint/no-explicit-any */
    query.value = route.params.query;
    InfoService.getInfo().catch((error) => {
        snackbarStore.showMessage(error);
    });
    /* eslint-enable  @typescript-eslint/no-explicit-any */
});

watch(
    () => route.params.query,
    (q: string) => {
        if (query.value !== q) {
            query.value = q;
        }
    }
);
</script>

<style>
.main {
    background-color: white;
}
</style>
