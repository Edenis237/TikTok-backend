package com.douyin.mapper;

import com.douyin.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(username, password) VALUES(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(Long id);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Long id);

    @Update("UPDATE users SET username = #{username}, password = #{password} WHERE id = #{id}")
    int updateUser(User user);
}
