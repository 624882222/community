package com.miao.community.community.Service;

import com.miao.community.community.dto.PaginationDTO;

import java.util.List;

public interface QuestionService {
    public PaginationDTO selectQuestionList(Integer page, Integer size);
}
