import { Component, HostListener, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { CreditCard } from 'src/app/model/datatype/credit-card';
import { Request } from 'src/app/model/request';
import { RequestAcceptData } from 'src/app/service/class/request-accept-data';
import { AccessService } from 'src/app/service/helpers/access.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { LessorService } from 'src/app/service/lessor.service';
import { RequestService } from 'src/app/service/request.service';

@Component({
  selector: 'request-accept-dialog',
  templateUrl: './request-accept-dialog.component.html',
  styleUrls: ['./request-accept-dialog.component.css']
})
export class RequestAcceptDialogComponent {

  request: Request;
  myId: number = 0;
  reuse: boolean = false;
  storedCreditCard: CreditCard | null = null;
  newCreditCard: CreditCard = new CreditCard;

  constructor(
    public dialogRef: MatDialogRef<RequestAcceptDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {request: Request},
    private readonly translator: TranslateService,
    private readonly popUpService: PopUpService,
    private readonly accessService: AccessService,
    private readonly lessorService: LessorService,
    private readonly requestService: RequestService
  ) {
    this.request = data.request;
    const myId = this.accessService.getUserId();
    if(myId != null){
      this.myId = myId;
      this.lessorService.getCreditCard(myId).then(creditCard => {
        this.storedCreditCard = creditCard;
        if(this.storedCreditCard !== null) this.reuse = true;
      });
    }
  }

  onCancel(){
    this.dialogRef.close();
  }

  onAccept(){
    this.newCreditCard.number = this.newCreditCard.number.replace(/\s/g, "");

    const creditCard = this.reuse ? null : this.newCreditCard;
    this.requestService.accept(this.request.id, {accept: true, creditCard } as RequestAcceptData).then(_ => {
      if(creditCard != null) this.lessorService.updateCreditCard(this.myId, creditCard);
      this.dialogRef.close(true);
      this.popUpService.Success(this.translator.instant("DIALOG.ACCEPTREQUEST.SUCCESS"));
    });
  }

  @HostListener("input", ["$event"])
  onClick(e: any) {
    try {
      if (e.target.id === "input_ccnumber") {
        e.target.value = e.target.value.replace(/[^\dA-Z]/g, "").replace(/(.{4})/g, "$1 ").trim();
      }
    } catch (e) {
    }
  }


}
