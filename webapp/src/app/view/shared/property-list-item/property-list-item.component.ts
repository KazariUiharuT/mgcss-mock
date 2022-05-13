import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Property } from 'src/app/model/property';
import { AccessService } from 'src/app/service/helpers/access.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { PropertyService } from 'src/app/service/property.service';
import { CreatePropertyDialogComponent } from '../../dialog/create-property-dialog/create-property-dialog.component';

@Component({
  selector: 'property-list-item',
  templateUrl: './property-list-item.component.html',
  styleUrls: ['./property-list-item.component.css']
})
export class PropertyListItemComponent {

  private _property: Property = new Property;
  get property(): Property {return this._property;}
  @Input() set property(val: Property) {
    this._property = val;
    this.mine = val.propietary.id === this.accessService.getUserId();
  }
  mine: boolean = false;

  @Output() onDeleteProperty = new EventEmitter<number>();

  constructor(
    private readonly router: Router,
    private readonly translator: TranslateService,
    private readonly dialog: MatDialog,
    private readonly popUpService: PopUpService,
    private readonly accessService: AccessService,
    private readonly propertyService: PropertyService,
  ) { }

  onGoToProperty() {
    this.router.navigate([`/property/${this.property.id}`]);
  }

  onDelete(event: Event){
    event.stopPropagation();
    this.popUpService.Confirm(this.translator.instant("DIALOG.DELETEPROPERTY.TITLE"), this.translator.instant("DIALOG.DELETEPROPERTY.TEXT"), this.translator.instant("DIALOG.DELETEPROPERTY.YES"), this.translator.instant("DIALOG.DELETEPROPERTY.NO")).then(_ => {
    this.propertyService.delete(this.property.id).then(_ => this.onDeleteProperty.emit(this.property.id));
    }).catch(_ => {});
  }

  onUpdate(event: Event){
    event.stopPropagation();
    this.dialog.open(CreatePropertyDialogComponent, {data: {property: this.property}, width: "400px"})
    .afterClosed().subscribe(property => { if(property) this.property = property; });
  }
  
}
