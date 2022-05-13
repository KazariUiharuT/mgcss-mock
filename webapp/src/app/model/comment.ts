import { Actor } from "./actor";

export class Comment {
    id: number = 0;
    title: string = "";
    text: string = "";
    stars: number = 0;
    date: Date = new Date();
    author: Actor = new Actor();
}
