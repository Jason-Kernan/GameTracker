package com.example.Game.Tracker.Models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private int game_id;
    private String game_name;
    private String studio_name;
    private int hours_played;
    private String username;
}
