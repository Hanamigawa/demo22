package com.example.demo22.entity.db;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "items")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {

    //https://www.baeldung.com/java-serial-version-uid
    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("url")
    private String url;

    @Column(name = "thumbnail_url")
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "game_id")
    @JsonProperty("game_id")
    private String gameId;

    @Column(name = "broadcaster_name")
    @JsonProperty("broadcaster_name")
    @JsonAlias({"user_name"})
    private String broadcasterName;

    @Enumerated(value = EnumType.STRING)
    @JsonProperty("item_type")
    private ItemType itemType;

}
