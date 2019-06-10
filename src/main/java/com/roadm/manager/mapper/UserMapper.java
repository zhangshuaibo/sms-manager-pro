package com.roadm.manager.mapper;


import com.roadm.manager.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserMapper {
	
	List<UserInfo> listAllUser();

	UserInfo getUserById(Integer userId);

	void insertUser(UserInfo user);

	void insertOperateLog(Map map);

	void updateUser(UserInfo user);

	UserInfo getUserInfo(UserInfo user);

	void updateUserBaseInfo(UserInfo user);

	void updateUserRights(UserInfo user);

	int getCountByName(String loginname);

	void deleteUser(int userId);

	int getCount(UserInfo user);

	void updateUserPwd(UserInfo user);

	List<UserInfo> listPageUser(UserInfo user);

	UserInfo getUserAndRoleById(Integer userId);

	void updateLastLogin(UserInfo user);
}
