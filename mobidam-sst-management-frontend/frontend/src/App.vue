<!--

    Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
    der Landeshauptstadt München, 2024

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
