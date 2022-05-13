package com.acme.bnb.services;

import com.acme.bnb.model.Commentable;
import com.acme.bnb.repositories.CommentableRepository;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class CommentableService {

    private final CommentableRepository commentableRepo;

    public void save(Commentable commenatable) {
        commentableRepo.save(commenatable);
    }


}
