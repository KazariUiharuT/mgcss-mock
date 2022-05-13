import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { SocialIdentity } from '../model/social-identity';
import { SocialIdentityCreationData } from './class/social-identity-creation-data';
import { Actor } from '../model/actor';
import { LongWrapper } from './class/long-wrapper';
import { ActorUpdateData } from './class/actor-update-data';

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  endpoint = `${env.api}actor`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) {
  }
  
  get(actorId: number) {
    return lastValueFrom(
      this.http.get<Actor>(
        `${this.endpoint}/${actorId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }
  
  update(actorId: number, updateData: ActorUpdateData) {
    return lastValueFrom(
      this.http.put<Actor>(
        `${this.endpoint}/${actorId}`, updateData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listSocialIdentities(actorId: number) {
    return lastValueFrom(
      this.http.get<SocialIdentity[]>(
        `${this.endpoint}/${actorId}/social-identity`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  createSocialIdentity(actorId: number, creationData: SocialIdentityCreationData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}/${actorId}/social-identity`, creationData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

}
