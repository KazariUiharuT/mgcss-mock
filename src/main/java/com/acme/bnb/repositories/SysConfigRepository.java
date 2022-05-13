package com.acme.bnb.repositories;

import com.acme.bnb.model.SysConfig;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysConfigRepository extends CrudRepository<SysConfig, Long>{

    public Optional<SysConfig> findByName(String name);
    
}
