package com.example.Game.Tracker.Controllers;
import com.example.Game.Tracker.DAO.LauncherDAO;
import com.example.Game.Tracker.Models.Launcher;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping ("/launchers")
public class LauncherController {

    private final LauncherDAO launcherDAO;

    public LauncherController (LauncherDAO launcherDAO) {
        this.launcherDAO = launcherDAO;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Launcher> getAllLaunchers (){
        return launcherDAO.getAllLaunchers();
    }

    @RequestMapping(path ="/{id}", method = RequestMethod.GET)
    public Launcher launcherByGameId (@PathVariable int id){
        return launcherDAO.getLauncherByID(id);
    }

    @RequestMapping(path = "/games/{id}", method = RequestMethod.GET)
    public List  <Launcher> launcherForGame (Principal principal, @PathVariable int id){
        String username = "Jason";
        if (principal != null){
            username = principal.getName();
        }
        return launcherDAO.getLaunchersForGame(username, id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Launcher addLauncher(@RequestBody Launcher launcher){
        launcherDAO.addLauncher(launcher);
        return launcherDAO.getLauncherByID(launcher.getLauncher_id());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Launcher updateLauncher(@RequestBody Launcher launcher, @PathVariable int id){
        return launcherDAO.updateLauncher(launcher, id);
    }

    @RequestMapping (path = "/{id}", method = RequestMethod.DELETE)
    public void deleteLauncher(@PathVariable int id){
        launcherDAO.deleteLauncher(id);
    }



}
