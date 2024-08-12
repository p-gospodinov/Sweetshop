package bg.codeacademy.cakeShop.model;

import bg.codeacademy.cakeShop.enums.BankAccountType;
import bg.codeacademy.cakeShop.enums.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"iban", "bankAccountType"})})
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false, unique = true)
    private String iban;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "beneficiary")
    private PersonalData beneficiary;

    private BankAccountType bankAccountType;

    @OneToMany(mappedBy = "sender")
    private List<ScheduleTransaction> sender;

    @OneToMany(mappedBy = "recipient")
    private List<ScheduleTransaction> recipient;

    @OneToMany(mappedBy = "senderBankAccount")
    private List<Transaction> senderList;

    @OneToMany(mappedBy = "recipientBankAccount")
    private List<Transaction> recipientList;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", beneficiary=" + beneficiary +
                ", bankAccountType=" + bankAccountType +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", senderList=" + senderList +
                ", recipientList=" + recipientList +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        BankAccount account = (BankAccount) obj;
        return this.getIban().equals(account.getIban());
    }
}
