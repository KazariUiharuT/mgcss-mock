import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom } from 'rxjs';
import { SocialIdentityUpdateData } from './class/social-identity-update-data';

@Injectable({
  providedIn: 'root'
})
export class SocialIdentityService {

  endpoint = `${env.api}social-identity`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  update(socialId: number, updateData: SocialIdentityUpdateData) {
    return lastValueFrom(
      this.http.put(
        `${this.endpoint}/${socialId}`, updateData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  delete(socialId: number) {
    return lastValueFrom(
      this.http.delete(
        `${this.endpoint}/${socialId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

}
