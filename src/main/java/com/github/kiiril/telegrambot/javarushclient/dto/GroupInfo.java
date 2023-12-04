package com.github.kiiril.telegrambot.javarushclient.dto;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.codec.language.bm.Lang;

@Data
@ToString
public class GroupInfo {
    private Integer id;
    private String avatarUrl;
    private String createTime;
    private String description;
    private String key;
    private Language language;
    private Integer levelToEditor;
    private MeGroupInfo meGroupInfo;
    private String pictureUrl;
    private String title;
    private GroupInfoType type;
    private Integer userCount;
    private GroupVisibilityStatus visibilityStatus;

}
