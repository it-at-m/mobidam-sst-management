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
    <div>
        <div class="text-overline">
            {{ props.label }}
        </div>
        <v-input
            v-model:error="error"
            :readonly="props.readonly"
            :hide-details="props.hideDetails"
            :rules="validierungsRegeln"
            :density="props.dense ? 'compact' : undefined"
            :error-messages="errorMessages"
            :persistent-hint="props.persistentHint"
            :hint="props.hint"
        >
            <v-row>
                <v-col cols="6">
                    <v-text-field
                        ref="day"
                        v-model="day"
                        required
                        label="Datum"
                        :readonly="props.readonly"
                        :error="error"
                        hide-details
                        :density="props.dense ? 'compact' : undefined"
                        :variant="props.filled ? 'filled' : 'outlined'"
                        type="date"
                        @focusout="leaveInput"
                        @focus="enterInput"
                        @blur="sendInput"
                    />
                </v-col>
                <v-col cols="6">
                    <v-text-field
                        ref="time"
                        v-model="time"
                        required
                        label="Zeit"
                        :readonly="props.readonly"
                        :error="error"
                        hide-details
                        :density="props.dense ? 'compact' : undefined"
                        :variant="props.filled ? 'filled' : 'outlined'"
                        type="time"
                        @focusout="leaveInput"
                        @focus="enterInput"
                        @blur="sendInput"
                    >
                        <template v-if="props.clearable && !props.readonly">
                            <v-btn
                                icon
                                :disabled="!value"
                                @click="clear"
                            >
                                <v-icon v-if="value"> mdi-close</v-icon>
                            </v-btn>
                        </template>
                    </v-text-field>
                </v-col>
            </v-row>
        </v-input>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";

/**
 * Das Date-Time-Input` Feld bietet eine Eingabemöglichkeit von Date-Times ohne zusätzliche
 * Dialoge und möglichst mit den nativen Features des Browsers, welche auch per Tastatur bedient werden können.
 *
 * Beispiel:
 * <datetime-input
 *   id="kontakt-field"
 *   v-model="kontaktDate"
 *   dense
 *   clearable
 *   label="Kontakt am"
 *   :rules="[(v: string) => !v || moment(v).isBefore(moment.now()) || 'Datum muss in der Vergangenheit liegen.']"
 * ></datetime-input>
 */

interface Props {
    value: string;
    readonly: boolean;
    hideDetails: boolean;
    dense: boolean;
    filled: boolean;
    clearable: boolean;
    persistentHint: boolean;
    hint: string;
    label: string;
    rules: { (v: string): string | boolean }[];
}

const props = withDefaults(defineProps<Props>(), {
    readonly: false,
    hideDetails: false,
    dense: false,
    filled: false,
    clearable: true,
    persistentHint: false,
    hint: "",
    label: "",
    rules: () => [],
});

const day = ref<string | null>(null);
const time = ref<string | null>(null);
const error = ref(false);
const errorMessages = ref("");
const dateFilled = (): string | boolean =>
    checkBothFieldsFilled() || "Datum und Zeit muss ausgefüllt werden";

const emits = defineEmits<{
    (e: "input", v: string | null): void;
}>();

const validierungsRegeln = computed(() => {
    if (props.rules) {
        return [...props.rules, dateFilled];
    } else {
        return [dateFilled];
    }
});

onMounted(() => {
    parseValue();
});

function clear(): void {
    errorMessages.value = "";
    time.value = null;
    day.value = null;
    emits("input", getDate());
}

function getDate(): string | null {
    if (day.value && time.value) {
        error.value = false;
        errorMessages.value = "";
        return new Date(day.value + "T" + time.value).toISOString();
    }

    return null;
}

function parseValue(): void {
    if (props.value) {
        const newDate = new Date(props.value);
        day.value = parseDay(newDate);
        time.value = parseTime(newDate);
    } else {
        day.value = null;
        time.value = null;
    }
}

watch(
    () => props.value,
    () => parseValue()
);

function parseDay(timestamp: Date): string {
    return timestamp.toISOString().replace(/T.*/, "");
}

function parseTime(timestamp: Date): string {
    return timestamp.toLocaleTimeString(navigator.language, {
        hour: "2-digit",
        minute: "2-digit",
    });
}

function leaveInput(): void {
    if (!checkBothFieldsFilled()) {
        error.value = true;
        errorMessages.value = "Datum und Zeit muss ausgefüllt werden";
    }
}

function enterInput(): void {
    if (!checkBothFieldsFilled()) {
        error.value = false;
        errorMessages.value = "";
    }
}

function sendInput(): void {
    if (checkBothFieldsFilled()) {
        emits("input", getDate());
    }
}

function checkBothFieldsFilled(): boolean {
    return !!(time.value && day.value) || (!time.value && !day.value);
}
</script>
