package com.roadm.manager.service.impl;


import com.roadm.manager.mapper.UserMapper;
import com.roadm.manager.model.UserInfo;
import com.roadm.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;


	@Override
	public UserInfo getUserById(Integer userId) {
		return userMapper.getUserById(userId);
	}

	@Override
	public boolean insertUser(UserInfo user) {
		int count = userMapper.getCountByName(user.getUserName());
		if (count > 0) {
			return false;
		} else {
			userMapper.insertUser(user);
			return true;
		}

	}

	public List<UserInfo> listPageUser(UserInfo user) {
		return userMapper.listPageUser(user);
	}

	public void updateUser(UserInfo user) {
		userMapper.updateUser(user);
	}

	public void updateUserBaseInfo(UserInfo user) {
		userMapper.updateUserBaseInfo(user);
	}

	public void updateUserRights(UserInfo user) {
		userMapper.updateUserRights(user);
	}

	public UserInfo getUserByName(String loginname) {
		UserInfo user = new UserInfo();
		user.setUserName(loginname);
		return userMapper.getUserInfo(user);
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void deleteUser(int userId) {
		userMapper.deleteUser(userId);
	}

	public UserInfo getUserAndRoleById(Integer userId) {
		return userMapper.getUserAndRoleById(userId);
	}

	public void updateLastLogin(UserInfo user) {
		userMapper.updateLastLogin(user);
	}

	public List<UserInfo> listAllUser() {
		return userMapper.listAllUser();
	}

	@Override
	public void updateUserPwd(UserInfo user) {
		userMapper.updateUserPwd(user);
	}

	@Override
	public void insertOperateLog(Map map) {
		userMapper.insertOperateLog(map);
	}

}
