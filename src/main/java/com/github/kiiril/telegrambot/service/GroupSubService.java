package com.github.kiiril.telegrambot.service;

import com.github.kiiril.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.kiiril.telegrambot.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {
    GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo);
    GroupSub save(GroupSub groupSub);
    Optional<GroupSub> findById(Integer groupId);
    List<GroupSub> findAll();
}
