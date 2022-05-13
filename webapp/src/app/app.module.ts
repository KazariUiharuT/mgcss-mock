import { ErrorHandler, NgModule } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { MatMomentDateModule } from "@angular/material-moment-adapter";
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMatSelectSearchModule } from "ngx-mat-select-search";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatTabsModule } from '@angular/material/tabs';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import {MatRadioModule} from '@angular/material/radio';

import { HomeComponent } from './view/home/home.component';
import { DefaultErrorHandler } from './service/helpers/default-error-handler';
import { PropertyComponent } from './view/property/property.component';
import { ProfileComponent } from './view/profile/profile/profile.component';
import { ProfileDataComponent } from './view/profile/profile-data/profile-data.component';
import { ProfileDataEditComponent } from './view/profile/profile-data-edit/profile-data-edit.component';
import { ProfilePropertiesComponent } from './view/profile/profile-properties/profile-properties.component';
import { ProfileRequestsComponent } from './view/profile/profile-requests/profile-requests.component';
import { ProfileAuditsComponent } from './view/profile/profile-audits/profile-audits.component';
import { AdminComponent } from './view/admin/admin/admin.component';
import { AdminDashboardComponent } from './view/admin/admin-dashboard/admin-dashboard.component';
import { AdminPropertyAttributesComponent } from './view/admin/admin-property-attributes/admin-property-attributes.component';
import { AdminConfigComponent } from './view/admin/admin-config/admin-config.component';
import { AdminAuditorListComponent } from './view/admin/admin-auditor-list/admin-auditor-list.component';
import { TopBarComponent } from './view/shared/top-bar/top-bar.component';
import { PropertyListComponent } from './view/shared/property-list/property-list.component';
import { PropertyListItemComponent } from './view/shared/property-list-item/property-list-item.component';
import { ActorInfoComponent } from './view/shared/actor-info/actor-info.component';
import { AuditDialogComponent } from './view/dialog/audit-dialog/audit-dialog.component';
import { AuditEditDialogComponent } from './view/dialog/audit-edit-dialog/audit-edit-dialog.component';
import { IconButtonComponent } from './view/shared/icon-button/icon-button.component';
import { EmptyMessageComponent } from './view/shared/empty-message/empty-message.component';
import { MoneyPipe } from './service/pipe/money.pipe';
import { CharLimitPipe } from './service/pipe/char-limit.pipe';
import { ImgLoadingComponent } from './view/shared/img-loading/img-loading.component';
import { PhonePipe } from './service/pipe/phone.pipe';
import { SocialInfoComponent } from './view/shared/social-info/social-info.component';
import { CoolCasePipe } from './service/pipe/cool-case.pipe';
import { SearchSelectorComponent } from './view/shared/search-selector/search-selector.component';
import { CacheBreakPipe } from './service/pipe/cache-break.pipe';
import { CreatePropertyDialogComponent } from './view/dialog/create-property-dialog/create-property-dialog.component';
import { StarsDisplayComponent } from './view/shared/stars-display/stars-display.component';
import { StarsInputComponent } from './view/shared/stars-input/stars-input.component';
import { RequestAcceptDialogComponent } from './view/dialog/request-accept-dialog/request-accept-dialog.component';
import { RequestMakeDialogComponent } from './view/dialog/request-make-dialog/request-make-dialog.component';
import { InvoiceDialogComponent } from './view/dialog/invoice-dialog/invoice-dialog.component';
import { EmptyComponent } from './view/shared/empty/empty.component';
import { AuditorRegisterDialogComponent } from './view/dialog/auditor-register-dialog/auditor-register-dialog.component';
import { ActorRegisterDialogComponent } from './view/dialog/actor-register-dialog/actor-register-dialog.component';
import { DashboardActorListComponent } from './view/admin/admin-dashboard/dashboard-actor-list/dashboard-actor-list.component';
import { DashboardPropertyListComponent } from './view/admin/admin-dashboard/dashboard-property-list/dashboard-property-list.component';
import { FooterComponent } from './view/shared/footer/footer.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PropertyComponent,
    ProfileComponent,
    ProfileDataComponent,
    ProfileDataEditComponent,
    ProfilePropertiesComponent,
    ProfileRequestsComponent,
    ProfileAuditsComponent,
    AdminComponent,
    AdminDashboardComponent,
    AdminPropertyAttributesComponent,
    AdminConfigComponent,
    AdminAuditorListComponent,
    TopBarComponent,
    PropertyListComponent,
    PropertyListItemComponent,
    ActorInfoComponent,
    AuditDialogComponent,
    AuditEditDialogComponent,
    IconButtonComponent,
    EmptyMessageComponent,
    MoneyPipe,
    CharLimitPipe,
    ImgLoadingComponent,
    PhonePipe,
    SocialInfoComponent,
    CoolCasePipe,
    SearchSelectorComponent,
    CacheBreakPipe,
    CreatePropertyDialogComponent,
    StarsDisplayComponent,
    StarsInputComponent,
    RequestAcceptDialogComponent,
    RequestMakeDialogComponent,
    InvoiceDialogComponent,
    EmptyComponent,
    AuditorRegisterDialogComponent,
    ActorRegisterDialogComponent,
    DashboardActorListComponent,
    DashboardPropertyListComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      defaultLanguage: 'en'
    }),
    NgbModule,
    NgxMatSelectSearchModule,
    MatButtonModule,
    MatIconModule,
    MatSlideToggleModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatTabsModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatDialogModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatCheckboxModule,
    MatRadioModule
  ],
  providers: [
    { provide: ErrorHandler, useClass: DefaultErrorHandler },
    {provide: MAT_DATE_LOCALE, useValue: "es-ES"},
    MatMomentDateModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
