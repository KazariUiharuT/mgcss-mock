import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ActorWithNumber } from 'src/app/service/class/actor-with-number';

@Component({
  selector: 'dashboard-actor-list',
  templateUrl: './dashboard-actor-list.component.html',
  styleUrls: ['./dashboard-actor-list.component.css']
})
export class DashboardActorListComponent {

  @Input() actors: ActorWithNumber[] = [];
  @Input() roundMode: string = "0.0-0";
  @Input() sufix: string = "";

  constructor(
    private readonly router: Router
  ) { }

  goToActor(id: number){
    this.router.navigate([`/profile/${id}`]);
  }

}
