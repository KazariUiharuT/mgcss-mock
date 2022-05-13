import { Actor } from "./actor";
import { CreditCard } from "./datatype/credit-card";

export class Tenant extends Actor{
    creditCard: CreditCard | null = null;
    smoker: boolean = false;
}
