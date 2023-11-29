export function useRules() {
    function maxLengthRule(length: number, message = "error") {
        return (value: string | null | undefined) =>
            (value && value.length <= length) || message;
    }

    function notEmptyDateRule(message = "error") {
        return (value: string | null | undefined) =>
            (value && value.trim() != "-") || message;
    }

    function notEmptyRule(message = "error") {
        return (value: string | null | undefined) =>
            (value && value.trim() != "") || message;
    }

    return {
        maxLengthRule,
        notEmptyDateRule,
        notEmptyRule,
    };
}
