import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Auditor } from 'src/app/model/auditor';
import { AuditorService } from 'src/app/service/auditor.service';
import { AuditorRegisterDialogComponent } from '../../dialog/auditor-register-dialog/auditor-register-dialog.component';

@Component({
  selector: 'admin-auditor-list',
  templateUrl: './admin-auditor-list.component.html',
  styleUrls: ['./admin-auditor-list.component.css']
})
export class AdminAuditorListComponent {

  auditors: Auditor[] = [];

  constructor(
    private readonly dialog: MatDialog,
    private readonly auditorService: AuditorService
  ) {
    this.updateList();
  }

  updateList(){
    this.auditorService.list().then(auditors => this.auditors = auditors);
  }

  onCreate(){
    this.dialog.open(AuditorRegisterDialogComponent, {data: {}, width: '400px'})
    .afterClosed().subscribe(result => { if(result) this.updateList(); });
  }

}
