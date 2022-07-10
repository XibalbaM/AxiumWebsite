package fr.xibalba.axiumwebsite.api.repositories;

import fr.xibalba.axiumwebsite.api.tables.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findByName(String name);
}
