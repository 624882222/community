package com.miao.community.community.model;

import lombok.Data;

@Data
public class Question
{
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;
}
