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
<script setup lang="ts">
import Datentransfer from "@/types/Datentransfer";
import DatentransferService from "@/api/DatentransferService";
import { ref, onMounted, watch } from "vue";
import type { VDataTableServer } from "vuetify/components";

type ReadonlyHeaders = VDataTableServer["$props"]["headers"];

type Props = {
    schnittstelle: string;
};
const props = withDefaults(defineProps<Props>(), {
    schnittstelle: "-",
});

const itemsPerPage = ref(10);
const items = ref<Datentransfer[]>([]);
const loading = ref(false);
const numberOfDatentransfer = ref(0);

const headers = ref<ReadonlyHeaders>([
    {
        title: "Zeitstempel",
        align: "start",
        value: "zeitstempel",
        sortable: false,
    },
    {
        title: "Prozess ID",
        align: "start",
        value: "prozessId",
        sortable: false,
    },
    {
        title: "Ereignis",
        align: "start",
        value: "ereignis",
        sortable: false,
    },
    {
        title: "Info",
        align: "start",
        value: "info",
        sortable: false,
    },
]);


onMounted(() => {
    DatentransferService.getDatentransferNumberBySchnittstelle(
        props.schnittstelle
    ).then((datentransferNumber) => {
        numberOfDatentransfer.value = datentransferNumber;
    });
});

</script>

<template>
    <v-data-table-server
        :headers="headers"
        :items="items"
        :loading="loading"
        :items-per-page="itemsPerPage"
        :items-length="numberOfDatentransfer"
        :sort-by="[{ key: 'zeitstempel' }]"
        :footer-props="{
            itemsPerPageOptions: [10],
        }"
    ></v-data-table-server>
</template>

<style scoped></style>
