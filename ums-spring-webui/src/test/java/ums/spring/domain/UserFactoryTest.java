// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================
package ums.spring.domain;

import static org.junit.Assert.*;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import ums.spring.domain.Role;
import ums.spring.domain.UserEntry;
import ums.spring.domain.Role.RoleEnum;
import ums.spring.utils.UserFactory;

/**
 * @author liushuangyi@126.com
 */
public class UserFactoryTest {

    private UserEntry user;

    @Before
    public void initializeUser() {

    }

    @Test
    public void userCreateTest() {
        String firstName = "lau";
        String lastName = "wings";
        String userName = "liusy";
        String password = "aaaaaa";

        user = UserFactory.create(firstName, lastName, userName, password, new Role(1));

        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(userName, user.getUserName());

        // assertNotSame(RoleEnum.ADMIN, user.getRole());

        user = UserFactory.createAdmin(firstName, lastName, userName, password);

        // assertNotSame(RoleEnum.ADMIN, user.getRole());

    }

    @Test
    public void verifyUserRoleEnum() {
        RoleEnum role = Role.RoleEnum.getRole(new Integer(0));
        assertEquals("unknown", role.getRoleName());

        role = Role.RoleEnum.getRole(new Integer(1));
        assertEquals("admin", role.getRoleName());

        role = Role.RoleEnum.getRole(new Integer(2));
        assertEquals("regular", role.getRoleName());

        role = Role.RoleEnum.getRole(new Integer(4));
        assertEquals("unknown", role.getRoleName());
    }

    @Test
    public void convertUserModel() {
        String firstName = "lau";
        String lastName = "wings";
        String userName = "liusy";
        String password = "aaaaaa";

        user = UserFactory.create(firstName, lastName, userName, password, new Integer(1));

        ObjectMapper mapper = new ObjectMapper();
        String userJsonStr;
        try {
            userJsonStr = mapper.writeValueAsString(user);
            System.out.println(userJsonStr);

            UserEntry userJson = mapper.readValue(userJsonStr, UserEntry.class);
            String roleName = userJson.getRole().getRoleEnum().getRoleName();
            assertEquals("admin", roleName);

        } catch (IOException e) {
           fail();
        }

    }
}
