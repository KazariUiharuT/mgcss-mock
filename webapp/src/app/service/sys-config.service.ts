import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { SysConfig } from '../model/sys-config';
import { StringWrapper } from './class/string-wrapper';

@Injectable({
  providedIn: 'root'
})
export class SysConfigService {

  endpoint = `${env.api}sys-config`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  list() {
    return lastValueFrom(
      this.http.get<SysConfig[]>(
        `${this.endpoint}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  get(configName: string) {
    return lastValueFrom(
      this.http.get<StringWrapper>(
        `${this.endpoint}/${configName}`,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

  set(configName: string, value: string) {
    return lastValueFrom(
      this.http.patch(
        `${this.endpoint}/${configName}`, new StringWrapper(value),
        { headers: this.commonsService.getHeaders() }
      ));
  }

}
