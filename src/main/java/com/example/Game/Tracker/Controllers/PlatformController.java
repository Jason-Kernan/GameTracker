package com.example.Game.Tracker.Controllers;

import com.example.Game.Tracker.DAO.LauncherDAO;
import com.example.Game.Tracker.DAO.PlatformDAO;
import com.example.Game.Tracker.Models.Launcher;
import com.example.Game.Tracker.Models.Platform;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {
    private final PlatformDAO platformDAO;

    public PlatformController (PlatformDAO platformDAO) {
        this.platformDAO = platformDAO;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Platform> getAllPlatforms (){
        return platformDAO.getAllPlatforms();
    }

    @RequestMapping(path ="/{id}", method = RequestMethod.GET)
    public Platform platformByGameId (@PathVariable int id){
        return platformDAO.getPlatformByID(id);
    }

    @RequestMapping(path = "/games/{id}", method = RequestMethod.GET)
    public List  <Platform> platformForGame (Principal principal, @PathVariable int id){
        String username = "Jason";
        if (principal != null){
            username = principal.getName();
        }
        return platformDAO.getPlatformForGame(username, id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Platform addPlatform(@RequestBody Platform platform){
        platformDAO.addPlatform(platform);
        return platformDAO.getPlatformByID(platform.getPlatform_id());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Platform updatePlatform(@RequestBody Platform platform, @PathVariable int id){
        return platformDAO.updatePlatform(platform, id);
    }

    @RequestMapping (path = "/{id}", method = RequestMethod.DELETE)
    public void deletePlatform(@PathVariable int id){
        platformDAO.deletePlatform(id);
    }
}
