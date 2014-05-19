package ums.reactor.domain;

import java.io.Serializable;
import java.util.List;

public class UserListDto implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 7579720698495837516L;
    private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}