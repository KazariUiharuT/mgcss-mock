import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom } from 'rxjs';
import { Administrator } from '../model/administrator';
import { ActorUpdateData } from './class/actor-update-data';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  endpoint = `${env.api}admin`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  get(adminId: number) {
    return lastValueFrom(
      this.http.get<Administrator>(
        `${this.endpoint}/${adminId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  update(adminId: number, updateData: ActorUpdateData) {
    return lastValueFrom(
      this.http.put(
        `${this.endpoint}/${adminId}`, updateData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

}
