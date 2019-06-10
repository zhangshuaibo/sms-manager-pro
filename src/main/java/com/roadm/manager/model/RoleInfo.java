package com.roadm.manager.model;

import java.io.Serializable;


public class RoleInfo implements Serializable {
	private static final long serialVersionUID = 2184261138624568318L;
	private int role_id;
	private String roleName;
	private String rights;
	private String description;
	private String create_user;
	private Page page;

	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}



	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
