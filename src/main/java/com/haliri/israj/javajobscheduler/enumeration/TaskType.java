package com.haliri.israj.javajobscheduler.enumeration;

public enum TaskType {
    HELLO_WORLD,
    BIFF_BUZZ;

    public static TaskType taskTypeConvertor(String param) {
        TaskType taskType;

        try {
            taskType = TaskType.valueOf(param);
        } catch (IllegalArgumentException e) {
            taskType = null;
        }

        return taskType;
    }
}