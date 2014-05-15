// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================
package ums.spring.utils;

import java.util.UUID;

import ums.spring.domain.Role;
import ums.spring.domain.UserEntry;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserFactory {

    /**
     * DOC crazyLau Comment method "create".
     * 
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param role
     * @return User
     */
    public static UserEntry create(String firstName, String lastName, String userName, String password, Integer role) {
        return create(firstName, lastName, userName, password, new Role(role));
    }

    /**
     * DOC crazyLau Comment method "create".
     * 
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param role
     * @return User
     */
    public static UserEntry create(String firstName, String lastName, String userName, String password, Role role) {
        UserEntry user = new UserEntry();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    /**
     * create administrator role user
     * 
     * @param firstName
     * @param LastName
     * @param userName
     * @param password
     * @return User
     */
    public static UserEntry createAdmin(String firstName, String lastName, String userName, String password) {
        return create(firstName, lastName, userName, password, new Integer(1));
    }

    /**
     * create common role user
     * 
     * @param firstName
     * @param LastName
     * @param userName
     * @param password
     * @return User
     */
    public static UserEntry createUser(String firstName, String lastName, String userName, String password) {
        return create(firstName, lastName, userName, password, new Integer(2));
    }

}
