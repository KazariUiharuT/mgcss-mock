import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Invoice } from 'src/app/model/invoice';
import { Request } from 'src/app/model/request';

@Component({
  selector: 'invoice-dialog',
  templateUrl: './invoice-dialog.component.html',
  styleUrls: ['./invoice-dialog.component.css']
})
export class InvoiceDialogComponent {

  request: Request;
  invoice: Invoice;

  constructor(
    public dialogRef: MatDialogRef<InvoiceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {request: Request, invoice: Invoice}
  ) {
    this.request = data.request;
    this.invoice = data.invoice;
  }

  openPdf(){
    if(this.invoice.pdf) window.open(this.invoice.pdf, "_blank");
  }

}
