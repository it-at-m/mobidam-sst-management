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

export function useRules() {
    function maxLengthRule(length: number, message = "error") {
        return (value: string | null | undefined) =>
            (value && value.length <= length) || message;
    }

    function notEmptyRule(message = "error") {
        return (value: string | null | undefined) =>
            (value && value.trim() != "") || message;
    }

    function isValidEmail(message = "error") {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return (value: string | null | undefined) =>
            (value && emailPattern.test(value.trim())) || message;
    }

    function isExpired(toDate: string | undefined) {
        if (!toDate) {
            return false;
        }
        const today = new Date();
        const to = new Date(toDate);
        return to < today;
    }

    function isGueltigAbBeforeGueltigBis(
        gueltigAb: string,
        gueltigBis: string | number | Date | undefined,
        message: string
    ) {
        if (!gueltigBis && !gueltigAb) {
            return true;
        }
        if (!gueltigBis && gueltigAb) {
            return true;
        } else {
            const to = new Date(gueltigBis ?? "");
            const from = new Date(gueltigAb);
            return from <= to || message;
        }
    }

    return {
        maxLengthRule,
        notEmptyRule,
        isValidEmail,
        isExpired,
        isGueltigAbBeforeGueltigBis,
    };
}
