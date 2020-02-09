package com.miao.community.community.Service;

import com.miao.community.community.dto.PaginationDTO;
import com.miao.community.community.dto.QuestionDTO;
import com.miao.community.community.model.Question;

import java.util.List;

public interface QuestionService {
    public PaginationDTO selectQuestionList(Integer page, Integer size);

    PaginationDTO selectListById(Integer userId,Integer page, Integer size);

    QuestionDTO selectById(Integer id);

    void createOrUpdate(Question question);
}
