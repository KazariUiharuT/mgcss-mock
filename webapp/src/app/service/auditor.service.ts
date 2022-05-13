import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { Auditor } from '../model/auditor';
import { AuditorRegisterData } from './class/auditor-register-data';
import { ActorUpdateData } from './class/actor-update-data';
import { Audit } from '../model/audit';
import { LongWrapper } from './class/long-wrapper';

@Injectable({
  providedIn: 'root'
})
export class AuditorService {

  endpoint = `${env.api}auditor`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  register(registerData: AuditorRegisterData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}`, registerData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

  list() {
    return lastValueFrom(
      this.http.get<Auditor[]>(
        `${this.endpoint}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  get(auditorId: number) {
    return lastValueFrom(
      this.http.get<Auditor>(
        `${this.endpoint}/${auditorId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  update(auditorId: number, updateData: ActorUpdateData) {
    return lastValueFrom(
      this.http.put<Auditor>(
        `${this.endpoint}/${auditorId}`, updateData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listAudits(auditorId: number) {
    return lastValueFrom(
      this.http.get<Audit[]>(
        `${this.endpoint}/${auditorId}/audit`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

}
