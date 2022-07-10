package fr.xibalba.axiumwebsite.api.controllers;

import fr.xibalba.axiumwebsite.api.repositories.GameRepository;
import fr.xibalba.axiumwebsite.api.tables.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/game")
@RestController
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("")
    public List<Game> infos(@RequestParam(value = "name", required = false) String username, @RequestParam(value = "id", required = false) Integer id) {

        if (username != null && gameRepository.findByName(username) != null) {

            return List.of(gameRepository.findByName(username));
        } else if (id != null && gameRepository.findById(id).isPresent()) {

            return List.of(gameRepository.findById(id).get());
        } else {

            return gameRepository.findAll();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException e) {

        e.printStackTrace();
    }
}
