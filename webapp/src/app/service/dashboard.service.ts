import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom } from 'rxjs';
import { DashboardData } from './class/dashboard-data';
import { DashboardLessorData } from './class/dashboard-lessor-data';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  endpoint = `${env.api}dashboard`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  get() {
    return lastValueFrom(
      this.http.get<DashboardData>(
        `${this.endpoint}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  getLessor(lessorId: number) {
    return lastValueFrom(
      this.http.get<DashboardLessorData>(
        `${this.endpoint}/lessor/${lessorId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  testql(ql: string) {
    return lastValueFrom(
      this.http.post<any>(
        `${this.endpoint}/testql`, ql,
        { headers: this.commonsService.getHeaders() }
      ));
  }
}
