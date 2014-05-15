// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.event;

import ums.axon.domain.UserId;
import ums.axon.query.RoleEntry;
import ums.axon.query.UserEntry;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserCreatedEvent {

    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    private RoleEntry role;

    /**
     * DOC crazyLau UserCreatedEvent constructor comment.
     * 
     * @param id
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param role
     */
    public UserCreatedEvent(String id, String firstName, String lastName, String userName, String password, RoleEntry role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public UserId getUserIdentifier() {
        return new UserId(this.id);
    }

    /**
     * Getter for firstName.
     * 
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName.
     * 
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for userName.
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for role.
     * 
     * @return the role
     */
    public RoleEntry getRole() {
        return role;
    }
}
