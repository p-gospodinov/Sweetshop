package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.model.Comment;
import bg.codeacademy.cakeShop.model.LegalEntity;
import bg.codeacademy.cakeShop.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final LegalEntityService legalEntityService;

    public CommentService(CommentRepository commentRepository,
                          LegalEntityService legalEntityService) {
        this.commentRepository = commentRepository;
        this.legalEntityService = legalEntityService;
    }

    public Comment createComment(String uin, String text) {
        Comment comment = new Comment();
        LegalEntity assessed = legalEntityService.getLegalEntity(uin);
        comment.setAssessed(assessed);
        comment.setComment(text);
        comment.setDate(now());
        commentRepository.save(comment);
        log.info("Service | Create comment for legal-entity with UIN=" + uin);
        return comment;
    }
}
