package com.example.demo22.service;

import com.example.demo22.entity.response.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@Service
public class GameService {
    private static final String TOKEN = "Bearer 2il94pl5lkvgc6mrivocova5ggief0";
    private static final String CLIENT_ID = "zl20uhxpt5hvmywu4znnr5s9uasmz3";
    private static final String TOP_GAME_URL = "https://api.twitch.tv/helix/games/top?first=%s";
    private static final String GAME_SEARCH_URL_TEMPLATE = "https://api.twitch.tv/helix/games?name=%s";
    private static final int DEFAULT_GAME_LIMIT = 20;

    private String buildGameUrl(String gameName) {
        if (gameName.equals("")) {
            return String.format(TOP_GAME_URL, DEFAULT_GAME_LIMIT);
        }
        try {
            gameName = URLEncoder.encode(gameName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format(GAME_SEARCH_URL_TEMPLATE, gameName);
    }

    private String getTopGamesUrl(int limit) {
        return String.format(TOP_GAME_URL, limit);
    }


    private String searchTwitch(String url) throws TwitchException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        ResponseHandler<String> responseHandler = response -> {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 200) {
                System.out.println("Response status: " + response.getStatusLine().getReasonPhrase());
                throw new TwitchException("Failed to get result from Twitch API");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new TwitchException("Failed to get result from Twitch API");
            }
            JSONObject obj = new JSONObject(EntityUtils.toString(entity));
            return obj.getJSONArray("data").toString();
        };

        try {
            // Define the HTTP request, TOKEN and CLIENT_ID are used for user authentication on Twitch backend
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", TOKEN);
            request.setHeader("Client-Id", CLIENT_ID);
            return httpClient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TwitchException("Failed to get result from Twitch API");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Convert JSON format data returned from Twitch to an Arraylist of Game objects
    private List<Game> getGameList(String data) throws TwitchException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(data, Game[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new TwitchException("Failed to parse game data from Twitch API");
        }
    }

    public List<Game> topGames(int limit) throws TwitchException {
        if (limit <= 0) {
            limit = DEFAULT_GAME_LIMIT;
        }
        return getGameList(searchTwitch(getTopGamesUrl(limit)));
    }

    public Game searchGame(String gameName) throws TwitchException {
        List<Game> gameList = getGameList(searchTwitch(buildGameUrl(gameName)));
        if (gameList.size() != 0) {
            return gameList.get(0);
        }
        return null;
    }
}
