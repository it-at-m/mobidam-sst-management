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
        persistent
        @update:model-value="closeDialog"
    >
        <v-card :style="{ overflowX: 'hidden' }">
            <v-card-title class="title-content">
                <span class="text-h5 mb-2"
                    >Schnittstelle {{ dialogProps.verb }}</span
                >
            </v-card-title>

            <v-divider class="divider"></v-divider>
            <v-card-text>
                <v-form ref="form">
                    <v-text-field
                        ref="name"
                        v-model="mutableSchnittstelle.name"
                        label="Name der Schnittstelle"
                        :rules="textInputRules"
                    >
                    </v-text-field>
                    <v-text-field
                        ref="anlagedatum"
                        :model-value="anlagedatum"
                        label="Anlagedatum"
                        hint="Als Anlagedatum wird automatisch der heutige Tag gesetzt."
                        readonly
                    ></v-text-field>
                    <v-text-field
                        ref="aenderungsdatum"
                        :model-value="today.toLocaleDateString()"
                        label="Änderungsdatum"
                        hint="Als Änderungsdatum wird automatisch der heutige Tag gesetzt."
                        readonly
                    ></v-text-field>
                    &nbsp;
                    <v-row>
                        <v-col cols="3">
                            <v-switch
                                ref="status"
                                v-model="mutableSchnittstelle.status"
                                :label="`Status der Schnittstelle: ${mutableSchnittstelle.status}`"
                                true-value="AKTIVIERT"
                                false-value="DEAKTIVIERT"
                                color="success"
                                @update:model-value="resetBegruendung()"
                            ></v-switch>
                        </v-col>
                        <v-col>
                            <v-textarea
                                ref="begruendung"
                                v-model="mutableSchnittstelle.begruendung"
                                label="Begründung der Statussetzung"
                                variant="outlined"
                                :rules="textInputRules"
                            ></v-textarea>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="3">
                            <v-tooltip location="right">
                                <template #activator="{ props }">
                                    <v-btn
                                        size="small"
                                        variant="outlined"
                                        v-bind="props"
                                        @click="showAddPersonDialog = true"
                                    >
                                        <v-icon>mdi-account-plus</v-icon>
                                    </v-btn>
                                </template>
                                Fachverantwortliche hinzufügen
                            </v-tooltip>
                        </v-col>
                        <v-col>
                            <v-chip
                                v-for="zuordnung in mutableZuordnungen"
                                :key="zuordnung.id"
                                closable
                                style="margin-right: 1%"
                                @click:close="removeZuordnung(zuordnung)"
                            >
                                {{ zuordnung.benutzerkennung }}
                            </v-chip>
                            <br />
                        </v-col>
                    </v-row>
                </v-form>
            </v-card-text>
            <v-divider class="divider"></v-divider>
            <v-card-actions>
                <v-btn @click="closeDialog">Abbrechen</v-btn>
                <v-spacer></v-spacer>
                <v-btn
                    class="text-white"
                    color="success"
                    @click="saveSchnittstelle"
                >
                    Speichern
                </v-btn>
            </v-card-actions>
        </v-card>
        <add-person-dialog
            v-model:show-dialog="showAddPersonDialog"
            confirm-button="Übernehmen"
            @zuordnung-saved="confirmZuordnung"
        ></add-person-dialog>
    </v-dialog>
</template>

<script setup lang="ts">
import { computed, onBeforeUpdate, ref } from "vue";
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
        "Die Eingabe darf maximal " +
            textMaxLength.value +
            " Zeichen lang sein."
    ),
];
const today = ref<Date>(new Date());
const showAddPersonDialog = ref(false);
const mutableZuordnungen = ref<Zuordnung[]>([]);
const firstRender = true;

interface Props {
    showDialog: boolean;
    verb: string;
    isEdit: boolean;
    schnittstelle: Schnittstelle;
    zuordnungen: Zuordnung[];
}

const dialogProps = withDefaults(defineProps<Props>(), {
    showDialog: false,
    verb: "",
    isEdit: false,
    schnittstelle: () => new Schnittstelle("", "", "", "DEAKTIVIERT"),
    zuordnungen: () => [],
});

const mutableSchnittstelle = ref<Schnittstelle>(
    new Schnittstelle("", "", "", "DEAKTIVIERT")
);

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
    (e: "schnittstelle-saved"): void;
    (e: "update-exited"): void;
}>();

const form = ref<HTMLFormElement>();

const anlagedatum = computed(() => {
    return dialogProps.isEdit
        ? new Date(mutableSchnittstelle.value.anlagedatum).toLocaleDateString()
        : today.value.toLocaleDateString();
});

onBeforeUpdate(() => {
    mutableZuordnungen.value = [...dialogProps.zuordnungen];
    mutableSchnittstelle.value = dialogProps.schnittstelle;
});

function createSchnittstelle(schnittstelleRequest: SchnittstelleRequest) {
    SchnittstelleService.create(schnittstelleRequest)
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

function updateSchnittstelle() {
    if (form.value?.validate()) {
        SchnittstelleService.update(mutableSchnittstelle.value).then(() => {
            mutableZuordnungen.value.forEach((zuordnung) => {
                if (!dialogProps.zuordnungen.includes(zuordnung)) {
                    zuordnung.schnittstelle = dialogProps.schnittstelle.id;
                    ZuordnungService.create(zuordnung);
                }
            });
            dialogProps.zuordnungen.forEach((toDelete) => {
                if (!mutableZuordnungen.value.includes(toDelete))
                    ZuordnungService.delete(toDelete.id);
            });
            emit("schnittstelle-saved");
            closeDialog();
            form.value?.resetValidation();
        });
    }
}

function saveSchnittstelle(): void {
    if (form.value?.validate() && mutableSchnittstelle.value.begruendung && mutableSchnittstelle.value.name) {
        if (dialogProps.isEdit) {
            updateSchnittstelle();
        } else {
            const schnittstelleRequest = new SchnittstelleRequest(
                mutableSchnittstelle.value.name,
                mutableSchnittstelle.value.status,
                mutableSchnittstelle.value.begruendung
            );
            createSchnittstelle(schnittstelleRequest);
        }
    }
}

function confirmZuordnung(zuordnung: Zuordnung): void {
    mutableZuordnungen.value.push(zuordnung);
}

function saveZuordnungen(schnittstelle: Schnittstelle) {
    for (const zuordnung of mutableZuordnungen.value) {
        if (schnittstelle.id !== undefined)
            zuordnung.schnittstelle = schnittstelle.id;
        ZuordnungService.create(zuordnung);
    }
}

function removeZuordnung(zuordnung: Zuordnung): void {
    mutableZuordnungen.value.splice(
        mutableZuordnungen.value.indexOf(zuordnung),
        1
    );
}

function resetSchnittstelle(): void {
    mutableSchnittstelle.value = dialogProps.isEdit
        ? dialogProps.schnittstelle
        : new Schnittstelle("", "", "", "DEAKTIVIERT");
    mutableZuordnungen.value = dialogProps.isEdit
        ? dialogProps.zuordnungen
        : [];
    form.value?.resetValidation();
}

function closeDialog() {
    emit("update:showDialog", false);
    emit("update-exited");
    resetSchnittstelle();
}

function resetBegruendung(): void {
    mutableSchnittstelle.value.begruendung = "";
}
</script>

<style>
@import "@/style/AddDialog.css";
</style>
