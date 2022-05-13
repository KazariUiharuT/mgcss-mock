import { Component, Input } from '@angular/core';
import { SocialIdentity } from 'src/app/model/social-identity';

@Component({
  selector: 'social-info',
  templateUrl: './social-info.component.html',
  styleUrls: ['./social-info.component.css']
})
export class SocialInfoComponent {

  @Input() socials: SocialIdentity[] = [];

  constructor() { }

  openSocial(social: SocialIdentity){
    window.open(social.url, '_blank');
  }

}
