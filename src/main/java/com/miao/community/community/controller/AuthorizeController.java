package com.miao.community.community.controller;

import com.miao.community.community.dto.AccessTokenDTO;
import com.miao.community.community.dto.GitHubUser;
import com.miao.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_Id);
        accessTokenDTO.setClient_secret(client_Secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
