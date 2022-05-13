import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NavigationEnd, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { filter } from 'rxjs';
import { AccessService, UserType } from 'src/app/service/helpers/access.service';
import { CommonsService } from 'src/app/service/helpers/commons.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { ActorRegisterDialogComponent } from '../../dialog/actor-register-dialog/actor-register-dialog.component';
import Swal from "sweetalert2";

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent {

  userType: UserType = "guest";
  lang: string;

  constructor(
    private readonly router: Router,
    private readonly dialog: MatDialog,
    private readonly translate: TranslateService,
    private readonly popUpService: PopUpService,
    private readonly commonsService: CommonsService,
    private readonly accessService: AccessService
  ) {
    this.lang = this.translate.currentLang;
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(event => {
      this.userType = this.accessService.getUserType();
    });
  }

  onGoToHome() {
    this.commonsService.navigate("/");
  }

  onGoToProfile() {
    this.commonsService.navigate(`/profile/${this.accessService.getUserId()}`);
  }

  onGoToAdminPanel() {
    this.commonsService.navigate("/admin");
  }

  onOpenLogin() {
    Swal.fire({
      title: 'Login',
      html: `<input type="email" id="login" class="swal2-input" placeholder="${this.translate.instant("SHARED.EMAIL")}">
      <input type="password" id="password" class="swal2-input" placeholder="${this.translate.instant("SHARED.PASSWORD")}">`,
      confirmButtonText: this.translate.instant("SHARED.TOPBAR.LOGIN"),
      focusConfirm: false,
      preConfirm: () => {
        const login = (Swal.getPopup()?.querySelector('#login') as HTMLInputElement).value
        const password = (Swal.getPopup()?.querySelector('#password') as HTMLInputElement).value
        if (!login || !password) {
          Swal.showValidationMessage(this.translate.instant("DIALOG.LOGIN.EMPTY"));
        }
        return { login: login, password: password }
      }
    }).then(result => {
      if(result.isConfirmed){
        this.accessService.login(result.value?.login??"", result.value?.password??"")
        .then(_ => this.commonsService.navigate("/"))
        .catch(err => {
          this.popUpService.Error(this.translate.instant("DIALOG.LOGIN.ERROR"));
        });
      }
    });
  }

  onOpenRegister(){
    this.dialog.open(ActorRegisterDialogComponent)
    .afterClosed().subscribe(result => {
      if(result) this.commonsService.navigate("/")
    });
  }

  setLang(lang: 'es' | 'en') {
    this.commonsService.changeLang(lang);
    this.lang = lang;
  }

}
