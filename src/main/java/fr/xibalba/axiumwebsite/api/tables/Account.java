package fr.xibalba.axiumwebsite.api.tables;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.xibalba.axiumwebsite.api.controllers.AccountController;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    @ToString.Exclude
    String password;

    @Column(name = "token", nullable = false)
    String token;

    @Column(name = "token_expiration")
    Date tokenExpiration;

    @Column(name = "icon_url")
    String icon_url;

    @Column(name = "creation_date")
    Date creationDate;

    @Column(name = "xp")
    int xp;

    @Column(name = "fidelity")
    int fidelity;

    @Column(name = "premium")
    int premium;

    @Column(name = "email_verified")
    boolean emailVerified;

    @Column(name = "enabled", nullable = false)
    boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {
                        CascadeType.MERGE,
                        CascadeType.PERSIST,
                }
    )
    @JoinTable(name = "accounts_games",
               joinColumns = @JoinColumn(name = "account_id"),
               inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @ToString.Exclude
    List<Game> games;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {
                        CascadeType.MERGE,
                        CascadeType.PERSIST
                }
    )
    @JoinTable(name = "accounts_roles",
               joinColumns = @JoinColumn(name = "account_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    @ToString.Exclude
    List<Role> roles;

    public static Account create(String username, String email, String password, String icon_url) {
        Account account = new Account();
        account.username = username;
        account.email = email;
        account.password = password;
        account.icon_url = icon_url;
        account.token = AccountController.tokenSupplier.get();
        account.tokenExpiration = Date.valueOf(LocalDate.now().plus(30, ChronoUnit.DAYS));
        account.creationDate = Date.valueOf(LocalDate.now());
        account.xp = 0;
        account.fidelity = 0;
        account.premium = 0;
        account.emailVerified = false;
        account.enabled = true;

        return account;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }
}