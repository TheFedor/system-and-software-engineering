package com.misis.laba6.dto;

public class HelloWorldDto {
    private String messageFullName;
    private String messageBirthday;
    private int randomValue;

    public String getMessageFullName() {
        return messageFullName;
    }

    public String getMessageBirthday() {
        return messageBirthday;
    }

    public int getRandomValue() {
        return randomValue;
    }

    public HelloWorldDto setMessage(String fullName, String birthday, int randomValue) {
        this.messageFullName = fullName;
        this.messageBirthday = birthday;
        this.randomValue = randomValue;
        return this;
    }

}
