package bg.codeacademy.cakeShop.model;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PersonalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String personalName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address")
    private Address address;

    @OneToMany(mappedBy = "beneficiary")
    private List<BankAccount> bankAccount;
}
