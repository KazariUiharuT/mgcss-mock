import { Pipe, PipeTransform } from '@angular/core';
import { Phone } from 'src/app/model/datatype/phone';

@Pipe({
  name: 'phone'
})
export class PhonePipe implements PipeTransform {

  transform(value: Phone, ...args: unknown[]): string {
    if(value)
      return `${value.prefix} ${value.number}`;
    else
      return "";
  }

}
