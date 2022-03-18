package com.example.demo22.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Game.GameBuilder.class)
public class Game {
    @JsonProperty("name")
    public String name;

    @JsonProperty("developer")
    public String developer;

    @JsonProperty("release_time")
    public String releaseTime;

    @JsonProperty("website")
    public String website;

    @JsonProperty("price")
    public double price;

    @JsonProperty("box_art_url")
    public String boxArtUrl;

    @JsonProperty("id")
    public String id;
}
