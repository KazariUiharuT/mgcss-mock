import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { PropertyAttribute } from 'src/app/model/property-attribute';
import { PropertyAttributeData } from 'src/app/service/class/property-attribute-data';
import { PropertyAttributeService } from 'src/app/service/property-attribute.service';
import Swal from "sweetalert2";

@Component({
  selector: 'admin-property-attributes',
  templateUrl: './admin-property-attributes.component.html',
  styleUrls: ['./admin-property-attributes.component.css']
})
export class AdminPropertyAttributesComponent {

  attributes: PropertyAttribute[] = [];
  lastAttributes: any = {};

  constructor(
    private readonly translator: TranslateService,
    private readonly propertyAttributeService: PropertyAttributeService
  ) {
    this.propertyAttributeService.list().then(attributes => {
      this.attributes = attributes;
      this.attributes.forEach(a => this.lastAttributes[a.id] = a.name);
    });
  }

  onCreate(){
    Swal.fire({
      title: this.translator.instant('DIALOG.CREATEATTRIBUTE.TITLE'),
      input: 'text',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: this.translator.instant('SHARED.CREATE'),
      cancelButtonText: this.translator.instant('SHARED.CANCEL'),
      showLoaderOnConfirm: true,
      preConfirm: (nombre) => {
        nombre = nombre.trim();
        if(nombre.length === 0) Swal.showValidationMessage(this.translator.instant('DIALOG.CREATEATTRIBUTE.EMPTYNAME'));
        else return nombre;
      }
    }).then((result) => {
      if (result.isConfirmed) {
        const name = result.value;
        this.propertyAttributeService.create({name} as PropertyAttributeData).then(id => {
          this.attributes.push({id, name, sysDefault: false} as PropertyAttribute);
          this.lastAttributes[id] = name;
        });
      }
    })
  }

  onUpdate(attribute: PropertyAttribute){
    this.propertyAttributeService.update(attribute.id, {name: attribute.name} as PropertyAttributeData).then(_ => {
      this.lastAttributes[attribute.id] = attribute.name;
    });
  }

  onDelete(attribute: PropertyAttribute){
    this.propertyAttributeService.delete(attribute.id).then(_ => {
      this.attributes = this.attributes.filter(a => a.id !== attribute.id);
      delete this.lastAttributes[attribute.id];
    });
  }

}
