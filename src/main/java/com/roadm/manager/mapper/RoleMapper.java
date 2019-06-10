package com.roadm.manager.mapper;


import com.roadm.manager.model.RoleInfo;

import java.util.List;

public interface RoleMapper {
	List<RoleInfo> listPageRoles(RoleInfo roleInfo);

	List<RoleInfo> listAllRoles(int role_id);

	RoleInfo getRoleById(int roleId);

	void insertRole(RoleInfo role);

	void updateRoleBaseInfo(RoleInfo role);

	void deleteRoleById(int roleId);

	int getCountByName(RoleInfo role);

	void updateRoleRights(RoleInfo role);

}
