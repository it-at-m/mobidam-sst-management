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