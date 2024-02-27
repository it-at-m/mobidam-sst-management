///
/// The MIT License
///  Copyright © 2023 Landeshauptstadt München | it@M
///
///  Permission is hereby granted, free of charge, to any person obtaining a copy
///  of this software and associated documentation files (the "Software"), to deal
///  in the Software without restriction, including without limitation the rights
///  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
///  copies of the Software, and to permit persons to whom the Software is
///  furnished to do so, subject to the following conditions:
///
///  The above copyright notice and this permission notice shall be included in
///  all copies or substantial portions of the Software.
///
///  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
///  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
///  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
///  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
///  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
///  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
///  THE SOFTWARE.
///

import FetchUtils from "@/api/FetchUtils";
import Schnittstelle from "@/types/Schnittstelle";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";

export default class SchnittstelleService {
    private static base: string | undefined = import.meta.env
        .VITE_VUE_APP_API_URL;

    public static getAllSchnittstelle(): Promise<Schnittstelle[]> {
        return fetch(
            `${this.base}/api/schnittstelle`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            if (response.status != 200) {
                FetchUtils.defaultResponseHandler(
                    response,
                    "Die Schnittstellen konnte nicht geladen werden."
                );
            }
            return response.json();
        });
    }

    public static create(instance: Schnittstelle): Promise<Schnittstelle> {
        return fetch(
            `${this.base}/api/schnittstelle`,
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
                    message: "Speichern der Schnittstelle fehlgeschlagen.",
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    }
}
