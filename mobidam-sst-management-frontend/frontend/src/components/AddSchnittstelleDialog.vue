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
                        v-model="schnittstelleRequest.name"
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
                    &nbsp;
                    <v-row>
                        <v-col cols="3">
                            <v-switch
                                ref="status"
                                v-model="schnittstelleRequest.status"
                                :label="`Status der Schnittstelle: ${schnittstelleRequest.status}`"
                                true-value="AKTIVIERT"
                                false-value="DEAKTIVIERT"
                                color="success"
                            ></v-switch>
                        </v-col>
                        <v-col>
                            <v-textarea
                                ref="explanantion"
                                v-model="schnittstelleRequest.explanation"
                                label="Begründung der Statussetzung"
                                outlined
                                :maxlength="255"
                            ></v-textarea>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="3">
                            <v-tooltip right>
                                <template #activator="{ on }">
                                    <v-btn
                                        small
                                        outlined
                                        @click="showAddPersonDialog = true"
                                        v-on="on"
                                    >
                                        <v-icon>mdi-account-plus</v-icon>
                                    </v-btn>
                                </template>
                                Fachverantwortliche hinzufügen
                            </v-tooltip>
                        </v-col>
                        <v-col>
                            <v-chip
                                v-for="zuordnung in zuordnungen"
                                :key="zuordnung"
                                close
                                style="margin-right: 1%"
                                @click:close="removeZuordnung(zuordnung)"
                            >
                                {{ zuordnung.userID }}
                            </v-chip>
                            <br />
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
                    @click="saveSchnittstelle"
                >
                    Speichern
                </v-btn>
            </v-card-actions>
        </v-card>
        <add-person-dialog
            :show-dialog.sync="showAddPersonDialog"
            confirm-button="Übernehmen"
            @zuordnung-saved="confirmZuordnung"
        ></add-person-dialog>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRules } from "@/composables/rules";
import SchnittstelleService from "@/api/SchnittstelleService";
import Schnittstelle from "@/types/Schnittstelle";
import AddPersonDialog from "@/components/AddPersonDialog.vue";
import Zuordnung from "@/types/Zuordnung";
import ZuordnungService from "@/api/ZuordnungService";
import SchnittstelleRequest from "@/types/SchnittstelleRequest";

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
const showAddPersonDialog = ref(false);
const zuordnungen = ref<Zuordnung[]>([]);

interface Props {
    showDialog: boolean;
}

const props = withDefaults(defineProps<Props>(), {
    showDialog: false,
});

const schnittstelleRequest = ref<SchnittstelleRequest>(
    new SchnittstelleRequest("", "DEAKTIVIERT")
);

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
    (e: "schnittstelle-saved"): void;
}>();

const form = ref<HTMLFormElement>();

function saveSchnittstelle(): void {
    if (form.value?.validate()) {
        SchnittstelleService.create(schnittstelleRequest.value)
            .then((schnittstelle) => {
                saveZuordnungen(schnittstelle);
            })
            .finally(() => {
                emit("schnittstelle-saved");
                resetSchnittstelle();
                closeDialog();
                form.value?.resetValidation();
            });
    }
}

function confirmZuordnung(zuordnung: Zuordnung): void {
    zuordnungen.value.push(zuordnung);
}

function saveZuordnungen(schnittstelle: Schnittstelle) {
    for (const zuordnung of zuordnungen.value) {
        if (schnittstelle.id !== undefined)
            zuordnung.schnittstelle = schnittstelle.id;
        ZuordnungService.create(zuordnung);
    }
}

function removeZuordnung(zuordnung: Zuordnung): void {
    zuordnungen.value.splice(zuordnungen.value.indexOf(zuordnung), 1);
}

function resetSchnittstelle(): void {
    schnittstelleRequest.value = new SchnittstelleRequest(
        "",
        "DEAKTIVIERT"
    );
    zuordnungen.value = [];
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
