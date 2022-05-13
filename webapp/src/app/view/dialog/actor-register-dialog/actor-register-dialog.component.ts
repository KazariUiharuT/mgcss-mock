import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ActorRegisterData } from 'src/app/service/class/actor-register-data';
import { AccessService, UserType } from 'src/app/service/helpers/access.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { LessorService } from 'src/app/service/lessor.service';
import { TenantService } from 'src/app/service/tenant.service';

@Component({
  selector: 'app-actor-register-dialog',
  templateUrl: './actor-register-dialog.component.html',
  styleUrls: ['./actor-register-dialog.component.css']
})
export class ActorRegisterDialogComponent {

  actor: ActorRegisterData = new ActorRegisterData;
  pwd1: string = "";
  pwd2: string = "";
  type: UserType | null = null;

  constructor(
    public dialogRef: MatDialogRef<ActorRegisterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {},
    private readonly translator: TranslateService,
    private readonly popUpService: PopUpService,
    private readonly lessorService: LessorService,
    private readonly tenantService: TenantService,
    private readonly accessService: AccessService
  ) { }

  onCancel(){
    this.dialogRef.close();
  }

  onRegister(){
    if(this.type === null){
      this.popUpService.Error(this.translator.instant("DIALOG.REGISTER.EMPTYTYPE"));
      return;
    }

    if(this.pwd1 !== this.pwd2){
      this.popUpService.Error(this.translator.instant("DIALOG.REGISTER.PWDMISMATCH"));
      return;
    }
    this.actor.pwd = this.pwd1;

    this.actor.name = this.actor.name.trim();
    this.actor.surname = this.actor.surname.trim();
    this.actor.email = this.actor.email.trim();
    if(this.actor.name.length === 0 || this.actor.surname.length === 0 || this.actor.email.length === 0 || this.actor.pwd.length === 0){
      this.popUpService.Error(this.translator.instant("DIALOG.REGISTER.EMPTY"));
      return;
    }

    let loginEvent = () => {
      this.accessService.login(this.actor.email, this.actor.pwd).then(() => {
        this.dialogRef.close(true);
      });
    }

    switch(this.type){
      case "lessor":
        this.lessorService.register(this.actor).then(loginEvent);
        break;
      case "tenant":
        this.tenantService.register(this.actor).then(loginEvent);
        break;
    }
  }

}
