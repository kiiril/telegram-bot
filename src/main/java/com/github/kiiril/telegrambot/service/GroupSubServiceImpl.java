package com.github.kiiril.telegrambot.service;

import com.github.kiiril.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.kiiril.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.kiiril.telegrambot.repository.GroupSubRepository;
import com.github.kiiril.telegrambot.repository.entity.GroupSub;
import com.github.kiiril.telegrambot.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupSubServiceImpl implements GroupSubService {

    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;
    private final JavaRushGroupClient javaRushGroupClient;

    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService telegramUserService, JavaRushGroupClient javaRushGroupClient) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
        this.javaRushGroupClient = javaRushGroupClient;
    }

    @Override
    public GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo) {
        TelegramUser user = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        // TODO add exception handling
        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDB = groupSubRepository.findById(groupDiscussionInfo.getId());
        if (groupSubFromDB.isPresent()) {
            groupSub = groupSubFromDB.get();
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(it -> it.getChatId().equals(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                groupSub.addUser(user);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(user);
            groupSub.setLastPostId(javaRushGroupClient.findLastPostId(groupDiscussionInfo.getId()));
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
        }
        return groupSubRepository.save(groupSub);
    }

    @Override
    public Optional<GroupSub> findById(Integer groupId) {
        return groupSubRepository.findById(groupId);
    }

    @Override
    public GroupSub save(GroupSub groupSub) {
        return groupSubRepository.save(groupSub);
    }

    @Override
    public List<GroupSub> findAll() {
        return groupSubRepository.findAll();
    }
}
