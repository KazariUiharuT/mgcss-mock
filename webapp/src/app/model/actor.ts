import { UserType } from "../service/helpers/access.service";
import { Phone } from "./datatype/phone";

export class Actor {
    id: number = 0;
    name: string = "";
    surname: string = "";
    email: string = "";
    phone: Phone = new Phone;
    picture: string | null = null;
    stars: number = 0;
    type: UserType | null = null;
}
