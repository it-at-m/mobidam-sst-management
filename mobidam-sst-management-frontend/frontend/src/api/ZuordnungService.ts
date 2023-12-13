import Zuordnung from "@/types/Zuordnung";
import FetchUtils from "@/api/FetchUtils";
import { Levels } from "@/api/error";
import { useSnackbarStore } from "@/stores/snackbar";

export default class ZuordnungService {
    private static base: string | undefined = import.meta.env
        .VITE_VUE_APP_API_URL;
    public static create(instance: Zuordnung): Promise<Zuordnung> {
        return fetch(
            `${this.base}/api/zuordnung`,
            FetchUtils.getPOSTConfig(instance)
        )
            .then((response) => {
                useSnackbarStore().showMessage({
                    message: "Speichern erfolgreich.",
                    level: Levels.SUCCESS,
                });
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: "Speichern der Person fehlgeschlagen.",
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    }

    public static getZuordnungenByID(
        instanceId: string | undefined
    ): Promise<Zuordnung[]> {
        return fetch(
            `${this.base}/api/zuordnung/bySchnittstelle/${instanceId}`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            if (response.status != 502) {
                FetchUtils.defaultResponseHandler(
                    response,
                    "Die Zuordnung konnte nicht geladen werden."
                );
            }
            return response.json();
        });
    }

    public static delete(
        instanceId: string | undefined
    ): Promise<Response | void> {
        return fetch(
            `${this.base}/api/zuordnung/${instanceId}`,
            FetchUtils.getDELETEConfig()
        )
            .then(() => {
                useSnackbarStore().showMessage({
                    message: "Löschen erfolgreich.",
                    level: Levels.SUCCESS,
                });
            })
            .catch((err) => {
                FetchUtils.defaultResponseHandler(
                    err,
                    "Die Zuordnung konnte nicht gelöscht werden."
                );
            });
    }
}
