package com.miao.community.community.mapper;

import com.miao.community.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id, name, token, gmt_create, gmt_modified,avatar_url) values(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from User where token = #{token}")
    User findByToken(@Param("token") String value);

    @Select("select * from user where id = #{id}")
    User findById(@Param(("id")) Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User selectByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl}")
    void updateById(User user);
}
