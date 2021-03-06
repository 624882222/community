package com.miao.community.community.controller;

import com.miao.community.community.Service.QuestionService;
import com.miao.community.community.dto.QuestionDTO;
import com.miao.community.community.mapper.QuestionMapper;
import com.miao.community.community.mapper.UserMapper;
import com.miao.community.community.model.Question;
import com.miao.community.community.model.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @PostMapping("/publish")
    public String createPublish(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("tag") String tag,
                                @RequestParam("id") Integer id,
                                HttpServletRequest request,
                                Model model){
        // 页面值的保存
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag",tag);
        // 必须入力check
        if (title == null || title.isEmpty()) {
            model.addAttribute("error", "标题不能为空！");
            return "publish";
        }
        if (description == null || description.isEmpty()) {
            model.addAttribute("error", "问题不能为空！");
            return "publish";
        }
        if (tag == null || tag.isEmpty()) {
            model.addAttribute("error", "标签不能为空！");
            return "publish";
        }
        User gitHubUser = (User)request.getSession().getAttribute("gitHubUser");
        // 添加数据
        if (gitHubUser != null) {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(gitHubUser.getId());
            question.setId(id);
            questionService.createOrUpdate(question);
            return "redirect:/";
        } else {
            model.addAttribute("error","请登录！");
            return "publish";
        }
    }

    @GetMapping("/publish/{id}")
    public String modifyQuestion(Model model,
                                 @PathVariable(name="id") Integer id){

        QuestionDTO question = questionService.selectById(id);

        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);

        return "publish";
    }
}
