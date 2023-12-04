package com.github.kiiril.telegrambot.javarushclient;

import com.github.kiiril.telegrambot.javarushclient.dto.PostInfo;

import java.util.List;

public interface JavaRushPostClient {
    /**
     * Find new posts since lastPostId in provided group.
     *
     * @param groupId provided group ID.
     * @param lastPostId provided last post ID.
     * @return the collection of the new {@link PostInfo}.
     */
    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);
}
