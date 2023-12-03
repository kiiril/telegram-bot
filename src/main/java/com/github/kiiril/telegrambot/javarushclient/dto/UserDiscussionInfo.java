package com.github.kiiril.telegrambot.javarushclient.dto;

import lombok.Data;

@Data
public class UserDiscussionInfo {
    private boolean isBookmarked;
    private Integer lastTime;
    private Integer newCommentsCount;
}
