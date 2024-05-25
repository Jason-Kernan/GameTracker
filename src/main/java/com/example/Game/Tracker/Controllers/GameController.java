package com.example.Game.Tracker.Controllers;

import com.example.Game.Tracker.DAO.GameDAO;
import com.example.Game.Tracker.Models.Game;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Game gameById(Principal principal, @PathVariable int id) {
        String username = "Jason";
        if(principal != null) {
            username = principal.getName();
        }
        return gameDAO.getGameById(username, id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Game updateGame(@RequestBody Game game ,@PathVariable int id){
        return gameDAO.updateGame(game, id);
    }


    @RequestMapping (path = "/{id}", method = RequestMethod.DELETE)
    public void deleteGame(Principal principal, @PathVariable int id){
        String username = "Jason";
        if(principal != null){
            username = principal.getName();
        }
        gameDAO.deleteGame(username, id);
    }

    @RequestMapping (method = RequestMethod.POST)
    public Game addGame(@RequestBody Game game){
        gameDAO.addGame(game);
        return gameDAO.getGameById(game.getUsername(), game.getGame_id());
    }


    @RequestMapping(path = "/{gameid}/launcherlink/{launcherid}", method = RequestMethod.POST)
        public void linklauncher( Principal principal,@PathVariable int gameid, @PathVariable int launcherid){
        String username = "Jason";
        if (principal != null) {
         username = principal.getName();
        }
       gameDAO.linkGameLauncher(username, gameid, launcherid);
    }

    @RequestMapping(path = "/{gameid}/platformlink/{platformid}", method = RequestMethod.POST)
    public void linkplatform( Principal principal,@PathVariable int gameid, @PathVariable int platformid){
        String username = "Jason";
        if (principal != null) {
            username = principal.getName();
        }
        gameDAO.linkGamePlatform(username, gameid, platformid);
    }

    @RequestMapping(path = "/{gameid}/launcherunlink/{launcherid}", method = RequestMethod.DELETE)
    public void unlinklauncher( Principal principal,@PathVariable int gameid, @PathVariable int launcherid){
        String username = "Jason";
        if (principal != null) {
            username = principal.getName();
        }
        gameDAO.unlinkGameLauncher(username, gameid, launcherid);
    }

    @RequestMapping(path = "/{gameid}/platformunlink/{platformid}", method = RequestMethod.DELETE)
    public void unlinkplatform( Principal principal,@PathVariable int gameid, @PathVariable int platformid){
        String username = "Jason";
        if (principal != null) {
            username = principal.getName();
        }
        gameDAO.unlinkGamePlatform(username, gameid, platformid);
    }
}
