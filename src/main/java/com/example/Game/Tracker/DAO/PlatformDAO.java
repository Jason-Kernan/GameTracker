package com.example.Game.Tracker.DAO;

import com.example.Game.Tracker.Models.Platform;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class PlatformDAO {
    private JdbcTemplate jdbcTemplate;

    public PlatformDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Platform> getAllPlatforms() {
        return jdbcTemplate.query("select * from platform", this::mapRowToPlatform);
    }

    public Platform getPlatformByID(int platformId) {
        return jdbcTemplate.queryForObject("select * from platform where platform_id = ?", this::mapRowToPlatform, platformId);
    }

    public List<Platform> getPlatformForGame(String username, int gameId) {
        return jdbcTemplate.query("select * from platform " +
                "join platform.platform_id on game_platform.platform_id " +
                "join game on game_platform.game_id = game.game_id " +
                "where username = ? and game_id = ?", this::mapRowToPlatform, username, gameId);
    }

    public Platform addPlatform(Platform platform) {
        jdbcTemplate.update("insert into platform (launcher_id, launcher_name), values (?,?)",
                platform.getPlatform_id(), platform.getPlatform_name());
        return getPlatformByID(platform.getPlatform_id());
    }

    public Platform updateLauncher(Platform platform, int platformId) {
        jdbcTemplate.update("update platform set platform_name where platform_id = ?",
                platformId);
        return getPlatformByID(platform.getPlatform_id());
    }

    public void deleteLauncher(int launcherId) {
        jdbcTemplate.update("Delete from launcher where launcher_id = ?", launcherId);
    }


    private Platform mapRowToPlatform(ResultSet row, int rowNumber) throws SQLException {
        Platform platform = new Platform();
        platform.setPlatform_id(row.getInt("platform_id"));
        platform.setPlatform_name(row.getString("platform_name"));
        return platform;
    }
}
