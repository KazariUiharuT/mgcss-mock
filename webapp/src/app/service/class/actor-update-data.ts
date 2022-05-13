import { Phone } from "src/app/model/datatype/phone";

export class ActorUpdateData {
    name: string = "";
    surname: string = "";
    phone: Phone = new Phone;
    picture: string | null = null;
}
