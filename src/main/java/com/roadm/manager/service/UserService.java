package com.roadm.manager.service;


import com.roadm.manager.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

	List<UserInfo> listPageUser(UserInfo user);
	
	List<UserInfo> listAllUser();
	
	UserInfo getUserById(Integer userId);
	
	UserInfo getUserByName(String username);
	
	UserInfo getUserAndRoleById(Integer userId);

	boolean insertUser(UserInfo user);

	void updateUser(UserInfo user);

	void updateUserBaseInfo(UserInfo user);

	void updateUserRights(UserInfo user);

	void insertOperateLog(Map map);

	void deleteUser(int userId);

	void updateLastLogin(UserInfo user);

	void updateUserPwd(UserInfo user);


}
