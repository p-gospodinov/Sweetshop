package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Turnover;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface TurnoverRepository extends CrudRepository<Turnover, Integer> {
    Turnover findTurnoverByDate(LocalDate now);
}
