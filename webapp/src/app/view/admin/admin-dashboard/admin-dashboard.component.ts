import { Component } from '@angular/core';
import { Lessor } from 'src/app/model/lessor';
import { DashboardData } from 'src/app/service/class/dashboard-data';
import { DashboardLessorData } from 'src/app/service/class/dashboard-lessor-data';
import { DashboardService } from 'src/app/service/dashboard.service';
import { LessorService } from 'src/app/service/lessor.service';

@Component({
  selector: 'admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {

  data: DashboardData = new DashboardData;
  dataLessor: DashboardLessorData | null = null;
  lessors: Lessor[] = [];
  lessorId: number | null = null;
  testQLquery: string = "SELECT a FROM Actor a";
  testQLresult: any = null;
  testQLerror: any = null;

  constructor(
    private readonly lessorService: LessorService,
    private readonly dashboardService: DashboardService
  ) {
    this.lessorService.list().then(lessors => this.lessors = lessors);
    this.dashboardService.get().then(data => this.data = data);
  }

  onLessorChange(){
    if(this.lessorId !== null)
      this.dashboardService.getLessor(this.lessorId).then(data => this.dataLessor = data);
  }

  testQL(){
    this.testQLresult = null;
    this.testQLerror = null;
    this.dashboardService.testql(this.testQLquery)
    .then(result => this.testQLresult = result)
    .catch(err => this.testQLerror = err);
  }

}
