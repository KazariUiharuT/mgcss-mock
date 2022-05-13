import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { ActorRegisterData } from './class/actor-register-data';
import { LongWrapper } from './class/long-wrapper';
import { Lessor } from '../model/lessor';
import { Property } from '../model/property';
import { CommentCreationData } from './class/comment-creation-data';
import { Comment } from '../model/comment';
import { Request } from '../model/request';
import { CreditCard } from '../model/datatype/credit-card';

@Injectable({
  providedIn: 'root'
})
export class LessorService {

  endpoint = `${env.api}lessor`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  list() {
    return lastValueFrom(
      this.http.get<Lessor[]>(
        `${this.endpoint}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

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

  updateCreditCard(lessorId: number, creditCard: CreditCard) {
    return lastValueFrom(
      this.http.patch(
        `${this.endpoint}/${lessorId}/credit-card`, creditCard,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listProperties(lessorId: number) {
    return lastValueFrom(
      this.http.get<Property[]>(
        `${this.endpoint}/${lessorId}/property`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listRequests(lessorId: number) {
    return lastValueFrom(
      this.http.get<Request[]>(
        `${this.endpoint}/${lessorId}/request`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listComments(lessorId: number) {
    return lastValueFrom(
      this.http.get<Comment[]>(
        `${this.endpoint}/${lessorId}/comment`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  createComment(lessorId: number, creationData: CommentCreationData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}/${lessorId}/comment`, creationData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

}
