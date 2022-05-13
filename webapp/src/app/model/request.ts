import { CreditCard } from "./datatype/credit-card";
import { Invoice } from "./invoice";
import { Property } from "./property";
import { Tenant } from "./tenant";

export class Request {
    id: number = 0;
    smoker: boolean = false;
    checkIn: Date = new Date();
    checkOut: Date = new Date();
    status: RequestStatus = 'PENDING';
    lessorCreditCard: CreditCard | null = null;
    tenantCreditCard: CreditCard | null = null;
    lessorFee: number = 0;
    tenantFee: number = 0;
    ndays: number = 0;
    rate: number = 0;
    lessorTotal: number = 0;
    tenantTotal: number = 0;
    property: Property = new Property;
    tenant: Tenant = new Tenant;
    invoice: Invoice | null = null;
}

export type RequestStatus = 'PENDING' | 'ACCEPTED' | 'DENIED';