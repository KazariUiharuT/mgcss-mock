import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Audit } from 'src/app/model/audit';
import { AuditAttachment } from 'src/app/model/audit-attachment';
import { AuditService } from 'src/app/service/audit.service';
import { AuditUpdateData } from 'src/app/service/class/audit-update-data';
import { CommonsService } from 'src/app/service/helpers/commons.service';
import { FileSelectService } from 'src/app/service/helpers/file-select.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';

@Component({
  selector: 'audit-edit-dialog',
  templateUrl: './audit-edit-dialog.component.html',
  styleUrls: ['./audit-edit-dialog.component.css']
})
export class AuditEditDialogComponent {

  audit: Audit;
  update: AuditUpdateData = new AuditUpdateData;

  constructor(
    public dialogRef: MatDialogRef<AuditEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {audit: Audit},
    private readonly popUpService: PopUpService,
    private readonly commonsService: CommonsService,
    private readonly auditService: AuditService
  ) {
    this.audit = data.audit;
    this.update.text = data.audit.text;
  }

  onAddAttachment(){
    FileSelectService.Select("*")
    .then(file => this.update.newAttachments.push(file))
    .catch(_ => {});
  }

  onRemoveAttachment(attachment: AuditAttachment){
    this.update.deletedAttachments.push(attachment.id);
    this.audit.attachments.splice(this.audit.attachments.indexOf(attachment), 1);
  }

  onRemoveNewAttachment(index: number){
    this.update.newAttachments.splice(index, 1);
  }

  onOpenAtatchment(value: string){
    if(value.includes("http"))
    window.open(value, "_blank");
    else 
    this.commonsService.openFileNewWindow("", value);
  }

  onCancel(){
    this.dialogRef.close();
  }

  onAccept(){
    this.popUpService.ShowLoading();
    this.auditService.update(this.audit.id, this.update).then(_ => {
      this.auditService.get(this.audit.id).then(newAudit => {
        this.dialogRef.close(newAudit);
        this.popUpService.DisableLoading();
      });
    });
  }

  onPublish(){
    this.popUpService.ShowLoading();
    this.auditService.update(this.audit.id, this.update).then(_ => {
      this.auditService.publish(this.audit.id).then(_ => {
        this.auditService.get(this.audit.id).then(newAudit => {
          this.dialogRef.close(newAudit);
          this.popUpService.DisableLoading();
        });
      });
    });
    
  }

}
