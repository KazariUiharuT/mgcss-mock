package com.acme.bnb.repositories;

import com.acme.bnb.model.Commentable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentableRepository extends CrudRepository<Commentable, Long>{
    
}
