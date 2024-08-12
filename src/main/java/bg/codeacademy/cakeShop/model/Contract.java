package bg.codeacademy.cakeShop.model;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String identifier;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "offeror")
    private LegalEntity offeror;

    @ManyToOne
    @JoinColumn(name = "recipient")
    private LegalEntity recipient;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", offeror=" + offeror +
                ", recipient=" + recipient +
                ", status=" + status +
                '}';
    }
}
