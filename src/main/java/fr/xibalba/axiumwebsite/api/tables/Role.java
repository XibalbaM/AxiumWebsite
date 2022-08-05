package fr.xibalba.axiumwebsite.api.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }
}