package com.example.demo22.entity.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item implements Serializable {

    @Id
    private String id;

    private String title;

    private String url;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "broadcaster_name")
    private String broadcasterName;

    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;

}
