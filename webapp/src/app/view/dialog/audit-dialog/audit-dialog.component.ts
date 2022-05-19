import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Audit } from 'src/app/model/audit';
import { AuditAttachment } from 'src/app/model/audit-attachment';
import { AccessService } from 'src/app/service/helpers/access.service';
import { CommonsService } from 'src/app/service/helpers/commons.service';
import { AuditEditDialogComponent } from '../audit-edit-dialog/audit-edit-dialog.component';

@Component({
  selector: 'audit-dialog',
  templateUrl: './audit-dialog.component.html',
  styleUrls: ['./audit-dialog.component.css']
})
export class AuditDialogComponent {

  audit: Audit = new Audit;
  mine: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<AuditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {audit: Audit},
    private readonly dialog: MatDialog,
    private readonly accessService: AccessService,
    private readonly commonsService: CommonsService
  ) {
    this.audit = data.audit;
    this.mine = data.audit.author.id === this.accessService.getUserId();
  }

  onDownloadAttachment(attachment: AuditAttachment){
    window.open(attachment.value);
  }

  onClose(){
    this.dialogRef.close();
  }

  onEdit(){
    if(!this.audit.draft) return;
    this.dialog.open(AuditEditDialogComponent, {data: {audit: this.audit}})
    .afterClosed().subscribe(result => this.dialogRef.close(result));
  }

  goToProfile(){
    this.commonsService.navigate(`/profile/${this.audit.author.id}`);
    this.dialogRef.close();
  }

}
