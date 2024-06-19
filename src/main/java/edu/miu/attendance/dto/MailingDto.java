package edu.miu.attendance.dto;

import lombok.Data;

@Data
public class MailingDto {

    private String title;
    private String description;
    private String userId;
    private String userEmail;

    public MailingDto(String title, String description, String userId, String userEmail) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
