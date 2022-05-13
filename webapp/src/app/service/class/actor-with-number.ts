import { Actor } from "src/app/model/actor";

export class ActorWithNumber {
    actor: Actor = new Actor;
    value: number = -1;

    constructor(data: any[]) {
        this.actor = data[0] as Actor;
        this.value = data[1] as number;
    }
}
