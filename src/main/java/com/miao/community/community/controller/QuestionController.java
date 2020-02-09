package com.miao.community.community.controller;

import com.miao.community.community.Service.QuestionService;
import com.miao.community.community.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question( Model model,
            @PathVariable(name="id") Integer id){

        QuestionDTO questions = questionService.selectById(id);
        model.addAttribute("questions", questions);

        return "question";

    }
}
