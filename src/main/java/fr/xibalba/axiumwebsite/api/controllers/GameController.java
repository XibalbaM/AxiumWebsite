package fr.xibalba.axiumwebsite.api.controllers;

import fr.xibalba.axiumwebsite.api.RestResponse;
import fr.xibalba.axiumwebsite.api.repositories.GameRepository;
import fr.xibalba.axiumwebsite.api.tables.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static fr.xibalba.axiumwebsite.api.RestResponse.*;

@RequestMapping("/api/game")
@RestController
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("")
    public RestResponse<List<Game>> infos(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "id", required = false) Integer id) {

        if (name != null && gameRepository.findByName(name) != null) {

            return success(List.of(gameRepository.findByName(name)));
        } else if (id != null && gameRepository.findById(id).isPresent()) {

            return success(List.of(gameRepository.findById(id).get()));
        } else {

            return success(gameRepository.findAll());
        }
    }
}
