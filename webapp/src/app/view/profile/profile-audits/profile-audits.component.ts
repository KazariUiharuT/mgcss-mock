import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Actor } from 'src/app/model/actor';
import { Audit } from 'src/app/model/audit';
import { AuditService } from 'src/app/service/audit.service';
import { AuditorService } from 'src/app/service/auditor.service';
import { AuditDialogComponent } from '../../dialog/audit-dialog/audit-dialog.component';
import { AuditEditDialogComponent } from '../../dialog/audit-edit-dialog/audit-edit-dialog.component';

@Component({
  selector: 'profile-audits',
  templateUrl: './profile-audits.component.html',
  styleUrls: ['./profile-audits.component.css']
})
export class ProfileAuditsComponent {

  @Input() set actor(val: Actor) {
    this.auditorService.listAudits(val.id).then(audits => this.audits = audits);
  }
  @Input() mySelf: boolean = false;
  audits: Audit[] = [];

  constructor(
    private readonly dialog: MatDialog,
    private readonly auditorService: AuditorService,
    private readonly auditService: AuditService
  ) { }

  onEdit(audit: Audit, event: Event){
    event.stopImmediatePropagation();
    this.dialog.open(AuditEditDialogComponent, {data: {audit: audit}})
    .afterClosed().subscribe(result => { if(result) this.audits[this.audits.findIndex(a => a.id === result.id)] = result; });
  }

  onDelete(audit: Audit, event: Event){
    event.stopImmediatePropagation();
    this.auditService.delete(audit.id).then(() => this.audits.splice(this.audits.findIndex(a => a.id === audit.id), 1));
  }

  onOpen(audit: Audit){
    this.dialog.open(AuditDialogComponent, {data: {audit: audit}, maxWidth: "400px"})
    .afterClosed().subscribe(result => { if(result) audit.draft = result.draft; });
  }

}
