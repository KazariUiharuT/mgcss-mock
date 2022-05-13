import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Actor } from 'src/app/model/actor';

@Component({
  selector: 'actor-info',
  templateUrl: './actor-info.component.html',
  styleUrls: ['./actor-info.component.css']
})
export class ActorInfoComponent {

  @Input() actor: Actor = new Actor;

  constructor(
    private readonly router: Router
  ) { }

  onGoToProfile(){
    this.router.navigate([`/profile/${this.actor.id}`]);
  }

}
