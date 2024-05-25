package com.example.Game.Tracker.DAO;

import com.example.Game.Tracker.Models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class GameDAO {

    private JdbcTemplate jdbcTemplate;
    public GameDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Game> getAllGames(String username){
        return jdbcTemplate.query("SELECT * from game where username = ?", this::mapRowToGame, username);
    }

    public Game getGameById(String username, int id) {
        return jdbcTemplate.queryForObject("select * from game where username = ? and game_id = ?", this::mapRowToGame, username, id);
    }
    public List <Game> searchGames (String username, String search) {
        return jdbcTemplate.query("select * from game where username = ? and game_name ilike ?", this ::mapRowToGame, username, "%" + search +"%");
    }

    public Game addGame(Game game) {
         jdbcTemplate.update("insert into game (game_name, studio, hours_played, username) values (?,?,?,?)",
                game.getGame_name(), game.getStudio_name(), game.getHours_played(), game.getUsername());
        return getGameById(game.getUsername(),game.getGame_id());
    }

    public Game updateGame(Game game, int id) {
        jdbcTemplate.update("update game set game_name = ?, studio = ?, hours_played = ? where username = ? and game_id = ?",
                game.getGame_name(), game.getStudio_name(), game.getHours_played(), game.getUsername(),id);
        return getGameById(game.getUsername(), game.getGame_id());
    }

    public void deleteGame(String username, int id){
        jdbcTemplate.update("Delete from game where game_id = ? and username =?", id,username);
    }
    public void linkGameLauncher(String username, int gameId, int launcherId) {
        if (getGameById(username, gameId) == null){
            return;
        } else {
            jdbcTemplate.update("insert into game_launcher (game_id, launcher_id) values (?,?)", gameId, launcherId);
        }
    }
    public void linkGamePlatform(String username, int gameId, int platformId) {
        if (getGameById(username, gameId) == null){
            return;
        } else {
            jdbcTemplate.update("insert into game_platform (game_id, platform_id) values (?,?)", gameId, platformId);
        }
    }

    public void unlinkGameLauncher(String username, int gameId, int launcherId) {
        if (getGameById(username, gameId) == null){
            return;
        } else {
            jdbcTemplate.update("delete from game_launcher where game_id = ? and launcher_id = ?", gameId, launcherId);
        }
    }
    public void unlinkGamePlatform(String username, int gameId, int platformId) {
        if (getGameById(username, gameId) == null){
            return;
        } else {
            jdbcTemplate.update("delete from game_platform where game_id = ? and platform_id = ?", gameId, platformId);
        }
    }










    private Game mapRowToGame(ResultSet row, int rowNumber) throws SQLException{
        Game game = new Game();
        game.setGame_id(row.getInt("game_id"));
        game.setGame_name(row.getString("game_name"));
        game.setStudio_name(row.getString("studio"));
        game.setHours_played(row.getInt("hours_played"));
        game.setUsername(row.getString("username"));
        return game;
    }
}
