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

/**
 * Mit dem SaveLeaveComposable kann ein Datenverlust durch ungewolltest Navigieren verhindert werden.
 *
 * Nimmt eine Funktion `isDirty()` entgegen, in der festgelegt werden kann,
 * ob sicher navigiert werden kann oder eine Rückfrage an den Nutzer stattfinden soll.
 * Diese Rückfrage kann z.B. über einen Dialog gelöst werden. Hierzu bietet das SaveLeaveComposable
 * ein `saveLeaveDialog` Flag an. Für genererische Dialoge bietet das SaveLeaveComposable bereits Titel und
 * Text mit an.
 *
 * Mit dem Aufruf von `leave()` oder `cancel()` kann die Entscheidung des Nutzers ausgeführt werden.
 */
import { onBeforeRouteLeave } from "vue-router";
import { NavigationGuardNext, Route } from "vue-router";
import { ref } from "vue";

export function useSaveLeave(isDirty: () => boolean) {
    const saveLeaveDialogTitle = ref("Ungespeicherte Änderungen");
    const saveLeaveDialogText = ref(
        "Es sind ungespeicherte Änderungen vorhanden. Wollen Sie die Seite verlassen?"
    );
    const saveLeaveDialog = ref(false);
    const isSave = ref(false);

    const nextRoute = ref<NavigationGuardNext | null>(null);

    onBeforeRouteLeave((to: Route, from: Route, next: NavigationGuardNext) => {
        if (isDirty() && !isSave.value) {
            saveLeaveDialog.value = true;
            nextRoute.value = next;
        } else {
            saveLeaveDialog.value = false;
            next();
        }
    });

    function cancel(): void {
        saveLeaveDialog.value = false;
        if (nextRoute.value != null) {
            nextRoute.value(false);
        }
    }

    function leave(): void {
        if (nextRoute.value != null) {
            nextRoute.value();
        }
    }

    return {
        saveLeaveDialogTitle,
        saveLeaveDialogText,
        saveLeaveDialog,
        isSave,
        nextRoute,
        cancel,
        leave,
    };
}
