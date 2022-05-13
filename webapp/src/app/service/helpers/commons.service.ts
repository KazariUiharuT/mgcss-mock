import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { AccessService } from './access.service';
import { TranslateService } from '@ngx-translate/core';
import {  SafeUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CommonsService {
  
  constructor(
    private readonly translator: TranslateService,
    private readonly accessService: AccessService,
    private readonly router: Router,
    private readonly translate: TranslateService,
    private readonly http: HttpClient
  ) {
    let lang = localStorage["lang"]??"en";
    this.translator.setDefaultLang(lang);
    this.translator.use(lang);
    console.log(lang);
  }

  changeLang(lang: "en" | "es"){
    localStorage.setItem("lang", lang);
    this.translate.use(lang);
  }

  getHeaders(): HttpHeaders {
    const bearer = this.accessService.getBearer();
    let headers: any = {};

    headers["Content-Type"] = "application/json";
    if(bearer) headers["Authorization"] = `Bearer ${bearer}`;
    
    return new HttpHeaders(headers);
  }

  downloadBlob(blob: Blob, filename: string){
    const a = document.createElement("a");
    document.body.appendChild(a);
    a.style.display = "none";
    const url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  removeAccents(text: string): string {
    return text.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
  }

  strInStr(haystack: string, needle: string): boolean {
    return this.removeAccents(haystack).toLowerCase().includes(this.removeAccents(needle).toLowerCase());
  }

  arr2obj(arr: any[]): any{
    let obj: any = {};
    arr.forEach(a => obj[a] = a);
    return obj;
  }

  getBase64ImageFromUrl(imageUrl: string): Promise<SafeUrl> {
    return new Promise((resolve, reject) => {
      this.http.get(imageUrl, { responseType: "blob" }).subscribe(blob => {
        this.blobToBase64(blob).then(base64 => {
          resolve(base64)
        });
      });
    });
  }

  blobToBase64(blob: Blob): Promise<string> {
    return new Promise((resolve, _) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result as string);
      reader.readAsDataURL(blob);
    });
  }

  openFileNewWindow(title: string, data: string, inNewWindow = false) {
    const ref: any = window.open("", "", inNewWindow?"height=800,width=800":undefined);
    ref.document.title = title;
    const type = data.split(";")[0].split(":")[1];
    let el;
    if (type === "application/pdf") {
      el = ref.document.createElement("object");
      el.data = data;
      el.type = "application/pdf";
    } else if(type === "image/png" || type === "image/jpeg") {
      el = ref.document.createElement("img");
      el.src = data;
      el.style.objectFit = "contain";
    }else{
      el = ref.document.createElement("object");
      el.data = data;
    }
    el.style.height = "100%";
    el.style.width = "100%";
    ref.document.body.appendChild(el);
    ref.document.body.style.margin = "0";
  }

  navigate(url: string) {
    this.router.navigate(["/empty"]);
    setTimeout(() => this.router.navigate([url]), 0);
  }

  clone<T>(obj: T): T {
    return JSON.parse(JSON.stringify(obj));
  }
  
}
