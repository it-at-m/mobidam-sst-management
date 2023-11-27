import Zuordnung from "@/types/Zuordnung";
import FetchUtils from "@/api/FetchUtils";

export default class ZuordnungService {
    private static base: string | undefined = import.meta.env
        .VITE_VUE_APP_API_URL;
    public static create(instance: Zuordnung): Promise<Zuordnung> {
        //console.log(instance);
        return fetch(
            `${this.base}/api/zuordnung`,
            FetchUtils.getPOSTConfig(instance)
        ).then((response) => {
            FetchUtils.defaultResponseHandler(
                response,
                "Das Erstellen der Zuordnung ist fehlgeschlagen."
            );
            return response.json();
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
            .then()
            .catch((err) => {
                FetchUtils.defaultResponseHandler(
                    err,
                    "Die Zuordnung konnte nicht gelöscht werden."
                );
            });
    }
}
