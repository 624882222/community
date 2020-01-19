package com.miao.community.community.controller;

import com.miao.community.community.Service.QuestionService;
import com.miao.community.community.dto.QuestionDTO;
import com.miao.community.community.mapper.QuestionMapper;
import com.miao.community.community.mapper.UserMapper;
import com.miao.community.community.model.Question;
import com.miao.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;



    /**
     * 主页面
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){


        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length != 0){
            for (Cookie cookie: cookies) {
                if ("token".equals(cookie.getName())) {

                    String value = cookie.getValue();

                    User user = userMapper.findByToken(value);

                    if (user != null) {
                        request.getSession().setAttribute("gitHubUser", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList =  questionService.selectQuestionList();

        model.addAttribute("questions", questionList);




        return "index";
    }
}
