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
                <v-form ref="form">
                    <v-text-field
                        ref="person"
                        v-model="zuordnung.benutzerkennung"
                        label="Person angeben"
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
                        label="Funktionsadresse"
                        hint="Welchem Gruppenpostfach gehört diese Person an?"
                        :counter="textMaxLength"
                        :maxlength="textMaxLength"
                        :minlength="textMinLength"
                        :rules="textInputRules"
                    ></v-text-field>
                    &nbsp;
                    <v-row>
                        <v-col>
                            <v-menu
                                max-width="100%"
                                :close-on-content-click="true"
                            >
                                <template #activator="{ props }">
                                    <v-text-field
                                        v-model="zuordnung.gueltigAb"
                                        label="Gültig ab"
                                        readonly
                                        variant="outlined"
                                        :rules="textInputRules"
                                        v-bind="props"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="zuordnung.gueltigAb"
                                    color="primary"
                                    header-color="primary"
                                    :min="today"
                                >
                                </v-date-picker>
                            </v-menu>
                        </v-col>
                        <v-col>
                            <v-menu
                                max-width="100%"
                                :close-on-content-click="true"
                            >
                                <template #activator="{ props }">
                                    <v-text-field
                                        v-model="zuordnung.gueltigBis"
                                        label="Gültig bis"
                                        readonly
                                        variant="outlined"
                                        :rules="textInputRules"
                                        v-bind="props"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="zuordnung.gueltigBis"
                                    color="primary"
                                    header-color="primary"
                                    :min="today"
                                >
                                </v-date-picker>
                            </v-menu>
                        </v-col>
                    </v-row>
                </v-form>
            </v-card-text>
            <v-divider class="divider"></v-divider>
            <v-card-actions>
                <v-btn @click="closeDialog">Schließen</v-btn>
                <v-spacer></v-spacer>
                <v-btn
                    class="text-white"
                    color="success"
                    @click="saveTask"
                >
                    {{ confirmButton }}
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import Zuordnung from "@/types/Zuordnung";
import { ref } from "vue";
import { useRules } from "@/composables/rules";

const textMaxLength = ref<number>(255);
const textMinLength = ref<number>(1);
const today = ref<string>(new Date().toISOString());
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

const form = ref<HTMLFormElement>();

function saveTask(): void {
    if (form.value?.validate()) {
        emit("zuordnung-saved", zuordnung.value);
        closeDialog();
        resetZuordnung();
        form.value?.resetValidation();
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
