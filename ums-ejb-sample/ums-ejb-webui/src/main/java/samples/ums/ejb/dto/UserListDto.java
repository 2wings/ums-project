package samples.ums.ejb.dto;

import java.util.List;

import samples.ums.ejb.domain.User;

public class UserListDto {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}