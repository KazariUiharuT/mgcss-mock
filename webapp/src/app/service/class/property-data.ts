import { PropertyAttributeValueData } from "./property-attribute-value-data";

export class PropertyData {
    name: string = "";
    description: string = "";
    rate: number | null = null;
    address: string = "";
    pictures: string[] = [];
    attributes: PropertyAttributeValueData[] = [];
}
