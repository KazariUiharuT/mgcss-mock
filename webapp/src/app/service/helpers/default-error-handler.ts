import { ErrorHandler, Injectable } from "@angular/core";
import { PopUpService } from "./pop-up.service";

@Injectable()
export class DefaultErrorHandler implements ErrorHandler {

    constructor(
        private popUpService: PopUpService
    ) { }

    handleError(error: Error) {
        let msg = error;
        try {
            if (error.toString().includes("HttpErrorResponse:")) {
                msg = JSON.parse(error.toString().split("HttpErrorResponse:")[1]).error;
            } else if (error.toString().includes(",\"error\":{")) {
                let parts = error.toString().split(",\"error\":");
                msg = JSON.parse(parts[parts.length - 1].slice(0, -1));
            }
        } catch (e) { }
        this.popUpService.Handle(msg);
    }
}