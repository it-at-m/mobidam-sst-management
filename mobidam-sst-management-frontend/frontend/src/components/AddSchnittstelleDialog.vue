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
        :value="props.showDialog"
        max-width="60%"
        @input="closeDialog"
    >
        <v-card :style="{ overflowX: 'hidden' }">
            <v-card-title class="title-content">
                <span class="text-h5 mb-2">Schnittstelle hinzufügen</span>
            </v-card-title>

            <v-divider class="divider"></v-divider>
            <v-card-text>
                <v-form ref="form">
                    <v-text-field
                        ref="name"
                        v-model="schnittstelle.name"
                        label="Name der Schnittstelle"
                        :rules="textInputRules"
                    >
                    </v-text-field>
                    <v-text-field
                        ref="creationDate"
                        :value="today.toLocaleDateString()"
                        label="Anlagedatum"
                        hint="Als Anlagedatum wird automatisch der heutige Tag gesetzt."
                        readonly
                    ></v-text-field>
                    <v-text-field
                        ref="address"
                        v-model="schnittstelle.editDate"
                        label="Änderungsdatum"
                        hint="Wird beim Bearbeiten automatisch gesetzt."
                        readonly
                        :disabled="true"
                    ></v-text-field>
                    &nbsp;
                    <v-row>
                        <v-col cols="3">
                            <v-switch
                                ref="status"
                                v-model="schnittstelle.status"
                                :label="`Status der Schnittstelle: ${schnittstelle.status}`"
                                true-value="AKTIVIERT"
                                false-value="DEAKTIVIERT"
                                color="success"
                            ></v-switch>
                        </v-col>
                        <v-col>
                            <v-textarea
                                ref="explanantion"
                                v-model="schnittstelle.explanation"
                                label="Begründung der Statussetzung"
                                :rules="textMaxLength"
                                outlined
                            ></v-textarea>
                        </v-col>
                    </v-row>
                </v-form>
            </v-card-text>
            <v-divider class="divider"></v-divider>
            <v-card-actions>
                <v-btn @click="closeDialog">Schließen</v-btn>
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
import { ref } from "vue";
import { useRules } from "@/composables/rules";
import SchnittstelleService from "@/api/SchnittstelleService";
import Schnittstelle from "@/types/Schnittstelle";

const textMaxLength = ref<number>(255);
const validationRules = useRules();
const textInputRules = [
    validationRules.notEmptyRule("Das Feld darf nicht leer sein."),
    validationRules.maxLengthRule(
        textMaxLength.value,
        "Die Eingabe darf maximal " + textMaxLength + " Zeichen lang sein."
    ),
];
const today = ref<Date>(new Date());

interface Props {
    showDialog: boolean;
}

const props = withDefaults(defineProps<Props>(), {
    showDialog: false,
});

const schnittstelle = ref<Schnittstelle>(
    new Schnittstelle("", today.value.toISOString(), "DEAKTIVIERT")
);

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
    (e: "schnittstelle-saved"): void;
}>();

const form = ref<HTMLFormElement>();

function saveTask(): void {
    if (form.value?.validate()) {
        SchnittstelleService.create(schnittstelle.value)
            .then(() => {
                closeDialog();
                resetSchnittstelle();
                emit("schnittstelle-saved");
            })
            .finally(() => {
                form.value?.resetValidation();
            });
    }
}

function resetSchnittstelle(): void {
    schnittstelle.value = new Schnittstelle(
        "",
        today.value.toISOString(),
        "DEAKTIVIERT"
    );
    form.value?.resetValidation();
}

function closeDialog() {
    emit("update:showDialog", false);
    resetSchnittstelle();
}
</script>

<style>
@import "@/style/AddDialog.css";
</style>
