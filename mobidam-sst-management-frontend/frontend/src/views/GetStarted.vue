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
    <v-container>
        <v-row class="text-center">
            <v-col>
                <h1 class="text-h3 font-weight-bold mb-10">Dokumentation</h1>
                <h3>Weiterführende Links sind hier zu finden:</h3>
                <div>
                    <a
                        href="https://wiki.muenchen.de/anwendungsentwicklung/index.php/Barrakuda"
                        target="_blank"
                        rel="noopener noreferrer"
                        @click="dokumentationClicked = true"
                        >Barrakuda-Doku</a
                    >
                </div>
                <div>
                    <a
                        href="https://wiki.muenchen.de/anwendungsentwicklung/index.php/Archetype_API-Gateway"
                        target="_blank"
                        rel="noopener noreferrer"
                        @click="dokumentationClicked = true"
                        >Doku Archetype API-Gateway</a
                    >
                </div>
                <div>
                    <a
                        href="https://wiki.muenchen.de/anwendungsentwicklung/index.php/Archetype_Backend"
                        target="_blank"
                        rel="noopener noreferrer"
                        @click="dokumentationClicked = true"
                        >Doku Archetype Backend</a
                    >
                </div>
                <div>
                    <a
                        href="https://git.muenchen.de/ccse/refarch-archetype"
                        target="_blank"
                        rel="noopener noreferrer"
                        @click="dokumentationClicked = true"
                        >Git-Repo Archetypes</a
                    >
                </div>
                <div>
                    <a
                        href="https://wiki.muenchen.de/anwendungsentwicklung/index.php/JavaScript-Themen"
                        target="_blank"
                        rel="noopener noreferrer"
                        @click="dokumentationClicked = true"
                        >Javascript-Wiki</a
                    >
                </div>
            </v-col>
        </v-row>
        <yes-no-dialog
            v-model="saveLeaveDialog"
            dialogtitle="Wirklich verlassen?"
            dialogtext="Wollen Sie wirklich nicht die Dokumentation anschauen?"
            @no="cancel"
            @yes="leave"
        />
    </v-container>
</template>

<script setup lang="ts">
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import { useSaveLeave } from "@/composables/saveLeave";
import { ref } from "vue";

const dokumentationClicked = ref(false);
const { cancel, leave, saveLeaveDialog } = useSaveLeave(isDirty);

function isDirty(): boolean {
    return !dokumentationClicked.value;
}
</script>
