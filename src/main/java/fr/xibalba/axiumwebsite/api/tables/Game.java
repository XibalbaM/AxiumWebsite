package fr.xibalba.axiumwebsite.api.tables;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
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
}
