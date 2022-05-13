package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.CommentCreationData;
import com.acme.bnb.exceptions.ConflictException;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Comment;
import com.acme.bnb.model.Commentable;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.Tenant;
import com.acme.bnb.repositories.CommentRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class CommentService {
    
    private final CommentRepository commentRepo;
    private final AuthService authService;
    
    public List<Comment> findCommentsByLessor(Lessor lessor){
        return commentRepo.findCommentsByTargetIdOrderByDateDesc(lessor.getCommentable().getId());
    }
    
    public List<Comment> findCommentsByTenant(Tenant tenant){
        return commentRepo.findCommentsByTargetIdOrderByDateDesc(tenant.getCommentable().getId());
    }

    public Long create(Commentable target, CommentCreationData data) {
        if(!data.isValid()) throw new InvalidParametersException("Comment data is not valid");
        
        Long authId = this.authService.getAuth().getId();
        if(authService.getAuth() instanceof Lessor){
            if(target.getLessor() != null && !target.getLessor().getId().equals(authId)) throw new ConflictException("Cannot post a comment on another lessor");
            if(target.getTenant() != null && !target.getTenant().getRequests().stream().anyMatch(r -> r.getProperty().getPropietary().getId().equals(authId))) throw new ConflictException("Cannot post a comment on a tennant that has not requested any of your properties");
        }else if(authService.getAuth() instanceof Tenant){
            if(target.getTenant() != null && !target.getTenant().getId().equals(authId)) throw new ConflictException("Cannot post a comment on another tenant");
            if(target.getLessor() != null && !target.getLessor().getProperties().stream().anyMatch(p -> p.getRequests().stream().anyMatch(r -> r.getTenant().getId().equals(authId)))) throw new ConflictException("Cannot post a comment on a lessor that you have not requested any properties of");
        }
        
        Comment comment = new Comment();
        comment.setAuthor(authService.getAuth());
        comment.setTarget(target);
        comment.setStars(data.getStars());
        comment.setTitle(data.getTitle());
        comment.setText(data.getText());
        commentRepo.save(comment);
        return comment.getId();
    }
    
    public double getLessorStars(Lessor lessor){
        return commentRepo.getAvgStarsByTargetId(lessor.getCommentable());
    }
    
    public double getTenantStars(Tenant tenant){
        return commentRepo.getAvgStarsByTargetId(tenant.getCommentable());
    }

    double getAvgStarsByActor(Commentable commentable) {
        return commentRepo.getAvgStarsByTargetId(commentable);
    }
    
}
