import { Pipe, PipeTransform } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Pipe({
  name: 'money'
})
export class MoneyPipe implements PipeTransform {

  constructor(
    private readonly translator: TranslateService,
  ) { }

  transform(value: number, ...args: string[]): unknown {
    let simbol = "€";
    if(args[0] && args[0] === "rate") simbol = `€/${this.translator.instant("SHARED.DAY")}`;
    return `${(Math.round(value * 100) / 100).toFixed(2)} ${simbol}`;
  }

}
