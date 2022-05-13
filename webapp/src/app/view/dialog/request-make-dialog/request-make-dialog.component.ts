import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { CreditCard } from 'src/app/model/datatype/credit-card';
import { Property } from 'src/app/model/property';
import { RequestCreationData } from 'src/app/service/class/request-creation-data';
import { AccessService } from 'src/app/service/helpers/access.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { PropertyService } from 'src/app/service/property.service';
import { TenantService } from 'src/app/service/tenant.service';
import { RequestAcceptDialogComponent } from '../request-accept-dialog/request-accept-dialog.component';

@Component({
  selector: 'request-make-dialog',
  templateUrl: './request-make-dialog.component.html',
  styleUrls: ['./request-make-dialog.component.css']
})
export class RequestMakeDialogComponent {

  myId: number = 0;
  reuse: boolean = false;
  storedCreditCard: CreditCard | null = null;
  newCreditCard: CreditCard = new CreditCard;
  request: RequestCreationData = new RequestCreationData;
  property: Property;

  constructor(
    public dialogRef: MatDialogRef<RequestAcceptDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {property: Property},
    private readonly translator: TranslateService,
    private readonly popUpService: PopUpService,
    private readonly accessService: AccessService,
    private readonly tenantService: TenantService,
    private readonly propertyService: PropertyService
  ) {
    this.property = data.property;
    const myId = this.accessService.getUserId();
    if(myId != null){
      this.myId = myId;
      this.tenantService.getCreditCard(myId).then(creditCard => {
        this.storedCreditCard = creditCard;
        if(this.storedCreditCard !== null) this.reuse = true;
      });
      this.tenantService.getSmoker(myId).then(smoker => this.request.smoker = smoker??false);
    }
  }

  onCancel(){
    this.dialogRef.close();
  }

  onAccept(){
    if(this.request.checkIn === null || this.request.checkOut === null){
      this.popUpService.Error(this.translator.instant("DIALOG.MAKEREQUEST.EMPTYCHECK"));
      return;
    }

    this.newCreditCard.number = this.newCreditCard.number.replace(/\s/g, "");

    this.request.creditCard = this.reuse ? null : this.newCreditCard;
    this.propertyService.createRequests(this.property.id, this.request).then(_ => {
      this.tenantService.updateSmoker(this.myId, this.request.smoker).then(_ => {
        if(this.request.creditCard != null) this.tenantService.updateCreditCard(this.myId, this.request.creditCard);
      });
      this.dialogRef.close(true);
      this.popUpService.Success(this.translator.instant("DIALOG.MAKEREQUEST.SUCCESS"));
    });
  }

}
