import { Component, Input } from '@angular/core';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { Property } from 'src/app/model/property';
import { CommonsService } from 'src/app/service/helpers/commons.service';

@Component({
  selector: 'property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent {

  filteredProperties: Property[] = [];

  private _properties: Property[] = [];
  get properties(): Property[] { return this._properties; }
  @Input() set properties(value: Property[]) {
    this._properties = value;
    this.sortAndfilterProperties();
  }

  private _key: string = "";
  get key(): string { return this._key; }
  set key(value: string) {
    this._key = value.trim();
    this.sortAndfilterProperties();
  }

  sortByRequests: boolean = false;

  constructor(
    private readonly commonsService: CommonsService
  ) { }

  onSortProperties(event: MatSlideToggleChange) {
    this.sortByRequests = event.checked;
    this.sortAndfilterProperties();
  }

  sortAndfilterProperties() {
    this.filteredProperties = this.properties.filter(
      p => this.commonsService.strInStr(p.name, this.key) ||
        this.commonsService.strInStr(p.description, this.key) ||
        this.commonsService.strInStr(p.address, this.key)
    );
    if (this.sortByRequests) {
      this.filteredProperties.sort((a, b) => b.nrequests - a.nrequests);
    } else {
      this.filteredProperties.sort((a, b) => (a.date > b.date) ? -1 : 1);
    }
  }

  onDelete(id: number) {
    this.properties.splice(this.properties.findIndex(p => p.id === id), 1);
  }

}
