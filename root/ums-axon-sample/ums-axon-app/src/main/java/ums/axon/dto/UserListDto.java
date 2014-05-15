package ums.axon.dto;

import java.util.List;

import ums.axon.query.UserEntry;

public class UserListDto {

	private List<UserEntry> users;

	public List<UserEntry> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntry> users) {
		this.users = users;
	}

}
