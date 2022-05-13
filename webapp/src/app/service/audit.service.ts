import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom } from 'rxjs';
import { Audit } from '../model/audit';
import { AuditUpdateData } from './class/audit-update-data';

@Injectable({
  providedIn: 'root'
})
export class AuditService {

  endpoint = `${env.api}audit`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  get(auditId: number) {
    return lastValueFrom(
      this.http.get<Audit>(
        `${this.endpoint}/${auditId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  update(auditId: number, updateData: AuditUpdateData) {
    return lastValueFrom(
      this.http.put(
        `${this.endpoint}/${auditId}`, updateData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  delete(auditId: number) {
    return lastValueFrom(
      this.http.delete(
        `${this.endpoint}/${auditId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  publish(auditId: number) {
    return lastValueFrom(
      this.http.patch(
        `${this.endpoint}/${auditId}/draft`, {},
        { headers: this.commonsService.getHeaders() }
      ));
  }

}
