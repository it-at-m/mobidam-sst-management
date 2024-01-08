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
                        v-for="n in 5"
                        :key="n"
                        @click="$router.push(`/schnittstelleDetailView/${n}`)"
                    >
                        Schnittstelle {{ n }}
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

const snackbarStore = useSnackbarStore();
const status = ref("DOWN");

onMounted(() => {
    HealthService.checkHealth()
        .then((content: HealthState) => (status.value = content.status))
        .catch((error) => {
            snackbarStore.showMessage(error);
        });
});
</script>

<style scoped>
.UP {
    color: limegreen;
}

.DOWN {
    color: lightcoral;
}
</style>
