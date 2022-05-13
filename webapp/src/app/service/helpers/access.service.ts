import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment as env } from '../../../environments/environment';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccessService {

  jwt: string | null = null;
  userId: number | null = null;
  userType: UserType = "guest";

  constructor(
    private readonly http: HttpClient
  ) {
    this.loadFromSessionStorage();
  }

  loadFromSessionStorage() {
    const jwt = window.sessionStorage.getItem('jwt');
    const userId = window.sessionStorage.getItem('userId');
    const userType = window.sessionStorage.getItem('userType') as UserType;
    if(jwt && userId) {
      this.setJwt(jwt, parseInt(userId), userType);
    }
  }

  isLoggedIn(): boolean {
    this.loadFromSessionStorage();
    return this.jwt !== null;
  }

  getUserId(): number | null {
    this.loadFromSessionStorage();
    return this.userId;
  }

  getUserType(): UserType {
    this.loadFromSessionStorage();
    return this.userType;
  }

  getBearer(): string | null {
    this.loadFromSessionStorage();
    if(this.jwt) {
      return `Bearer ${this.jwt}`;
    }
    return null;
  }

  setJwt(jwt: string, userId: number, userType: UserType): void {
    this.jwt = jwt;
    this.userId = userId;
    this.userType = userType;
    window.sessionStorage.setItem('jwt', jwt);
    window.sessionStorage.setItem('userId', userId.toString());
    window.sessionStorage.setItem('userType', userType);
  }

  unsetJwt(): void {
    this.jwt = null;
    this.userId = null;
    this.userType = "guest";
    window.sessionStorage.removeItem('jwt');
    window.sessionStorage.removeItem('userId');
    window.sessionStorage.removeItem('userType');
  }

  async login(email: string, pwd: string) {
      let result = await lastValueFrom(
        this.http.post<LoginResponse>(
          `${env.api}services/controller/user/login`,
          {email, pwd}
      ));
      this.setJwt(result.jwt, result.userId, result.userType);
      return result;
  }
}

export type UserType = "guest" | "admin" | "lessor" | "tenant" | "auditor";

export interface LoginResponse {
  jwt: string,
  userId: number,
  userType: UserType;
}

export class ApiError {
  timestamp: Date = new Date;
  message: string = "";
  code: number = 0;
}