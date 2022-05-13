import { AuditAttachment } from "./audit-attachment";
import { Auditor } from "./auditor";
import { Property } from "./property";

export class Audit {
    id: number = 0;
    text: string = "";
    draft: boolean = false;
    date: Date = new Date;
    property: Property = new Property;
    author: Auditor = new Auditor;
    attachments: AuditAttachment[] = [];
}
