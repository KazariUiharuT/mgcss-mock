import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuditorService } from 'src/app/service/auditor.service';
import { AuditorRegisterData } from 'src/app/service/class/auditor-register-data';

@Component({
  selector: 'auditor-register-dialog',
  templateUrl: './auditor-register-dialog.component.html',
  styleUrls: ['./auditor-register-dialog.component.css']
})
export class AuditorRegisterDialogComponent {

  auditor: AuditorRegisterData = new AuditorRegisterData;

  constructor(
    public dialogRef: MatDialogRef<AuditorRegisterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {},
    private readonly auditorService: AuditorService
  ) { }

  onCancel(){
    this.dialogRef.close();
  }

  onAccept(){
    this.auditorService.register(this.auditor).then(_ => {
      this.dialogRef.close(true);
    });
  }

}
