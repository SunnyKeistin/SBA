package com.ibm.sba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ibm.sba.entity.User;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE EMAIL = #{email}")
	User findByName(@Param("email") String email);
    
    @Insert("INSERT INTO USER(EMAIL,FIRST_NAME,LAST_NAME, MOBILE, PASSWORD, ROLE, ACTIVE, AVATAR_PATH, BALANCE) "
    		+ "VALUES(#{email}, #{firstName}, #{lastName}, #{mobile}, #{password}, #{role}, #{active}, #{avatarPath}, #{balance})")
    void save(User user);
    
//    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("SELECT * FROM USER WHERE ID IN (#{userIds})")
    List<User> getUsersByIds(@Param("userIds") List<Long> userIds);
}
