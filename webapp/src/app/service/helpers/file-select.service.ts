import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class FileSelectService {

  static resolve: any;
  static reject: any;

  static SelectPic() {
    return FileSelectService.Select("image/*");
  }

  static Select(filter: string) : Promise<string>{
    return new Promise((resolve, reject) => {
      FileSelectService.resolve = resolve;
      FileSelectService.reject = reject;

      let input = document.createElement("input");
      input.type = "file";
      input.accept = filter;
      input.onchange = FileSelectService.onSelect;
      input.click();
    });
  }

  static onSelect(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.onload = (event: any) => {
        FileSelectService.resolve(event.target.result);
      }
      reader.readAsDataURL(event.target.files[0]);
    }else{
      FileSelectService.reject();
    }
  }

  constructor() { }
}
