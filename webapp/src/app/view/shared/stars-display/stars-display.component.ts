import { Component, Input } from '@angular/core';

@Component({
  selector: 'stars-display',
  templateUrl: './stars-display.component.html',
  styleUrls: ['./stars-display.component.css']
})
export class StarsDisplayComponent {

  @Input() stars: number = 0;

  constructor() { }

}
