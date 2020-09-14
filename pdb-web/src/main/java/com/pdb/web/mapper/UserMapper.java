package com.pdb.web.mapper;

import com.pdb.web.config.mybatis.DefaultMapper;
import com.pdb.web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends DefaultMapper {

  int deleteByPrimaryKey(Long userId);

  int insert(User record);

  int insertSelective(User record);

  User selectByPrimaryKey(Long userId);

  int updateByPrimaryKeySelective(User record);

  int updateByPrimaryKey(User record);

  int updateUserPwd(@Param("userId") long userId, @Param("newPwd") String newPwd);
}
