import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Audit } from 'src/app/model/audit';
import { Auditor } from 'src/app/model/auditor';
import { Property } from 'src/app/model/property';
import { PropertyAttributeValue } from 'src/app/model/property-attribute-value';
import { ActorService } from 'src/app/service/actor.service';
import { AccessService, UserType } from 'src/app/service/helpers/access.service';
import { PropertyService } from 'src/app/service/property.service';
import { AuditDialogComponent } from '../dialog/audit-dialog/audit-dialog.component';
import { AuditEditDialogComponent } from '../dialog/audit-edit-dialog/audit-edit-dialog.component';
import { CreatePropertyDialogComponent } from '../dialog/create-property-dialog/create-property-dialog.component';
import { RequestMakeDialogComponent } from '../dialog/request-make-dialog/request-make-dialog.component';

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.css']
})
export class PropertyComponent {

  userType!: UserType;
  mine: boolean = false;

  property: Property = new Property;
  images: string[] = [];
  attributes: PropertyAttributeValue[] = [];
  country: string = "";
  state: string = "";
  city: string = "";
  province: string = "";
  capacity: string = "";
  audits: Audit[] = [];

  constructor(
    private readonly dialog: MatDialog,
    private readonly accessService: AccessService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly actorService: ActorService,
    private readonly propertyService: PropertyService
  ) {
    const id = this.activatedRoute.snapshot.paramMap.get("id");
    this.userType = this.accessService.getUserType();
    this.propertyService.get(Number(id)).then(property => {
      this.setProperty(property);
    });
  }

  setProperty(property: Property){
    this.property = property;
    this.images = property.pictures.map(p => p.value);
    if(this.images.length === 0) this.images.push("assets/placeholder.png");
    this.attributes = property.attributes.filter(a => !a.attribute.sysDefault);
    this.country = property.attributes.find(a => a.attribute.name==="Country")?.value??"";
    this.state = property.attributes.find(a => a.attribute.name==="State")?.value??"";
    this.city = property.attributes.find(a => a.attribute.name==="City")?.value??"";
    this.province = property.attributes.find(a => a.attribute.name==="Province")?.value??"";
    this.capacity = property.attributes.find(a => a.attribute.name==="Capacity")?.value??"";

    if(this.accessService.isLoggedIn()){
      this.propertyService.listAudits(this.property.id).then(audits => this.audits = audits);
      this.mine = this.accessService.getUserId() === this.property.propietary.id;
    }
  }

  onCreateRequest(){
    this.dialog.open(RequestMakeDialogComponent, {data: {property: this.property}});
  }

  onCreateAudit(){
    const myId = this.accessService.getUserId();
    if(myId === null) return;
    this.actorService.get(myId).then(actor => {
      this.propertyService.createAudit(this.property.id).then(id => {
        let audit = new Audit;
        audit.id = id;
        audit.property = this.property;
        audit.date = new Date();
        audit.draft = true;
        audit.author = actor as Auditor;
        this.dialog.open(AuditEditDialogComponent, {data: {audit}})
        .afterClosed().subscribe(newAudit => { if(newAudit && !newAudit.draft) this.audits.push(newAudit); });
      });
    });
    
  }

  onUpdate(){
    this.dialog.open(CreatePropertyDialogComponent, {data: {property: this.property}, width: "400px"})
    .afterClosed().subscribe(property => { if(property) this.setProperty(property); });
  }

  onOpenAudit(audit: Audit){
    this.dialog.open(AuditDialogComponent, {data: {audit: audit}, maxWidth: "400px"});
  }
  
}
