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
    <v-dialog
        :model-value="dialogProps.showDialog"
        max-width="60%"
        @update:model-value="closeDialog"
    >
        <v-card :style="{ overflowX: 'hidden' }">
            <v-card-title class="title-content">
                <span class="text-h5 mb-2">Person hinzufügen</span>
            </v-card-title>

            <v-divider class="divider"></v-divider>
            <v-card-text>
                <v-form
                    ref="form"
                    @submit.prevent
                >
                    <v-text-field
                        ref="person"
                        v-model="zuordnung.benutzerkennung"
                        label="Benutzerkennung"
                        hint="Welche Person soll der
                    Schnittstelle zugewiesen werden?"
                        :rules="textInputRules"
                    >
                    </v-text-field>
                    <v-text-field
                        ref="fachbereich"
                        v-model="zuordnung.fachbereich"
                        label="Fachbereich"
                        hint="Welchem Fachbereich ist die betreffende Person zugeordnet?"
                        :counter="textMaxLength"
                        :maxlength="textMaxLength"
                        :minlength="textMinLength"
                        :rules="textInputRules"
                    ></v-text-field>
                    <v-text-field
                        ref="address"
                        v-model="zuordnung.funktionsadresse"
                        label="Funktionspostfach"
                        hint="Wie lautet das Funktionspostfach?"
                        placeholder="[Ändere mich]@muenchen.de"
                        :counter="textMaxLength"
                        :maxlength="textMaxLength"
                        :minlength="textMinLength"
                        :rules="emailInputRules"
                    ></v-text-field>
                    &nbsp;
                    <v-row>
                        <v-col>
                            <v-menu
                                v-model="gueltigAbMenu"
                                max-width="100%"
                                :close-on-content-click="false"
                            >
                                <template #activator="{ props }">
                                    <v-text-field
                                        v-model="zuordnung.gueltigAb"
                                        label="Gültig ab"
                                        readonly
                                        variant="outlined"
                                        :rules="textInputRules"
                                        v-bind="props"
                                        @click="gueltigAbMenu = true"
                                    ></v-text-field>
                                </template>
                                <v-locale-provider locale="de">
                                    <v-date-picker
                                        v-model="gueltigAb"
                                        title="Datum auswählen"
                                        color="primary"
                                        header-color="primary"
                                        :min="today"
                                        @update:model-value="updateGueltigAb"
                                    >
                                    </v-date-picker>
                                </v-locale-provider>
                            </v-menu>
                        </v-col>
                        <v-col>
                            <v-menu
                                v-model="gueltigBisMenu"
                                max-width="100%"
                                :close-on-content-click="false"
                            >
                                <template #activator="{ props }">
                                    <v-text-field
                                        v-model="zuordnung.gueltigBis"
                                        label="Gültig bis"
                                        readonly
                                        variant="outlined"
                                        clearable
                                        v-bind="props"
                                        :rules="[
                                            validationRules.isGueltigAbBeforeGueltigBis(
                                                zuordnung.gueltigAb,
                                                zuordnung.gueltigBis,
                                                'Gültig-ab muss kleiner gleich Gültig-bis sein.'
                                            ),
                                        ]"
                                        @click="gueltigBisMenu = true"
                                    ></v-text-field>
                                </template>
                                <v-locale-provider locale="de">
                                    <v-date-picker
                                        v-model="gueltigBis"
                                        title="Datum auswählen"
                                        color="primary"
                                        header-color="primary"
                                        :min="today"
                                        @update:model-value="updateGueltigBis"
                                    >
                                    </v-date-picker>
                                </v-locale-provider>
                            </v-menu>
                        </v-col>
                    </v-row>
                    &nbsp;
                    <v-divider class="divider"></v-divider>
                    <v-card-actions>
                        <v-btn @click="closeDialog">Abbrechen</v-btn>
                        <v-spacer></v-spacer>
                        <v-btn
                            class="text-white"
                            color="success"
                            type="submit"
                            @click="saveTask"
                        >
                            {{ confirmButton }}
                        </v-btn>
                    </v-card-actions>
                </v-form>
            </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import Zuordnung from "@/types/Zuordnung";
import { ref } from "vue";
import { useRules } from "@/composables/rules";
import type { VForm } from "vuetify/components";

const textMaxLength = ref<number>(255);
const textMinLength = ref<number>(1);
const today = ref(formatDate(new Date().toLocaleDateString()));
const validationRules = useRules();
const textInputRules = [
    validationRules.notEmptyRule("Das Feld darf nicht leer sein."),
    validationRules.maxLengthRule(
        textMaxLength.value,
        "Die Eingabe darf maximal " +
            textMaxLength.value +
            " Zeichen lang sein."
    ),
];
const emailInputRules = textInputRules.concat([
    validationRules.isValidEmail("Kein gültiges EMail Format."),
]);

const gueltigBisMenu = ref(false);
const gueltigBis = ref(new Date());
const gueltigAbMenu = ref(false);
const gueltigAb = ref(new Date());

interface Props {
    showDialog: boolean;
    confirmButton: string;
}

const dialogProps = withDefaults(defineProps<Props>(), {
    showDialog: false,
});

const zuordnung = ref<Zuordnung>(new Zuordnung("", "", "", "", "", ""));

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
    (e: "zuordnung-saved", b: Zuordnung): void;
}>();

const form = ref<VForm>();

async function saveTask() {
    // eslint-disable-next-line no-unsafe-optional-chaining
    const valid = (await form.value?.validate())?.valid;
    if (valid) {
        emit("zuordnung-saved", zuordnung.value);
        closeDialog();
    }
}

function closeDialog() {
    emit("update:showDialog", false);
    zuordnung.value = new Zuordnung("", "", "", "", "", "");
    form.value?.resetValidation();
    form.value?.reset();
}

function updateGueltigBis(date: Date) {
    gueltigBisMenu.value = false;
    zuordnung.value.gueltigBis = formatDate(date.toLocaleDateString());
}

function updateGueltigAb(date: Date) {
    gueltigAbMenu.value = false;
    zuordnung.value.gueltigAb = formatDate(date.toLocaleDateString());
}

function formatDate(localeDate: string): string {
    const dateParts = localeDate.split(".");
    return (
        dateParts[2] +
        "-" +
        (dateParts[1].length == 1 ? "0" : "") +
        dateParts[1] +
        "-" +
        (dateParts[0].length == 1 ? "0" : "") +
        dateParts[0]
    );
}
</script>

<style>
@import "@/style/AddDialog.css";
</style>
