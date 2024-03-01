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

export const enum Levels {
    INFO = "info",
    WARNING = "warning",
    ERROR = "error",
    SUCCESS = "success",
}

export class ApiError extends Error {
    level: string;
    constructor({
        level = Levels.ERROR,
        message = "Ein unbekannter Fehler ist aufgetreten, bitte den Administrator informieren.",
    }: {
        level?: string;
        message?: string;
    }) {
        // Übergibt die verbleibenden Parameter (einschließlich Vendor spezifischer Parameter) dem Error Konstruktor
        super(message);

        // Behält den richtigen Stack-Trace für die Stelle bei, an der unser Fehler ausgelöst wurde
        this.stack = new Error().stack;

        // Benutzerdefinierte Informationen
        this.level = level;
        this.message = message;
    }
}
