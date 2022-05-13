import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { PropertyWithNumber } from 'src/app/service/class/property-with-number';

@Component({
  selector: 'dashboard-property-list',
  templateUrl: './dashboard-property-list.component.html',
  styleUrls: ['./dashboard-property-list.component.css']
})
export class DashboardPropertyListComponent {

  @Input() properties: PropertyWithNumber[] = [];
  @Input() roundMode: string = "0.0-0";
  @Input() sufix: string = "";

  constructor(
    private readonly router: Router
  ) { }

  goToProperty(id: number){
    this.router.navigate([`/property/${id}`]);
  }

}
