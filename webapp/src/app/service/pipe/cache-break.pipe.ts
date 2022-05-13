import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'cacheBreak'
})
export class CacheBreakPipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    if (value.includes("http"))
      return value + (value.includes("?") ? "&" : "?") + 'v=' + Date.now();
    else
      return value;
  }

}
