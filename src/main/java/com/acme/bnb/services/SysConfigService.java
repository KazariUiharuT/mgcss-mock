package com.acme.bnb.services;

import com.acme.bnb.model.SysConfig;
import com.acme.bnb.repositories.SysConfigRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class SysConfigService {
    
    private final SysConfigRepository sysConfigRepo;
    
    public String getConfig(String name){
        return sysConfigRepo.findByName(name).orElseThrow(() -> new IllegalStateException("SysConfig parameter not found")).getValue();
    }

    public List<SysConfig> findAll() {
        return (List<SysConfig>) sysConfigRepo.findAll();
    }

    public void setConfig(String name, String value) {
        SysConfig config = sysConfigRepo.findByName(name).orElseThrow(() -> new IllegalStateException("SysConfig parameter not found"));
        config.setValue(value);
        sysConfigRepo.save(config);
    }
}
