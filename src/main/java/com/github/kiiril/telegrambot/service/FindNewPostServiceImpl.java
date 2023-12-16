package com.github.kiiril.telegrambot.service;

import com.github.kiiril.telegrambot.javarushclient.JavaRushPostClient;
import com.github.kiiril.telegrambot.javarushclient.dto.PostInfo;
import com.github.kiiril.telegrambot.repository.entity.GroupSub;
import com.github.kiiril.telegrambot.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewPostServiceImpl implements FindNewPostService {

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.com/groups/posts/%s";
    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public FindNewPostServiceImpl(GroupSubService groupSubService, JavaRushPostClient javaRushPostClient, SendBotMessageService sendBotMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void findNewPosts() {
        groupSubService.findAll().forEach(gSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(gSub.getId(), gSub.getLastPostId());

            setNewLastPostId(gSub, newPosts);

            notifySubscribersAboutNewPosts(gSub, newPosts);
        });
    }

    private void notifySubscribersAboutNewPosts(GroupSub groupSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewPosts = newPosts.stream()
                .map(post -> String.format("✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨\n\n" +
                        "<b>Описание:</b> %s\n\n" +
                        "<b>Ссылка:</b> %s\n", post.getTitle(), groupSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());
        groupSub.getUsers().stream().filter(TelegramUser::isActive).
                forEach(user -> sendBotMessageService.sendMessage(user.getChatId(), messagesWithNewPosts));
    }

    private void setNewLastPostId(GroupSub groupSub, List<PostInfo> newPosts) {
        // bigger id -> newer post
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    groupSub.setLastPostId(id);
                    groupSubService.save(groupSub);
                });
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }
}
