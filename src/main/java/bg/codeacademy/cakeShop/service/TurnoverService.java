package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.model.Turnover;
import bg.codeacademy.cakeShop.repository.TurnoverRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.time.LocalDate.now;

@Slf4j
@Service
public class TurnoverService {
    public final TurnoverRepository turnoverRepository;
    public final LegalEntityService legalEntityService;

    public TurnoverService(TurnoverRepository turnoverRepository, LegalEntityService LegalEntityService) {
        this.turnoverRepository = turnoverRepository;
        this.legalEntityService = LegalEntityService;
    }

    public Turnover additionAmount(int id, float amount) {
        Turnover turnover = turnoverRepository.findTurnoverByDate(now());
        if (turnover == null) {
            Turnover to = new Turnover();
            to.setAmount(amount);
            to.setDate(now());
            to.setOwner(legalEntityService.getLegalEntity(id));
            turnoverRepository.save(to);
            log.info("Service | Save turnover with ID:" + id + ", amount=" + amount);
            return to;
        }
        float newAmount = turnover.getAmount() + amount;
        turnover.setAmount(newAmount);
        log.info("Service | Update turnover with ID:" + id + ", amount=" + amount);
        turnoverRepository.save(turnover);
        return turnover;
    }
}
