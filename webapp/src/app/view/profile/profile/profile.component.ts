import { Component } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Actor } from 'src/app/model/actor';
import { SocialIdentity } from 'src/app/model/social-identity';
import { ActorService } from 'src/app/service/actor.service';
import { AccessService } from 'src/app/service/helpers/access.service';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  mySelf: boolean = false;
  actor: Actor = new Actor;
  socials: SocialIdentity[] = [];
  editing: boolean = false;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly accessService: AccessService,
    private readonly actorService: ActorService
  ) {
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      const id = Number(params.get("id"));
      this.mySelf = id === this.accessService.getUserId();
      this.actorService.get(id).then(actor => this.actor = actor);
      this.actorService.listSocialIdentities(id).then(socials => this.socials = socials);
    });
  }

}
