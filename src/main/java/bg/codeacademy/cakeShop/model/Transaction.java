package bg.codeacademy.cakeShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"city", "street"})})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "senderBankAccount")
    private BankAccount senderBankAccount;

    @ManyToOne
    @JoinColumn(name = "recipientBankAccount")
    private BankAccount recipientBankAccount;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private float amount;
}
