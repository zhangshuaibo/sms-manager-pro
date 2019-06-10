package com.roadm.manager.service.impl;


import com.roadm.manager.mapper.RoleMapper;
import com.roadm.manager.model.RoleInfo;
import com.roadm.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public List<RoleInfo> listPageRoles(RoleInfo roleInfo) {

		return roleMapper.listPageRoles(roleInfo);
	}

	public List<RoleInfo> listAllRoles(int role_id) {

		return roleMapper.listAllRoles(role_id);
	}

	public void deleteRoleById(int roleId) {

		roleMapper.deleteRoleById(roleId);
	}

	public RoleInfo getRoleById(int roleId) {

		return roleMapper.getRoleById(roleId);
	}

	public boolean insertRole(RoleInfo role) {

		if (roleMapper.getCountByName(role) > 0)
			return false;
		else {
			roleMapper.insertRole(role);
			return true;
		}
	}

	public boolean updateRoleBaseInfo(RoleInfo role) {

		if (roleMapper.getCountByName(role) > 0)
			return false;
		else {
			roleMapper.updateRoleBaseInfo(role);
			return true;
		}
	}

	public void updateRoleRights(RoleInfo role) {

		roleMapper.updateRoleRights(role);
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

}
