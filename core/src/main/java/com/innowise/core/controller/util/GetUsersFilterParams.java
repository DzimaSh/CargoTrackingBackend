package com.innowise.core.controller.util;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@ToString
@Setter
public class GetUsersFilterParams {
    private String name;

    private String surname;

    private String patronymic;

    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"EEE MMM dd HH:mm:ss zzz yyyy"})
    private Date beforeBornDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"EEE MMM dd HH:mm:ss zzz yyyy"})
    private Date afterBornDate;

    private String town;

    private String street;

    private String house;

    private String flat;

    private String[] roles;

    private Integer pageSize = 5;

    private Integer pageNumber = 0;

}
