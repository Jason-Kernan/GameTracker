package com.example.Game.Tracker.Controllers;

import com.example.Game.Tracker.DAO.GameDAO;
import com.example.Game.Tracker.Models.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/games")
public class GameController {

    private final GameDAO gameDAO;

    public GameController(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }
    @RequestMapping(method = RequestMethod.GET)
    public List <Game> gameList (
            @RequestParam(required = false, defaultValue = "") String title_like,
            Principal principal
    ){
        String username = "Jason";
        if (principal != null){
            username = principal.getName();
        }
        if (title_like.equals("")){
            return gameDAO.getAllGames(username);
        } else {
            return gameDAO.searchGames(username, title_like);
        }
    }

    @RequestMapping(method = )
}
