package fr.xibalba.axiumwebsite.api.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "priority", nullable = false)
    int priority;

    @Column(name = "prefix")
    String prefix;

    @Column(name = "multiple_prefix", nullable = false)
    boolean multiple_prefix;

    @Column(name = "prefix_color", nullable = false)
    String prefix_color;

    @Column(name = "suffix")
    String suffix;

    @Column(name = "multiple_suffix", nullable = false)
    boolean multiple_suffix;

    @Column(name = "suffix_color", nullable = false)
    String suffix_color;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    List<Account> accounts;
}