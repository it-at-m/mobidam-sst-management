///
/// The MIT License
/// Copyright © 2023 Landeshauptstadt München | it@M
///
/// Permission is hereby granted, free of charge, to any person obtaining a copy
/// of this software and associated documentation files (the "Software"), to deal
/// in the Software without restriction, including without limitation the rights
/// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
/// copies of the Software, and to permit persons to whom the Software is
/// furnished to do so, subject to the following conditions:
///
/// The above copyright notice and this permission notice shall be included in
/// all copies or substantial portions of the Software.
///
/// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
/// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
/// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
/// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
/// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
/// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
/// THE SOFTWARE.
///

import { ApiError, Levels } from "@/api/error";

export default class FetchUtils {
    /**
     * Liefert eine default GET-Config für fetch
     */
    static getGETConfig(): RequestInit {
        return {
            headers: this.getHeaders(),
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default POST-Config für fetch
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getPOSTConfig(body: any): RequestInit {
        return {
            method: "POST",
            body: body ? JSON.stringify(body) : undefined,
            headers: FetchUtils.getHeaders(),
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default PUT-Config für fetch
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getPUTConfig(body: any): RequestInit {
        const headers = FetchUtils.getHeaders();
        if (body.version) {
            headers.append("If-Match", body.version);
        }
        return {
            method: "PUT",
            body: body ? JSON.stringify(body) : undefined,
            headers,
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default DELETE-Config für fetch
     */
    static getDELETEConfig(): RequestInit {
        return {
            method: "DELETE",
            headers: this.getHeaders(),
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default PATCH-Config für fetch
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getPATCHConfig(body: any): RequestInit {
        const headers = FetchUtils.getHeaders();
        if (body.version !== undefined) {
            headers.append("If-Match", body.version);
        }
        return {
            method: "PATCH",
            body: body ? JSON.stringify(body) : undefined,
            headers,
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    static sendRequest(
        url: string,
        request: RequestInit,
        errorMessage: string,
        skipNotFound = false
    ): Promise<any> {
        return fetch(url, request)
            .catch(() => {
                // Catching the Error before any response was able to come
                throw new ApiError({
                    level: Levels.ERROR,
                    message: `Die Verbindung zum Service konnte nicht aufgebaut werden.`,
                });
            })
            .then((response) => {
                // handling not 200 responses
                if (!response.ok) {
                    if (response.status === 403) {
                        // HTTP 403 means unauthorized
                        throw new ApiError({
                            level: Levels.ERROR,
                            message: `Sie haben nicht die nötigen Rechte um diese Aktion durchzuführen.`,
                        });
                    } else if (response.type === "opaqueredirect") {
                        // the redirect option was set to manual and the server redirects the site
                        location.reload();
                    } else if (response.status === 404 && skipNotFound) {
                        // some backend APIs give a 404 NOT_FOUND, when everything is correct, but the resource doesn't exist
                        // in such cases the argument 'skipNotFound' can be set as true and this method creates an empty Promise
                        // (instead of throwing an Error)
                        return Promise.resolve();
                    }
                    throw new ApiError({
                        // for all other HTTP code the standard error message handling is used
                        level: Levels.ERROR,
                        message: errorMessage,
                    });
                }
                // returning a Promise with the request results because HTTP code is 200
                return response.json();
            });
    }

    /**
     * Default Catch-Handler für alle Anfragen des Service.
     * Schmeißt derzeit nur einen ApiError
     * @param error die Fehlermeldung aus fetch-Befehl
     */
    static defaultCatchHandler(
        error: Error,
        errorMessage = "Es ist ein unbekannter Fehler aufgetreten."
    ): PromiseLike<never> {
        throw new ApiError({
            level: Levels.WARNING,
            message: errorMessage,
        });
    }

    /**
     *  Baut den Header fuer den Request auf
     * @returns {Headers}
     */
    static getHeaders(): Headers {
        const headers = new Headers({
            "Content-Type": "application/json; charset=utf-8",
        });
        const csrfCookie = this._getXSRFToken();
        if (csrfCookie !== "") {
            headers.append("X-XSRF-TOKEN", csrfCookie);
        }
        return headers;
    }

    /**
     * Liefert den XSRF-TOKEN zurück.
     * @returns {string|string}
     */
    static _getXSRFToken(): string {
        const help = document.cookie.match(
            "(^|;)\\s*" + "XSRF-TOKEN" + "\\s*=\\s*([^;]+)"
        );
        return (help ? help.pop() : "") as string;
    }

    /**
     * Gibt an, wie im derzeit aktiven Modus mit den Credentials umzugehen ist.
     * Liefert im Development-Modus (npm run serve) den Wert "include" für Request.credentials,
     * damit die Credentials auch beim Zugriff von localhost:8081 auf localhost:8082 übertragen werden.
     * Im Production-Modus (npm run build) sollen die Credentials nur dann übertragen werden,
     * wenn der Zugriff auf den selben Host erfolgt (Request.credentials = "same-origin").
     */
    private static getCredentials(): RequestCredentials {
        return import.meta.env.MODE === "development"
            ? "include"
            : "same-origin";
    }
}
