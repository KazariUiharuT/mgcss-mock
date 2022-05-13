import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { ActorRegisterData } from './class/actor-register-data';
import { LongWrapper } from './class/long-wrapper';
import { Tenant } from '../model/tenant';
import { CommentCreationData } from './class/comment-creation-data';
import { Comment } from '../model/comment';
import { Request } from '../model/request';
import { CreditCard } from '../model/datatype/credit-card';
import { BooleanWrapper } from './class/boolean-wrapper';

@Injectable({
  providedIn: 'root'
})
export class TenantService {

  endpoint = `${env.api}tenant`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  register(registerData: ActorRegisterData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}`, registerData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

  getCreditCard(lessorId: number) {
    return lastValueFrom(
      this.http.get<CreditCard | null>(
        `${this.endpoint}/${lessorId}/credit-card`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  getSmoker(lessorId: number) {
    return lastValueFrom(
      this.http.get<BooleanWrapper | null>(
        `${this.endpoint}/${lessorId}/smoker`,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r === null ? null : r.value)));
  }

  updateCreditCard(tenantId: number, creditCard: CreditCard) {
    return lastValueFrom(
      this.http.patch(
        `${this.endpoint}/${tenantId}/credit-card`, creditCard,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  updateSmoker(tenantId: number, smoker: boolean) {
    return lastValueFrom(
      this.http.patch(
        `${this.endpoint}/${tenantId}/smoker`, smoker,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listRequests(tenantId: number) {
    return lastValueFrom(
      this.http.get<Request[]>(
        `${this.endpoint}/${tenantId}/request`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listComments(tenantId: number) {
    return lastValueFrom(
      this.http.get<Comment[]>(
        `${this.endpoint}/${tenantId}/comment`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  createComment(tenantId: number, creationData: CommentCreationData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}/${tenantId}/comment`, creationData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

}
