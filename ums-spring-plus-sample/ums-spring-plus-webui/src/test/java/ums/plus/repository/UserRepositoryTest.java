// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.repository;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.mysema.query.types.Predicate;

import ums.plus.domain.User;
import ums.plus.service.IUserService;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    EntityManager em;

    @Mock
    IUserService userService;

    @InjectMocks
    UserRepository userRepository = new UserRepository();

    @Test
    public void findAllUsersCount(){
        
        when(userRepository.count(any(Predicate.class))).thenReturn("1");
        
      
    }
    
    
    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User();

//        when(userRepository.createUser(newUser)).thenReturn(new User());

        doAnswer(new Answer<User>() {

            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                User User = (User) args[0];
                User.setId(UUID.randomUUID().node());
                return User;
            }

        }).when(em).persist(any(User.class));

        User persistedUser = userRepository.createUser(newUser);
        assertNotNull(persistedUser.getId());
    }

}
