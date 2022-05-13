import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { RequestAcceptData } from './class/request-accept-data';
import { Invoice } from '../model/invoice';
import { LongWrapper } from './class/long-wrapper';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  endpoint = `${env.api}request`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  accept(requestId: number, acceptData: RequestAcceptData) {
    return lastValueFrom(
      this.http.patch(
        `${this.endpoint}/${requestId}/accepted`, acceptData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  getInvoice(requestId: number) {
    return lastValueFrom(
      this.http.get<Invoice>(
        `${this.endpoint}/${requestId}/invoice`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  createInvoice(requestId: number) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}/${requestId}/invoice`, {},
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

}
