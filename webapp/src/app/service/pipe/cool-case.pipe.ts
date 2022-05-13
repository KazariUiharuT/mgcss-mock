import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'coolCase'
})
export class CoolCasePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    return value.split(" ").map(s => s.charAt(0).toUpperCase() + s.slice(1).toLowerCase()).join(" ");
  }

}
