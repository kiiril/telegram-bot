package com.github.kiiril.telegrambot.command;

public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    STAT("/stat"),
    ADD_GROUP_SUB("/addgroupsub"),
    LIST_GROUP_SUB("/listgroupsub"),
    NO("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
