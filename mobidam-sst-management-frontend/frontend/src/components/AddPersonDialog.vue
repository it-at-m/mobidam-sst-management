<template>
    <v-dialog
        :value="props.showDialog"
        max-width="60%"
        @input="closeDialog"
    >
        <v-card :style="{ overflowX: 'hidden' }">
            <v-card-title class="title-content">
                <span class="text-h5 mb-2">Person hinzufügen</span>
            </v-card-title>

            <v-divider class="divider"></v-divider>
            <v-card-text>
                <v-form ref="form">
                    <v-text-field
                        ref="person"
                        v-model="newZuordnung.userID"
                        label="Person angeben"
                        hint="Welche Person soll der
                    Schnittstelle zugewiesen werden?"
                        :rules="textInputRules"
                    >
                    </v-text-field>
                    <v-text-field
                        ref="department"
                        v-model="newZuordnung.department"
                        label="Fachbereich"
                        hint="Welchem Fachbereich ist die betreffende Person zugeordnet?"
                        counter="255"
                        maxlength="255"
                        minlength="1"
                        :rules="textInputRules"
                    ></v-text-field>
                    <v-text-field
                        ref="address"
                        v-model="newZuordnung.functionAddress"
                        label="Funktionsadresse"
                        hint="Welchem Gruppenpostfach gehört diese Person an?"
                        counter="255"
                        maxlength="255"
                        minlength="1"
                        :rules="textInputRules"
                    ></v-text-field>
                    <v-row>
                        <v-col>
                            <v-menu
                                max-width="100%"
                                :close-on-content-click="true"
                            >
                                <template #activator="{ on }">
                                    <v-text-field
                                        v-model="newZuordnung.validFrom"
                                        label="Gültig ab"
                                        readonly
                                        :rules="textInputRules"
                                        v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="newZuordnung.validFrom"
                                    color="primary"
                                    header-color="primary"
                                    :first-day-of-week="1"
                                    :min="new Date().toISOString()"
                                ></v-date-picker>
                            </v-menu>
                        </v-col>
                        <v-col>
                            <v-menu
                                max-width="100%"
                                :close-on-content-click="true"
                            >
                                <template #activator="{ on }">
                                    <v-text-field
                                        v-model="newZuordnung.validUntil"
                                        label="Gültig bis"
                                        readonly
                                        :rules="textInputRules"
                                        v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="newZuordnung.validUntil"
                                    color="primary"
                                    header-color="primary"
                                    :first-day-of-week="1"
                                    :min="new Date().toISOString()"
                                ></v-date-picker>
                            </v-menu>
                        </v-col>
                    </v-row>
                </v-form>
            </v-card-text>
            <v-divider class="divider"></v-divider>
            <v-card-actions>
                <v-btn
                    text
                    @click="closeDialog"
                    >Schließen</v-btn
                >
                <v-spacer></v-spacer>
                <v-btn
                    class="white--text"
                    color="success"
                    @click="saveTask"
                >
                    Speichern
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { useSnackbarStore } from "@/stores/snackbar";
import Zuordnung from "@/types/Zuordnung";
import { ref, reactive } from "vue";
import ZuordnungService from "@/api/ZuordnungService";
import { Levels } from "@/api/error";
import { useRouter } from "vue-router/composables";
import { useRules } from "@/composables/rules";

const maxLength = 255;
const validationRules = useRules();
const textInputRules = [
    validationRules.notEmptyRule("Das Feld darf nicht leer sein."),
    validationRules.maxLengthRule(
        maxLength,
        "Die Eingabe darf maximal " + maxLength + " Zeichen lang sein."
    ),
];

interface Props {
    showDialog: boolean;
}

const props = withDefaults(defineProps<Props>(), {
    showDialog: false,
});

const newZuordnung: Zuordnung = reactive({
    schnittstelle: "",
    userID: "",
    department: "",
    functionAddress: "",
    validFrom: "",
    validUntil: "",
});

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
    (e: "zuordnung-saved"): void;
}>();

const form = ref<HTMLFormElement>();
let schnittstelleID = useRouter().currentRoute.params.id;

function saveTask(): void {
    if (form.value?.validate()) {
        newZuordnung.schnittstelle = schnittstelleID;
        ZuordnungService.create(newZuordnung)
            .then(() => {
                closeDialog();
                resetZuordnung();
                emit("zuordnung-saved");
            })
            .finally(() => {
                form.value?.resetValidation();
            });
    }
}

function resetZuordnung(): void {
    newZuordnung.schnittstelle = "";
    newZuordnung.userID = "";
    newZuordnung.department = "";
    newZuordnung.functionAddress = "";
    newZuordnung.validFrom = "";
    newZuordnung.validUntil = "";
}

function closeDialog() {
    emit("update:showDialog", false);
    form.value?.resetValidation();
}
</script>

<style scoped>
.divider {
    background-color: #395b64;
}
.title-content {
    margin-right: 8%;
}
</style>
