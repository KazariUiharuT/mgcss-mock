import { CreditCard } from "src/app/model/datatype/credit-card";

export class RequestAcceptData {
    accept: boolean = false;
    creditCard: CreditCard | null = null;
}
