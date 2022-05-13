package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.StringWrapper;
import com.acme.bnb.model.SysConfig;
import com.acme.bnb.services.SysConfigService;
import java.util.List;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/sys-config")
public class SysConfigController {
    
    private final SysConfigService sysConfigService;
    
    @GetMapping("")
    @Secured("admin")
    public List<SysConfig> list() {
        return sysConfigService.findAll();
    }
    
    @GetMapping(path = "/{configName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public StringWrapper get(@PathVariable String configName) {
        return new StringWrapper(sysConfigService.getConfig(configName));
    }
    
    @PatchMapping("/{configName}")
    @Secured("admin")
    public void set(@PathVariable String configName, @RequestBody StringWrapper value) {
        sysConfigService.setConfig(configName, value.getValue());
    }
}
