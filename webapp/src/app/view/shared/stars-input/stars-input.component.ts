import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'stars-input',
  templateUrl: './stars-input.component.html',
  styleUrls: ['./stars-input.component.css']
})
export class StarsInputComponent {

  hoverStars: number | null = null;
  @Input()  stars: number = 0;
  @Output() starsChange = new EventEmitter<number>();

  constructor() { }

  onHover(stars: number) {
    this.hoverStars = stars;
  }

  onNoHover() {
    this.hoverStars = null;
  }

  onSelect(stars: number) {
    this.stars = stars;
    this.starsChange.emit(stars);
  }

}
