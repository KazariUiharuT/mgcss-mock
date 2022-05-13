export class CreditCard {
    number: string = "";
    expiracyMonth: number = 1;
    expiracyYear: number = new Date().getFullYear();
    cvv: string = "";
}
