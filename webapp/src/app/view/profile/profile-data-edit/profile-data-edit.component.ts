import { Component, Input } from '@angular/core';
import { Actor } from 'src/app/model/actor';
import { COUNTRIES, SOCIAL_NETWORKS } from 'src/app/service/helpers/constants.service';
import { FileSelectService } from 'src/app/service/helpers/file-select.service';
import { SearchItem } from '../../shared/search-selector/search-selector.component';
import { CommonsService } from 'src/app/service/helpers/commons.service';
import { SocialIdentity } from 'src/app/model/social-identity';
import { SocialIdentityService } from 'src/app/service/social-identity.service';
import { ActorService } from 'src/app/service/actor.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { TranslateService } from '@ngx-translate/core';
import Swal from "sweetalert2";

@Component({
  selector: 'profile-data-edit',
  templateUrl: './profile-data-edit.component.html',
  styleUrls: ['./profile-data-edit.component.css']
})
export class ProfileDataEditComponent {

  private _actor: Actor = new Actor;
  get actor(): Actor {return this._actor; }
  @Input() set actor(val: Actor) {
    this._actor = val;
    this.prefix = this.prefixes.find(p => p.value.iso3 === this.actor.phone.country)??this.prefixes[0];
  };
  newPicture: string | null = null;
  @Input() set socials(val: SocialIdentity[]){
    this.updatedSocials  = val.map(v => ({unsaved: false, ...v} as UpdateableSocialIdentity));
  }
  updatedSocials: UpdateableSocialIdentity[] = [];

  prefixes: SearchItem[] = [];
  private _prefix!: SearchItem;
  get prefix(): SearchItem {return this._prefix}
  set prefix(val: SearchItem) {
    this._prefix = val;
    this.actor.phone.country = val.value.iso3;
  }

  constructor(
    private readonly translator: TranslateService,
    private readonly popUpService: PopUpService,
    private readonly commonsService: CommonsService,
    private readonly actorService: ActorService,
    private readonly socialIdentityService: SocialIdentityService
  ) {
    this.prefixes = COUNTRIES.map(c => ({text: `${c.name} +${c.code}`, value: c, keys: [c.name, `+${c.code}`]} as SearchItem));
  }

  onChangePic(){
    FileSelectService.SelectPic()
    .then(pic => {
      console.log(pic);
      this.newPicture = pic as string;
    })
    .catch(_ => {});
  }

  async onAddSocial(){
    const { value: network } = await Swal.fire({
      title: this.translator.instant("DIALOG.MAKESOCIAL.TITLE"),
      input: "select",
      inputOptions: this.commonsService.arr2obj(SOCIAL_NETWORKS),
      inputPlaceholder: this.translator.instant("DIALOG.MAKESOCIAL.SELECT"),
      showCancelButton: true,
      inputValidator: (value) => {
        return new Promise((resolve) => {
          if (value) {
            resolve("");
          } else {
            resolve(this.translator.instant("DIALOG.MAKESOCIAL.EMPTYSELECT"));
          }
        })
      }
    })
    
    if (network) {
      const result: any = await Swal.fire({
        title: this.translator.instant("DIALOG.MAKESOCIAL.INSERTTITLE"),
        html: `<input type="text" id="nick" class="swal2-input" placeholder="${this.translator.instant("DIALOG.MAKESOCIAL.NICK")}">
        <input type="text" id="url" class="swal2-input" placeholder="${this.translator.instant("DIALOG.MAKESOCIAL.URL")}">`,
        confirmButtonText: this.translator.instant("DIALOG.MAKESOCIAL.ADD"),
        focusConfirm: false,
        preConfirm: () => {
          const nick = (Swal.getPopup()?.querySelector("#nick") as HTMLInputElement).value
          const url = (Swal.getPopup()?.querySelector("#url") as HTMLInputElement).value
          if (!nick || !url) {
            Swal.showValidationMessage(this.translator.instant("DIALOG.MAKESOCIAL.EMPTY"));
          }
          return { nick, url }
        }
      });

      if(result.isConfirmed){
        let social = {nick: result.value.nick, url: result.value.url, socialNetwork: network, unsaved: false} as UpdateableSocialIdentity;
        this.actorService.createSocialIdentity(this.actor.id, social).then(newId => {
          social.id = newId;
          this.updatedSocials.push(social);
        });
      }
    }
  }

  onUpdateSocial(social: UpdateableSocialIdentity){
    this.socialIdentityService.update(social.id, social).then(_ => social.unsaved = false);
  }

  onRemoveSocial(social: UpdateableSocialIdentity){
    this.popUpService.Confirm(this.translator.instant("DIALOG.DELETESOCIAL.TITLE"), this.translator.instant("DIALOG.DELETESOCIAL.TEXT"), this.translator.instant("DIALOG.DELETESOCIAL.YES"), this.translator.instant("DIALOG.DELETESOCIAL.NO")).then(_ => {
      this.socialIdentityService.delete(social.id).then(_ => this.updatedSocials.splice(this.updatedSocials.findIndex(s => s === social), 1));
    }).catch(_ => {});
  }

  onSave(){
    if(this.newPicture !== null) this.actor.picture = this.newPicture;
    this.actorService.update(this.actor.id, this.actor).then(_ => {
      this.onExit();
    });
  }

  onExit(){
    this.commonsService.navigate(`/profile/${this.actor.id}`);
  }

}

interface UpdateableSocialIdentity extends SocialIdentity {
  unsaved: boolean;
}
