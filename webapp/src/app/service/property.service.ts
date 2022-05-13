import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { Property } from '../model/property';
import { LongWrapper } from './class/long-wrapper';
import { PropertyData } from './class/property-data';
import { RequestCreationData } from './class/request-creation-data';
import { Audit } from '../model/audit';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  endpoint = `${env.api}property`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  list() {
    return lastValueFrom(
      this.http.get<Property[]>(
        `${this.endpoint}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  get(propertyId: number) {
    return lastValueFrom(
      this.http.get<Property>(
        `${this.endpoint}/${propertyId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  create(creationData: PropertyData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}`, creationData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

  update(propertyId: number, updateData: PropertyData) {
    return lastValueFrom(
      this.http.put(
        `${this.endpoint}/${propertyId}`, updateData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  delete(propertyId: number) {
    return lastValueFrom(
      this.http.delete(
        `${this.endpoint}/${propertyId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  listRequests(propertyId: number) {
    return lastValueFrom(
      this.http.get<Request[]>(
        `${this.endpoint}/${propertyId}/request`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  createRequests(propertyId: number, creationData: RequestCreationData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}/${propertyId}/request`, creationData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

  listAudits(propertyId: number) {
    return lastValueFrom(
      this.http.get<Audit[]>(
        `${this.endpoint}/${propertyId}/audit`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  createAudit(propertyId: number) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}/${propertyId}/audit`, {},
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

}
