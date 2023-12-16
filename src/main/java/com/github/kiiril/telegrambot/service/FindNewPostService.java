package com.github.kiiril.telegrambot.service;

public interface FindNewPostService {
    /**
     * Find new articles and notify subscribers about it.
     */
    void findNewPosts();
}
