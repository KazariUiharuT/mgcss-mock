import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Actor } from 'src/app/model/actor';
import { Property } from 'src/app/model/property';
import { LessorService } from 'src/app/service/lessor.service';
import { CreatePropertyDialogComponent } from '../../dialog/create-property-dialog/create-property-dialog.component';

@Component({
  selector: 'profile-properties',
  templateUrl: './profile-properties.component.html',
  styleUrls: ['./profile-properties.component.css']
})
export class ProfilePropertiesComponent {

  @Input() set actor(val: Actor) {
    this.lessorService.listProperties(val.id).then(properties => this.properties = properties);
  }
  @Input() mySelf: boolean = false;
  properties: Property[] = [];

  constructor(
    private readonly dialog: MatDialog,
    private readonly lessorService: LessorService
  ) { }

  onCreateProperty() {
    this.dialog.open(CreatePropertyDialogComponent, {data: {}, width: "400px"})
    .afterClosed().subscribe(property => {if(property) this.properties.unshift(property)});
  }

}
