import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { Invoice } from 'src/app/model/invoice';
import { Request } from 'src/app/model/request';
import { RequestAcceptData } from 'src/app/service/class/request-accept-data';
import { AccessService } from 'src/app/service/helpers/access.service';
import { CommonsService } from 'src/app/service/helpers/commons.service';
import { REQUEST_STATUS } from 'src/app/service/helpers/constants.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { LessorService } from 'src/app/service/lessor.service';
import { RequestService } from 'src/app/service/request.service';
import { TenantService } from 'src/app/service/tenant.service';
import { InvoiceDialogComponent } from '../../dialog/invoice-dialog/invoice-dialog.component';
import { RequestAcceptDialogComponent } from '../../dialog/request-accept-dialog/request-accept-dialog.component';

@Component({
  selector: 'profile-requests',
  templateUrl: './profile-requests.component.html',
  styleUrls: ['./profile-requests.component.css']
})
export class ProfileRequestsComponent {

  myId: number = 0;
  requests: Request[] = [];

  REQUEST_STATUS = REQUEST_STATUS;

  constructor(
    private readonly translator: TranslateService,
    private readonly dialog: MatDialog,
    private readonly popUpService: PopUpService,
    private readonly commonsService: CommonsService,
    private readonly accessService: AccessService,
    private readonly lessorService: LessorService,
    private readonly tenantService: TenantService,
    private readonly requestService: RequestService
  ) {
    const myId = this.accessService.getUserId();
    if(myId != null){
      this.myId = myId;
      if(this.accessService.getUserType() === "lessor") {
        this.lessorService.listRequests(myId).then(requests => this.requests = requests);
      }else if(this.accessService.getUserType() === "tenant"){
        this.tenantService.listRequests(myId).then(requests => this.requests = requests);
      }
    }
  }

  onAccept(request: Request){
    this.dialog.open(RequestAcceptDialogComponent, {data: {request: request}})
    .afterClosed().subscribe(accepted => { if(accepted) request.status = 'ACCEPTED'; });
  }

  onReject(request: Request){
    this.popUpService.Confirm(this.translator.instant("DIALOG.CANCELREQUEST.TITLE"), this.translator.instant("DIALOG.CANCELREQUEST.TEXT"), this.translator.instant("DIALOG.CANCELREQUEST.YES"), this.translator.instant("DIALOG.CANCELREQUEST.NO"))
    .then(_ => {
      this.requestService.accept(request.id, {accept: false} as RequestAcceptData).then(_ => request.status = 'DENIED');
    }).catch(_ => {});
  }

  onGenerateInvoice(request: Request){
    this.popUpService.ShowLoading();
    this.requestService.createInvoice(request.id).then(_ => {
      this.popUpService.DisableLoading();
      request.invoice = new Invoice;
      this.onOpenInvoice(request);
    });
  }

  onOpenInvoice(request: Request){
    this.requestService.getInvoice(request.id).then(invoice => {
      this.dialog.open(InvoiceDialogComponent, {data: {invoice, request}});
    });
  }

  goToProfile(id: number){
    this.commonsService.navigate(`/profile/${id}`);
  }

}
