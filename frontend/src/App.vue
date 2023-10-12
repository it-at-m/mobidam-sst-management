<template>
    <v-app>
        <the-snackbar />
        <v-app-bar
            app
            clipped-left
            dark
            color="primary"
        >
            <v-row align="center">
                <v-col
                    cols="2"
                    class="d-flex align-center justify-start"
                >
                    <span class="font-weight-bold text-h5">MobidaM</span>
                </v-col>
                <v-spacer />
                <v-col
                    cols="2"
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

const query = ref("");
const appswitcherBaseUrl = ref<string | null>(null);

const route = useRoute();
const snackbarStore = useSnackbarStore();

onMounted(() => {
    /* eslint-disable  @typescript-eslint/no-explicit-any */
    query.value = route.params.query;
    InfoService.getInfo()
        .then((content: any) => {
            appswitcherBaseUrl.value = content.appswitcher.url;
        })
        .catch((error) => {
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
