package fr.xibalba.axiumwebsite.api.tables;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
}