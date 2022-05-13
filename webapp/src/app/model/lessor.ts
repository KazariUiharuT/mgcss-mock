import { Actor } from "./actor";
import { CreditCard } from "./datatype/credit-card";

export class Lessor extends Actor {
    creditCard: CreditCard | null = null;
}
