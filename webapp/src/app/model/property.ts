import { Lessor } from "./lessor";
import { PropertyAttributeValue } from "./property-attribute-value";
import { PropertyPicture } from "./property-picture";

export class Property {
    id: number = 0;
    name: string = "";
    description: string = "";
    rate: number = 0;
    address: string = "";
    date: Date = new Date();
    nrequests: number = 0;
    naudits: number = 0;
    propietary: Lessor = new Lessor();
    pictures: PropertyPicture[] = [];
    attributes: PropertyAttributeValue[] = [];
}
