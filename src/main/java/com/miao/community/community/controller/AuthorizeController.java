package com.miao.community.community.controller;

import com.miao.community.community.dto.AccessTokenDTO;
import com.miao.community.community.dto.GitHubUser;
import com.miao.community.community.mapper.UserMapper;
import com.miao.community.community.model.User;
import com.miao.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${git.Client.id}")
    private String client_Id;
    @Value("${git.Client.secret}")
    private String client_Secret;
    @Value("${redirect.uri}")
    private String redirect_uri;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_Id);
        accessTokenDTO.setClient_secret(client_Secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        System.out.println(gitHubUser.getName());
        if (gitHubUser != null) {

            User user = new User();
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("gitHubUser", gitHubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
