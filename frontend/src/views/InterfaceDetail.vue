<template>
    <v-container>
        Dummy Daten über die Schnittstelle
        <v-text-field
            prepend-icon="mdi-account-plus"
            label="zuzuordnende Person"
            type="person"
            hint="Gib den Namen einer Person an, die Du der Schnittstelle zuordnen möchstest "
        ></v-text-field>
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
