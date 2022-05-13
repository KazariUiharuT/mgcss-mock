package com.acme.bnb.repositories;

import com.acme.bnb.model.Comment;
import com.acme.bnb.model.Commentable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{

    public List<Comment> findCommentsByTargetIdOrderByDateDesc(Long targetId);
    
    @Query("SELECT COALESCE(AVG(c.stars), 0) FROM Comment c WHERE c.target = :target")
    public double getAvgStarsByTargetId(Commentable target);
    
}
