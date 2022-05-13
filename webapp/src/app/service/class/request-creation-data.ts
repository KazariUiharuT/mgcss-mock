import { CreditCard } from "src/app/model/datatype/credit-card";

export class RequestCreationData {
    smoker: boolean = false;
    checkIn: Date | null = null;
    checkOut: Date | null = null;
    creditCard: CreditCard | null = null;
}
