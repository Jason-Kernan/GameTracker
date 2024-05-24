package com.example.Game.Tracker.DAO;

import com.example.Game.Tracker.Models.Launcher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LauncherDAO {

    private JdbcTemplate jdbcTemplate;
    public LauncherDAO(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public List<Launcher> getAllLaunchers(){
        return jdbcTemplate.query("select * from launcher", this::mapRowToLauncher);
    }

    public Launcher getLauncherByID(int launcherId){
        return jdbcTemplate.queryForObject("select * from launcher where launcher_id = ?", this::mapRowToLauncher, launcherId );
    }

    public List <Launcher> getLaunchersForGame (String username, int gameId){
        return jdbcTemplate.query("select * from launcher " +
                "join game_launcher on game_launcher.launcher_id = launcher.launcher_id   " +
                "join game on game_launcher.game_id = game.game_id " +
                "where username = ? and game.game_id = ?", this::mapRowToLauncher, username, gameId);
    }

    public Launcher addLauncher (Launcher launcher){
        jdbcTemplate.update("insert into launcher (launcher_id, launcher_name) values (?,?)",
                launcher.getLauncher_id(), launcher.getLauncher_name());
        return getLauncherByID(launcher.getLauncher_id());
    }

    public Launcher updateLauncher (Launcher launcher, int launcherId){
        jdbcTemplate.update("update launcher set launcher_name = ? where launcher_id = ?",
                launcher.getLauncher_name(), launcher.getLauncher_id());
        return getLauncherByID(launcher.getLauncher_id());
    }

    public void deleteLauncher (int launcherId){
        jdbcTemplate.update("Delete from launcher where launcher_id = ?", launcherId);
    }



    private Launcher mapRowToLauncher(ResultSet row, int rownumber) throws SQLException{
        Launcher launcher = new Launcher();
        launcher.setLauncher_id(row.getInt("launcher_id"));
        launcher.setLauncher_name(row.getString("launcher_name"));
        return launcher;
    }


}
