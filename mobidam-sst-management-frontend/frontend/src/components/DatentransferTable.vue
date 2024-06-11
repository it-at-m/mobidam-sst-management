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
import { DataOptions, DataTableHeader } from "vuetify";

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
const options = ref<DataOptions>({
    groupBy: [],
    groupDesc: [],
    itemsPerPage: 10,
    multiSort: false,
    mustSort: false,
    page: 1,
    sortBy: ["zeitstempel"],
    sortDesc: [],
});

const headers = ref<DataTableHeader[]>([
    {
        text: "Zeitstempel",
        align: "start",
        value: "zeitstempel",
        sortable: false,
    },
    {
        text: "Prozess ID",
        align: "start",
        value: "prozessId",
        sortable: false,
    },
    {
        text: "Ereignis",
        align: "start",
        value: "ereignis",
        sortable: false,
    },
    {
        text: "Info",
        align: "start",
        value: "info",
        sortable: false,
    },
]);

watch(options, loadItems);

onMounted(() => {
    DatentransferService.getDatentransferNumberBySchnittstelle(
        props.schnittstelle
    ).then((datentransferNumber) => {
        numberOfDatentransfer.value = datentransferNumber;
    });
});

function loadItems(updatedOptions: DataOptions) {
    loading.value = true;
    DatentransferService.getDatentransfersBySchnittstelle(
        props.schnittstelle,
        updatedOptions.page
    )
        .then((datentransfer) => {
            if (datentransfer) items.value = datentransfer;
        })
        .finally(() => (loading.value = false));
}
</script>

<template>
    <v-data-table
        :headers="headers"
        :items="items"
        :loading="loading"
        :items-per-page="itemsPerPage"
        :server-items-length="numberOfDatentransfer"
        :options.sync="options"
        :footer-props="{
            itemsPerPageOptions: [10],
        }"
    ></v-data-table>
</template>

<style scoped>

</style>