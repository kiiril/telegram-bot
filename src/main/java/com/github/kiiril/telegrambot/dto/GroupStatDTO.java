package com.github.kiiril.telegrambot.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"title", "activeUserCount"})
public class GroupStatDTO {
    private final Integer id;
    private final String title;
    private final Integer activeUserCount;
}
