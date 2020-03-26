package ru.geekbrains.supershop.persistence.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPojo {
    private String id;
    private String captchaCode;
    private String commentary;
    private UUID productId;
    private boolean visible;
}