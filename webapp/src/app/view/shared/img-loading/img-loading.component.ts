import { Component, ElementRef, Input, ViewChild, AfterViewInit } from '@angular/core';

@Component({
  selector: 'img-loading',
  templateUrl: './img-loading.component.html',
  styleUrls: ['./img-loading.component.css']
})
export class ImgLoadingComponent implements AfterViewInit {

  @ViewChild('loadingDiv') loadingDiv!: ElementRef<HTMLDivElement>;
  @Input() src: string = "";
  loaded: boolean = false;

  constructor() { }

  ngAfterViewInit(){
    this.onResize();
  }

  onLoad(){
    this.loaded=true;
    this.onResize();
  }

  onResize(){
    if(!this.loaded && this.loadingDiv.nativeElement.clientWidth != 0)
      this.loadingDiv.nativeElement.style.height = `${this.loadingDiv.nativeElement.clientWidth}px`;
  }
}
