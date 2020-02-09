package com.miao.community.community.Service.impl;

import com.miao.community.community.Service.UserService;
import com.miao.community.community.mapper.UserMapper;
import com.miao.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrInsert(User user) {

        User dBUser = userMapper.selectByAccountId(user.getAccountId());
        if (dBUser == null) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            dBUser.setName(user.getName());
            dBUser.setToken(user.getToken());
            dBUser.setGmtModified(System.currentTimeMillis());
            dBUser.setAvatarUrl(user.getAvatarUrl());
            userMapper.updateById(dBUser);
        }

    }
}
