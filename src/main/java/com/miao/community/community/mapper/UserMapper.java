package com.miao.community.community.mapper;

import com.miao.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id, name, token, gmt_create, gmt_modified,avatar_url) values(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from User where token = #{token}")
    User findByToken(@Param("token") String value);

    @Select("select * from user where id = #{id}")
    User findById(@Param(("id")) int id);
}
