import { Component, Inject } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { Property } from 'src/app/model/property';
import { PropertyAttributeValueData } from 'src/app/service/class/property-attribute-value-data';
import { PropertyData } from 'src/app/service/class/property-data';
import { CommonsService } from 'src/app/service/helpers/commons.service';
import { FileSelectService } from 'src/app/service/helpers/file-select.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { PropertyAttributeService } from 'src/app/service/property-attribute.service';
import { PropertyService } from 'src/app/service/property.service';

@Component({
  selector: 'create-property-dialog',
  templateUrl: './create-property-dialog.component.html',
  styleUrls: ['./create-property-dialog.component.css']
})
export class CreatePropertyDialogComponent {

  propertyId: number | null = null; //If the user is creating a new property, the propertyId is null. If Updating a property, the propertyId is the id of the property to update.
  property: PropertyData = new PropertyData;
  attributes: Attribute[] = [];

  constructor(
    public dialogRef: MatDialogRef<CreatePropertyDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {property: Property | null},
    private readonly translator: TranslateService,
    private readonly popUpService: PopUpService,
    private readonly commonsService: CommonsService,
    private readonly propertyAttributeService: PropertyAttributeService,
    private readonly propertyService: PropertyService
  ) {
    if(data.property){
      this.propertyId = data.property.id;
      this.property = {...data.property, attributes: [], pictures: []} as PropertyData;
      data.property.pictures.forEach(p => this.commonsService.getBase64ImageFromUrl(p.value).then((b64: any) => this.property.pictures.unshift(b64)));
      this.attributes = data.property.attributes.map(a => ({id: a.attribute.id, name: a.attribute.name, obligatory: a.attribute.sysDefault, value: a.value} as Attribute));
    }
    this.propertyAttributeService.list().then(attributes => {
      attributes.map(attribute => ({id: attribute.id, name: attribute.name, obligatory: attribute.sysDefault, value: ""} as Attribute))
      .forEach(attribute => { if(this.attributes.every(a => a.id != attribute.id)) this.attributes.push(attribute); });
    });
    this.dialogRef.disableClose = true;
  }

  addPicture(){
    FileSelectService.SelectPic().then(file => this.property.pictures.unshift(file)).catch(_ => {});
  }

  removePicture(index: number){
    this.property.pictures.splice(index, 1);
  }

  onCancel(){
    this.dialogRef.close();
  }

  async onCreate(){
    this.property.name = this.property.name.trim();
    if(this.property.name.length == 0){
      this.popUpService.Error(this.translator.instant("DIALOG.MAKEPROPERTY.EMPTYNAME"));
      return;
    }

    this.property.description = this.property.description.trim();
    if(this.property.description.length == 0){
      this.popUpService.Error(this.translator.instant("DIALOG.MAKEPROPERTY.EMPTYDESCRIPTION"));
      return;
    }

    if(!this.property.rate){
      this.popUpService.Error(this.translator.instant("DIALOG.MAKEPROPERTY.EMPTYRATE"));
      return;
    }

    this.property.address = this.property.address.trim();
    if(this.property.address.length == 0){
      this.popUpService.Error(this.translator.instant("DIALOG.MAKEPROPERTY.EMPTYADDRESS"));
      return;
    }

    for(let i = this.attributes.length - 1; i >= 0; i--){
      this.attributes[i].value = this.attributes[i].value.trim();
    }
    if(this.attributes.some(a => a.obligatory && a.value.length == 0)){
      this.popUpService.Error(this.translator.instant("DIALOG.MAKEPROPERTY.EMPTYATTRIBUTE"));
      return;
    }

    this.popUpService.ShowLoading();

    this.property.attributes = this.attributes;
    this.property.pictures.reverse();
    let id: number;
    if(this.propertyId){
      await this.propertyService.update(this.propertyId, this.property);
      id = this.propertyId;
    }else{
      id = await this.propertyService.create(this.property);
    }
    let newProperty = await this.propertyService.get(id);

    this.popUpService.DisableLoading();
    this.dialogRef.close(newProperty);
  }

}

interface Attribute extends PropertyAttributeValueData {
  name: string;
  obligatory: boolean;
}