// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.reactor.model;

import static org.junit.Assert.*;

import org.junit.Test;

import reactor.filter.FirstFilter;
import ums.reactor.domain.User;


/**
 * DOC crazyLau  class global comment. Detailled comment
 * @author liushuangyi@126.com
 */
public class UserTest {

    
    private static final String USERNAME = "lsy";
    private static final String PASSWORD = "aaa";
    private static final String FIRST_NAME = "liu";
    private static final String LAST_NAME = "sy";
    private static final int ROLE = 1;
    private static final String ROLE_NAME = "admin";
    @Test
    public void buildUser(){
        User user = User.getBuilder(USERNAME,PASSWORD,FIRST_NAME,LAST_NAME,ROLE).build();
        assertEquals(USERNAME,user.getUserName());
        assertEquals(PASSWORD,user.getPassword());
        assertEquals(FIRST_NAME,user.getFirstName());
        assertEquals(LAST_NAME,user.getLastName()  );
        assertEquals(ROLE_NAME,  user.getRole().getRoleEnum());
        
    }
}
