package bg.codeacademy.cakeShop.model;

import bg.codeacademy.cakeShop.enums.PaymentCriteria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ScheduleTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender")
    private BankAccount sender;

    @ManyToOne
    @JoinColumn(name = "recipient")
    private BankAccount recipient;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentCriteria paymentCriteria;

    @Column(nullable = false)
    private int amountPercentage;

    @Override
    public String toString() {
        return "ScheduleTransaction{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", paymentCriteria=" + paymentCriteria +
                ", amountPercentage=" + amountPercentage +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        ScheduleTransaction transaction = (ScheduleTransaction) obj;
        boolean equalSender = transaction.getSender().equals(this.getSender());
        boolean equalRecipient = transaction.getRecipient().equals(this.getRecipient());
        return transaction.getId() == this.getId() &&
                equalSender &&
                equalRecipient &&
                transaction.getPaymentCriteria().equals(this.getPaymentCriteria()) &&
                transaction.getAmountPercentage() == this.getAmountPercentage();
    }
}
