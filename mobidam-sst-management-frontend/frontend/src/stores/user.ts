///
/// Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
/// der Landeshauptstadt München, 2024
///

import { defineStore } from "pinia";

export interface UserState {
    username: string | undefined;
    authorities?: string[];
}

export const useUserStore = defineStore("user", {
    state: (): UserState => ({
        username: "",
    }),
    getters: {},
    actions: {
        setUsername(payload: string): void {
            this.username = payload;
        },
        setLevel(payload: string[]): void {
            this.authorities = payload;
        },
    },
});
