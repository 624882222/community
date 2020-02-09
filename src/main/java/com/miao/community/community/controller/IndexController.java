package com.miao.community.community.controller;

import com.miao.community.community.Service.QuestionService;
import com.miao.community.community.dto.PaginationDTO;
import com.miao.community.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    /**
     * 主页面
     *
     * @return
     */
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "3") Integer size) {

        PaginationDTO questionList = questionService.selectQuestionList(page, size);

        model.addAttribute("questions", questionList);
        return "index";
    }
}
