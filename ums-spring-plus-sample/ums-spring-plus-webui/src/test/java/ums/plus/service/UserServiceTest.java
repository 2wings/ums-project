// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ums.plus.domain.User;
import ums.plus.dto.UserDTO;
import ums.plus.repository.PaginatingUserRepositoryImpl;
import ums.plus.repository.UserRepository;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserServiceTest {

    private static final String FIRST_NAME = "liu";
    private static final String LAST_NAME = "sy";
    private static final Long USER_ID = 1L;
    private UserService userService;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userService = new UserService();

        userRepository = mock(UserRepository.class);
        userService.setUserRepository(userRepository);
    }

    @Test
    public void create() {
        UserDTO created = UserTestUtil.createDTO(null, FIRST_NAME, LAST_NAME);
        User persisted = UserTestUtil.createModelObject(USER_ID, FIRST_NAME, LAST_NAME);

        when(userRepository.save(any(User.class))).thenReturn(persisted);

        User returned = userService.createUser(created);

        ArgumentCaptor<User> personArgument = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(personArgument.capture());
        verifyNoMoreInteractions(userRepository);

        assertPerson(created, personArgument.getValue());
//        assertEquals(persisted, returned);
    }
    
    private void assertPerson(UserDTO expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), expected.getLastName());
    }
}
