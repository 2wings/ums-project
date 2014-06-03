// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.domain;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * DOC crazyLau  class global comment. Detailled comment
 * @author liushuangyi@126.com
 */
public class UserTest {
    
    private static final String TEST_FIRST_NAME = "liu";
    private static final String TEST_LAST_NAME = "sy";
    
    private static final String TEST_UPDATE_FIRST_NAME = "liu";
    private static final String TEST_UPDATE_LAST_NAME = "sya";
    
    @Test
    public void buildUser(){
        User user = User.getBuilder(TEST_FIRST_NAME, TEST_LAST_NAME).build();
        
        assertEquals(TEST_FIRST_NAME,user.getFirstName());
        assertEquals(TEST_LAST_NAME,user.getLastName());
        assertEquals(TEST_LAST_NAME+" "+TEST_FIRST_NAME, user.getFullName());
        assertNull(user.getEmail());
        assertNull(user.getPhone());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        
        
        
    }
    
    @Test
    public void updateUserName(){
        User user = User.getBuilder(TEST_FIRST_NAME, TEST_LAST_NAME).build();
        user.update(TEST_UPDATE_FIRST_NAME, TEST_UPDATE_LAST_NAME);
        
        assertEquals(TEST_UPDATE_FIRST_NAME,user.getFirstName());
        assertEquals(TEST_UPDATE_LAST_NAME,user.getLastName());
        assertEquals(TEST_UPDATE_LAST_NAME+" "+TEST_UPDATE_FIRST_NAME, user.getFullName());
    }

}
