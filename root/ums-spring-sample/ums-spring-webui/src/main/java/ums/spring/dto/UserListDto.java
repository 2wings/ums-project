package ums.spring.dto;

import java.util.List;

import ums.spring.domain.User;
import ums.spring.domain.UserEntry;

public class UserListDto {

	private List<UserEntry> users;

	public List<UserEntry> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntry> users) {
		this.users = users;
	}

}
