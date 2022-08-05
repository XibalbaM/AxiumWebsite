package fr.xibalba.axiumwebsite.api.tables;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "icon_url", nullable = false)
    String icon_url;

    @Column(name = "download_url", nullable = false)
    String download_url;

    @Column(name = "version")
    String version;

    @Column(name = "last_update", nullable = false)
    Date last_update;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Game game = (Game) o;
        return id != null && Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }
}
