import { CreditCard } from "./datatype/credit-card";

export class Invoice {
    id: number = 0;
    date: Date = new Date();
    pdf: string | null = null;
    vat: string = "";
    creditCard: CreditCard = new CreditCard;
    ammount: number = 0;
}
