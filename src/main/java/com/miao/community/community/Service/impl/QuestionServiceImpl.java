package com.miao.community.community.Service.impl;

import com.miao.community.community.Service.QuestionService;
import com.miao.community.community.dto.PaginationDTO;
import com.miao.community.community.dto.QuestionDTO;
import com.miao.community.community.mapper.QuestionMapper;
import com.miao.community.community.mapper.UserMapper;
import com.miao.community.community.model.Question;
import com.miao.community.community.model.QuestionExample;
import com.miao.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
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

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

        // 计算一共多少页
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = (page - 1) * size;

        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));


        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (questionList != null && questionList.size() != 0) {
            for (Question question : questionList) {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
                questionDTOList.add(questionDTO);
            }
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        paginationDTO.setPagination(page, totalPage);

        return paginationDTO;
    }

    @Override
    public PaginationDTO selectListById(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example);
        // 计算一共多少页
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = (page - 1) * size;

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (questionList != null && questionList.size() != 0) {
            for (Question question : questionList) {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
                questionDTOList.add(questionDTO);
            }
        }

        paginationDTO.setQuestionDTOList(questionDTOList);
        paginationDTO.setPagination(page, totalPage);
        return paginationDTO;
    }

    @Override
    public QuestionDTO selectById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();

        Question questions = questionMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(questions, questionDTO);
        User user = userMapper.selectByPrimaryKey(questions.getCreator());

        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {

        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());

            questionMapper.insertSelective(question);
        } else {

            Question dBQuestion = new Question();
            dBQuestion.setTitle(question.getTitle());
            dBQuestion.setDescription(question.getDescription());
            dBQuestion.setGmtModified(System.currentTimeMillis());
            dBQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(dBQuestion, example);
        }

    }
}
