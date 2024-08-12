package bg.codeacademy.cakeShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class LegalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personalData")
    private PersonalData personalData;

    @Column(nullable = false, unique = true)
    private String uin;

    @OneToMany(mappedBy = "offeror")
    private List<Offer> offerors;

    @OneToMany(mappedBy = "offered")
    private List<Offer> offered;

    @OneToMany(mappedBy = "offeror")
    private List<Contract> contactsFromMe;

    @OneToMany(mappedBy = "recipient")
    private List<Contract> contractsToMe;

    @OneToMany(mappedBy = "assessed")
    private List<Comment> comments;

    @OneToMany(mappedBy = "owner")
    private List<Turnover> dailyTurnover;

    @OneToMany(mappedBy = "owner")
    private List<Storage> storageRows;
}
