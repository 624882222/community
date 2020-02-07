package com.miao.community.community.Service.impl;

import com.miao.community.community.Service.QuestionService;
import com.miao.community.community.dto.PaginationDTO;
import com.miao.community.community.dto.QuestionDTO;
import com.miao.community.community.mapper.QuestionMapper;
import com.miao.community.community.mapper.UserMapper;
import com.miao.community.community.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public PaginationDTO selectQuestionList(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        Integer totalCount = questionMapper.getCount();

        // 计算一共多少页
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }
        Integer offset = (page - 1) * size;

        List<Question> questionList = questionMapper.getQuestionList(offset, size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (questionList != null && questionList.size() != 0) {
            for (Question question: questionList) {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(userMapper.findById(question.getCreator()));
                questionDTOList.add(questionDTO);
            }
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        paginationDTO.setPagination(page, totalPage);

        return paginationDTO;
    }

    @Override
    public PaginationDTO selectListById(Integer userId,Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.getCountById(userId);
        // 计算一共多少页
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }
        Integer offset = (page - 1) * size;

        List<Question> questionList = questionMapper.getQuestionListById(userId, offset, size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (questionList != null && questionList.size() != 0) {
            for (Question question: questionList) {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(userMapper.findById(question.getCreator()));
                questionDTOList.add(questionDTO);
            }
        }

        paginationDTO.setQuestionDTOList(questionDTOList);
        paginationDTO.setPagination(page, totalPage);
        return paginationDTO;
    }
}
