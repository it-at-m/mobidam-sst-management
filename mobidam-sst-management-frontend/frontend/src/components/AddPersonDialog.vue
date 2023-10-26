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
                        label="Person angeben"
                        hint="Welche Person soll der
                    Schnittstelle zugewiesen werden?"
                    >
                    </v-text-field>
                    <v-text-field
                        ref="department"
                        label="Fachbereich"
                        hint="Welchem Fachbereich ist die betreffende Person zugeordnet?"
                        counter="255"
                        maxlength="255"
                    ></v-text-field>
                    <v-text-field
                        ref="adress"
                        label="Funktionsadresse"
                        hint="Welchem Gruppenpostfach gehört diese Person an?"
                        counter="255"
                        maxlength="255"
                    ></v-text-field>
                    <v-row>
                        <v-col>
                            <v-menu
                                max-width="100%"
                                :close-on-content-click="false"
                            >
                                <template #activator="{ on }">
                                    <v-text-field
                                        label="Gültig ab"
                                        readonly
                                        v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    color="primary"
                                    header-color="primary"
                                    :first-day-of-week="1"
                                    :min="new Date().toISOString()"
                                ></v-date-picker>
                            </v-menu>
                        </v-col>
                        <v-col>
                            <v-menu
                                max-width="100%"
                                :close-on-content-click="false"
                            >
                                <template #activator="{ on }">
                                    <v-text-field
                                        label="Gültig bis"
                                        readonly
                                        v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                    color="primary"
                                    header-color="primary"
                                    :first-day-of-week="1"
                                    :min="new Date().toISOString()"
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
                    color="green"
                >
                    Speichern
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
interface Props {
    showDialog: boolean;
}

const props = withDefaults(defineProps<Props>(), {
    showDialog: false,
});

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
    (e: "taskDeleted"): void;
}>();

function closeDialog() {
    emit("update:showDialog", false);
}
</script>

<style scoped>
.divider {
    background-color: #395b64;
}
.title-content {
    margin-right: 8%;
}
</style>
