import { Injectable } from '@angular/core';
import { ApiError } from "../helpers/access.service";
import Swal from "sweetalert2";
import { TranslateService } from '@ngx-translate/core';


@Injectable({
  providedIn: 'root'
})
export class PopUpService {

  loadingSwal: boolean = false;

  constructor(
    private readonly translator: TranslateService
  ) { }

  /**
   * Lanza un mensaje de éxito al usuario.
   */
  Success(title: string) {
    return Swal.fire({ icon: "success", position: 'top-end', title: title, showConfirmButton: false, timer: 3000 });
  }

  /**
   * Lanza un mensaje de información al usuario.
   */
  Info(htmlInfo: string, title: string, confirmBtnText = "Ok") {
    return Swal.fire({ icon: "info", position: 'top-end', title: title, html: htmlInfo, confirmButtonText: confirmBtnText, confirmButtonColor: "#3085d6" });
  }

  /**
   * Lanza un mensaje de error al usuario.
   */
  Error(htmlError: string, title = "ERROR", confirmBtnText = "Ok") {
    PopUpService.Error(htmlError, title, confirmBtnText);
  }

  static Error(htmlError: string, title = "ERROR", confirmBtnText = "Ok") {
    return Swal.fire({ icon: "error", title: title, html: htmlError, confirmButtonText: confirmBtnText, confirmButtonColor: "#3085d6" });
  }

  /**
   * Pide confirmacion para una operacion importante.
   */
  Confirm(title: string, text: string, button: string, cancel: string) {
    return new Promise((resolve, reject) => {
      Swal.fire({
        title,
        text,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#585858',
        confirmButtonText: button,
        cancelButtonText: cancel
      }).then((result) => {
        if (result.isConfirmed) {
          resolve(true);
        }else{
          reject();
        }
      });
    });
  }

  /**
   * Lanza un mensaje de carga si no existe.
   */
  ShowLoading(title = "PROCESANDO", htmlText = this.translator.instant("DIALOG.SWAL.PROCESING")) {
    if (!this.loadingSwal) {
      this.loadingSwal = true;
      Swal.fire({ title: title, html: htmlText, showCloseButton: false, allowEscapeKey: false, allowOutsideClick: false, didOpen: () => { Swal.showLoading(); } });
    }
    return this.loadingSwal;
  }

  /**
   * Detiene el mensaje de carga existente.
   */
  DisableLoading() {
    if (this.loadingSwal) {
      Swal.close()
      this.loadingSwal = false;
    }
    return this.loadingSwal;
  }


  /**
   * Maneja un error del back.
   */
  Handle(ex : any) {
    if(ex?.code && ex?.message && ex?.timestamp){
      const translatedError = this.translator.instant("EXCEPTIONS."+ex.code);
      if(translatedError != "EXCEPTIONS."+ex.code)
        this.Error(translatedError);
      else
        this.Error(ex.message);
    }else if(ex?.error?.code && ex?.error?.message && ex?.error?.timestamp){
      this.Handle(ex.error);
    }else{
      this.Error(ex?.message??this.translator.instant("DIALOG.SWAL.UNESPECTEDERROR"));
    }
    console.log(ex);
  }

}
