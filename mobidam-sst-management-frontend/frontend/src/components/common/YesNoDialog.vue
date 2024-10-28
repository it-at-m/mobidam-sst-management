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
        v-model="visible"
        persistent
        width="800"
    >
        <template #activator="{ props }">
            <template v-if="props.buttontext">
                <v-btn color="primary">
                    {{ buttontext }}
                </v-btn>
            </template>
            <template v-else-if="props.icontext">
                <v-btn
                    variant="text"
                    color="primary"
                    v-bind="props"
                >
                    <v-icon size="large">
                        {{ props.icontext }}
                    </v-icon>
                </v-btn>
            </template>
        </template>
        <v-card>
            <v-card-title>
                {{ dialogProps.dialogtitle }}
            </v-card-title>
            <v-card-text>
                {{ dialogProps.dialogtext }}
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    id="yesnodialog-btn-no"
                    variant="text"
                    @click="no"
                >
                    Nein
                </v-btn>
                <v-btn
                    id="yesnodialog-btn-yes"
                    color="success"
                    @click="yes"
                >
                    Ja
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { computed } from "vue";
/**
 * Der YesNo-Dialog ist ein generischer Dialog zur binären Abfrage beim Nutzer.
 * So kann z.B. die Bestätigung für das Löschen einer Entität damit realisiert werden.
 *
 * Da das Bestätigen einer Aktion in der Regel mit einem Button zusammenhängt, bietet der
 * YesNoDialog diesen gleich mit an. Über `buttontext` und `icontext` kann dieser konfiguriert werden.
 *
 * Wenn sowohl kein `buttontext` als auch `icontext` nicht gesetzt sind, kann der YesNoDialog auch
 * als reiner Dialog verwendet werden. Hierzu wird das Value vom Dialog durchgereicht.
 *
 * Die Bestätigung des Dialogs wird über ein `yes` Event signalisiert. Analog erfolgt die
 * Signalisierung der Abweisung durch ein `no` Event.
 *
 * Beispiel:
 * <yes-no-dialog
 *    v-model="deleteDialog"
 *    buttontext="Löschen"
 *    dialogtitle="Löschen?"
 *    dialogtext="Wollen Sie die Entität wirklich löschen?"
 *    @no="deleteDialog = false"
 *    @yes="deleteSome"></yes-no-dialog>
 */

const dialogProps = defineProps<{
    buttontext?: string;
    icontext?: string;
    dialogtitle: string;
    dialogtext: string;
    /**
     * Steuerflag für den Dialog
     */
    value: boolean;
}>();

const emits = defineEmits<{
    (e: "no"): void;
    (e: "yes"): void;
    (e: "input", v: boolean): void;
}>();

const visible = computed({
    get: () => dialogProps.value,
    set: (v) => emits("input", v),
});

function no(): void {
    emits("no");
}
function yes(): void {
    emits("yes");
}
</script>
