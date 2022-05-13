import { Component } from '@angular/core';
import { SysConfig } from 'src/app/model/sys-config';
import { SysConfigService } from 'src/app/service/sys-config.service';

@Component({
  selector: 'admin-config',
  templateUrl: './admin-config.component.html',
  styleUrls: ['./admin-config.component.css']
})
export class AdminConfigComponent {

  CONFIG_NAME: any = {"feeTenant": "Tarifa a tenant", "feeLessor": "Tarifa lessor", "bnbVar": "VAT de la empresa"}

  sysConfigs: SysConfig[] = [];
  lastSysConfigs: any = {};

  constructor(
    private readonly sysConfigService: SysConfigService
  ) {
    this.sysConfigService.list().then(sysConfigs => {
      this.sysConfigs = sysConfigs;
      this.sysConfigs.forEach(sysConfig => {
        this.lastSysConfigs[sysConfig.name] = sysConfig.value;
      })
    });
  }

  updateVariable(sysConfig: SysConfig) {
    this.sysConfigService.set(sysConfig.name, sysConfig.value).then(() => {
      this.lastSysConfigs[sysConfig.name] = sysConfig.value;
    });
  }

}
