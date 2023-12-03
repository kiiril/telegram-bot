package com.github.kiiril.telegrambot.service;

import com.github.kiiril.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.kiiril.telegrambot.repository.entity.GroupSub;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
