package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
