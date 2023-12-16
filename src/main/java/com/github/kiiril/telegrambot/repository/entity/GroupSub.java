package com.github.kiiril.telegrambot.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Data
@Table(name = "group_sub")
@EqualsAndHashCode
public class GroupSub {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "last_post_id")
    private Integer lastPostId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_x_user",
            joinColumns = @JoinColumn(name = "group_sub_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TelegramUser> users;

    public void addUser(TelegramUser telegramUser) {
        if(isNull(users)) {
            users = new ArrayList<>();
        }
        users.add(telegramUser);
    }

}
