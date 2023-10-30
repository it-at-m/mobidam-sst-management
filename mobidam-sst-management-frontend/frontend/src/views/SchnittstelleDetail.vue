<template>
    <v-container>
        <h1>Schnittstelle {{ interfaceID }}</h1>
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
                v-for="n in 5"
                :key="n"
            >
                <v-col> Person {{ n }} </v-col>
                <v-col>
                    <v-icon>mdi-delete</v-icon>
                </v-col>
            </v-list-item>
        </v-list>
        <add-person-dialog
            :show-dialog.sync="showAddPersonDialog"
        ></add-person-dialog>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import HealthState from "@/types/HealthState";
import AddPersonDialog from "@/components/AddPersonDialog.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router/composables";

const snackbarStore = useSnackbarStore();
const status = ref("DOWN");
let interfaceID = useRouter().currentRoute.params.id;
const showAddPersonDialog = ref(false);

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
