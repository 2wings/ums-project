// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.reactor.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserDTO {

    private String id;

    @NotEmpty
    @Length(max = 20)
    private String firstName;

    @NotEmpty
    @Length(max = 20)
    private String lastName;
    @NotEmpty
    @Length(max = 20)
    private String userName;
    @NotEmpty
    @Length(min = 6, max = 20)
    private String password;

    @NotEmpty
    @Range(min = 0, max = 2)
    private Integer role;

    /**
     * Getter for id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * Sets the firstName.
     * 
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Sets the lastName.
     * 
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Sets the userName.
     * 
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for role.
     * 
     * @return the role
     */
    public Integer getRole() {
        return role;
    }

    /**
     * Sets the role.
     * 
     * @param role the role to set
     */
    public void setRole(Integer role) {
        this.role = role;
    }

}
