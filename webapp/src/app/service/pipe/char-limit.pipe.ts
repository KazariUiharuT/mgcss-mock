import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'charLimit'
})
export class CharLimitPipe implements PipeTransform {

  transform(value: string, ...args: number[]): string {
    const limit = args[0]??50;
    if(value.length < limit)
      return value;
    else
      return `${value.substring(0, limit)}...`;
  }

}
