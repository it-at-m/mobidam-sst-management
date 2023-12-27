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
                        v-model="zuordnung.userID"
                        label="Person angeben"
                        hint="Welche Person soll der
                    Schnittstelle zugewiesen werden?"
                        :rules="textInputRules"
                    >
                    </v-text-field>
                    <v-text-field
                        ref="department"
                        v-model="zuordnung.department"
                        label="Fachbereich"
                        hint="Welchem Fachbereich ist die betreffende Person zugeordnet?"
                        :counter="textMaxLength"
                        :maxlength="textMaxLength"
                        :minlength="textMinLength"
                        :rules="textInputRules"
                    ></v-text-field>
                    <v-text-field
                        ref="address"
                        v-model="zuordnung.functionAddress"
                        label="Funktionsadresse"
                        hint="Welchem Gruppenpostfach gehört diese Person an?"
                        :counter="textMaxLength"
                        :maxlength="textMaxLength"
                        :minlength="textMinLength"
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
                                        v-model="zuordnung.validFrom"
                                        label="Gültig ab"
                                        readonly
                                        :rules="textInputRules"
                                        v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="zuordnung.validFrom"
                                    color="primary"
                                    header-color="primary"
                                    :first-day-of-week="1"
                                    :min="today"
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
                                        v-model="zuordnung.validUntil"
                                        label="Gültig bis"
                                        readonly
                                        :rules="textInputRules"
                                        v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="zuordnung.validUntil"
                                    color="primary"
                                    header-color="primary"
                                    :first-day-of-week="1"
                                    :min="today"
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
import Zuordnung from "@/types/Zuordnung";
import { ref } from "vue";
import ZuordnungService from "@/api/ZuordnungService";
import { useRules } from "@/composables/rules";

const textMaxLength = ref<number>(255);
const textMinLength = ref<number>(1);
const today = ref<string>(new Date().toISOString());
const validationRules = useRules();
const textInputRules = [
    validationRules.notEmptyRule("Das Feld darf nicht leer sein."),
    validationRules.maxLengthRule(
        textMaxLength.value,
        "Die Eingabe darf maximal " + textMaxLength + " Zeichen lang sein."
    ),
];

interface Props {
    showDialog: boolean;
    schnittstelleID: string;
}

const props = withDefaults(defineProps<Props>(), {
    showDialog: false,
});

const zuordnung = ref<Zuordnung>(new Zuordnung("", "", "", "", "", ""));

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
    (e: "zuordnung-saved"): void;
}>();

const form = ref<HTMLFormElement>();

function saveTask(): void {
    if (form.value?.validate()) {
        zuordnung.value.schnittstelle = props.schnittstelleID;
        ZuordnungService.create(zuordnung.value)
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
  zuordnung.value = new Zuordnung("", "", "", "", "", "");
  form.value?.resetValidation();
}

function closeDialog() {
    emit("update:showDialog", false);
    form.value?.resetValidation();
}
</script>

<style>

@import "@/style/AddDialog.css";

</style>
