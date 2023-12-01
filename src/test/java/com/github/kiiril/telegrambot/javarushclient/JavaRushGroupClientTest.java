package com.github.kiiril.telegrambot.javarushclient;

import com.github.kiiril.telegrambot.javarushclient.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Integration-level testing for JavaRushGroupClientImpl")
public class JavaRushGroupClientTest {
    private final JavaRushGroupClient groupClient = new JavaRushGroupClientImpl("https://javarush.com/api/1.0/rest");

    @Test
    public void shouldProperlyGetGroupsWithEmptyArgs() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        // when
        List<GroupInfo> groupList = groupClient.getGroupList(args);

        // then
        Assertions.assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupsWithOffsetAndLimit() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        // when
        List<GroupInfo> groupList = groupClient.getGroupList(args);

        // then
        Assertions.assertNotNull(groupList);
        Assertions.assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupsDiscWithEmptyArgs() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        // when
        List<GroupDiscussionInfo> groupList = groupClient.getGroupDiscussionList(args);

        // then
        Assertions.assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupsDiscWithOffsetAndLimit() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        // when
        List<GroupDiscussionInfo> groupList = groupClient.getGroupDiscussionList(args);

        // then
        Assertions.assertNotNull(groupList);
        Assertions.assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupTECHCount() {
        // given
        GroupsCountRequestArgs args = GroupsCountRequestArgs.builder()
                .type(GroupInfoType.TECH)
                .build();

        // when
        Integer groupCount = groupClient.getGroupCount(args);

        // then
        Assertions.assertEquals(7, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupById() {
        // given
        Integer androidGroupId = 16;

        // when
        GroupDiscussionInfo groupById = groupClient.getGroupById(androidGroupId);

        // then
        Assertions.assertNotNull(groupById);
        Assertions.assertEquals(16, groupById.getId());
        Assertions.assertEquals(GroupInfoType.TECH, groupById.getType());
        Assertions.assertEquals("android", groupById.getKey());
    }

}
