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
    <v-snackbar
        id="snackbar"
        v-model="show"
        :color="color"
        :timeout="timeout"
    >
        {{ message }}
        <v-btn
            v-if="color === 'error'"
            color="primary"
            variant="text"
            @click="show = false"
        >
            Schließen
        </v-btn>
    </v-snackbar>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
import { useSnackbarStore } from "@/stores/snackbar";

const snackbarStore = useSnackbarStore();

const defaultTimeout = 5000;

const show = ref(false);
const timeout = ref(defaultTimeout);
const message = ref("");
const color = ref("info");

watch(
    () => snackbarStore.message,
    () => (message.value = snackbarStore.message ?? "")
);

watch(
    () => snackbarStore.level,
    () => {
        color.value = snackbarStore.level;
        if (color.value === "error") {
            timeout.value = 0;
        } else {
            timeout.value = defaultTimeout;
        }
    }
);

watch(
    () => snackbarStore.show,
    () => {
        if (snackbarStore.show) {
            show.value = false;
            setTimeout(() => {
                show.value = true;
                snackbarStore.show = false;
            }, 100);
        }
    }
);
</script>
