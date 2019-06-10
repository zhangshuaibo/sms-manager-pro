package com.roadm.manager.service;


import com.roadm.manager.model.RoleInfo;

import java.util.List;

public interface RoleService {
	
	List<RoleInfo> listPageRoles(RoleInfo roleInfo);

	List<RoleInfo> listAllRoles(int role_id);

	RoleInfo getRoleById(int roleId);

	boolean insertRole(RoleInfo role);

	boolean updateRoleBaseInfo(RoleInfo role);

	void deleteRoleById(int roleId);

	void updateRoleRights(RoleInfo role);
}
