export default class Zuordnung {
    constructor(
        public schnittstelle: string,
        public userID: string,
        public department: string,
        public functionAddress: string,
        public validFrom: string,
        public validUntil: string,
        public id?: string,
    ) {}
}
