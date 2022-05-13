import { Component, OnInit } from '@angular/core';
import { Property } from 'src/app/model/property';
import { CommonsService } from 'src/app/service/helpers/commons.service';
import { PropertyService } from 'src/app/service/property.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  properties: Property[] = [];
  filteredProperties: Property[] = [];

  constructor(
    private readonly propertyService: PropertyService
  ) { }

  ngOnInit(): void {
    this.propertyService.list().then(properties => this.properties = properties);
  }

}
