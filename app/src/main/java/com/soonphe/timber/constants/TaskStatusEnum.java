package com.soonphe.timber.constants;

/**
 * 任务状态枚举
 *
 * @author soonphe
 * @since 1.0
 */
public enum TaskStatusEnum {

    NO_CHECKED(0),
    CHECKING(1),
    CHECKED_DONE(2);

    final int value;

    TaskStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}







