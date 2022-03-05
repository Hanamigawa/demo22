package com.example.demo22.controller;

import com.example.demo22.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/game")
    public void getGame(@RequestParam(value = "game_name", required = false) String gameName, HttpServletResponse response) throws IOException {
        response.getWriter().println("gameName received" + gameName);
    }
}
