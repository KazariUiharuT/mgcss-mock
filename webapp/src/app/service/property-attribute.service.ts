import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../environments/environment';
import { CommonsService } from './helpers/commons.service';
import { lastValueFrom, map } from 'rxjs';
import { PropertyAttribute } from '../model/property-attribute';
import { LongWrapper } from './class/long-wrapper';
import { PropertyAttributeData } from './class/property-attribute-data';

@Injectable({
  providedIn: 'root'
})
export class PropertyAttributeService {

  endpoint = `${env.api}property-attribute`;

  constructor(
    private readonly http: HttpClient,
    private readonly commonsService: CommonsService
  ) { }

  list() {
    return lastValueFrom(
      this.http.get<PropertyAttribute[]>(
        `${this.endpoint}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  create(creationData: PropertyAttributeData) {
    return lastValueFrom(
      this.http.post<LongWrapper>(
        `${this.endpoint}`, creationData,
        { headers: this.commonsService.getHeaders() }
      ).pipe(map(r => r.value)));
  }

  update(attributeId: number, updateData: PropertyAttributeData) {
    return lastValueFrom(
      this.http.put(
        `${this.endpoint}/${attributeId}`, updateData,
        { headers: this.commonsService.getHeaders() }
      ));
  }

  delete(attributeId: number) {
    return lastValueFrom(
      this.http.delete<LongWrapper>(
        `${this.endpoint}/${attributeId}`,
        { headers: this.commonsService.getHeaders() }
      ));
  }

}
