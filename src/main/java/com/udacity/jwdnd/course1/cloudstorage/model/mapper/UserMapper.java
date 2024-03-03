package com.udacity.jwdnd.course1.cloudstorage.model.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Update("UPDATE USERS SET salt = #{salt}, password = #{password}, firstname =  #{firstName}, lastname = #{lastName} WHERE username = #{user.userName}")
    int update(User user);

    @Delete("DELETE FROM USERS WHERE username = #{userName}")
    int delete(String userName);

}