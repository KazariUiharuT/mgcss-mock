import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './view/admin/admin/admin.component';
import { HomeComponent } from './view/home/home.component';
import { ProfileComponent } from './view/profile/profile/profile.component';
import { PropertyComponent } from './view/property/property.component';
import { EmptyComponent } from './view/shared/empty/empty.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, pathMatch: "full" },
  { path: 'property/:id', component: PropertyComponent, pathMatch: "full" },
  { path: 'profile/:id', component: ProfileComponent, pathMatch: "full" },
  { path: 'admin', component: AdminComponent, pathMatch: "full" },
  { path: 'empty', component: EmptyComponent, pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
