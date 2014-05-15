// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================
package ums.spring.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import ums.spring.domain.UserEntry;
import ums.spring.dto.UserListDto;
import ums.spring.repository.UserQueryRepository;
import ums.spring.utils.UserFactory;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserRepositoryTest {

	@Mock
	UserQueryRepository userQueryRepository;

	@Spy
	UserListDto userList = new UserListDto();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void userRepositoryTest() {
		UserEntry user = (UserEntry) UserFactory.createUser("admin", "fortest",
				"admin", "aaaaaa");
		List users = new ArrayList();
		users.add(user);
		userList.setUsers(users);
		assertEquals(1, userList.getUsers().size());
		verify(userList).setUsers(users);

		when(userQueryRepository.findById("admin")).thenReturn(user);

		UserEntry user1 = userQueryRepository.findById("admin");
		assertNotNull(user1);
		when(userQueryRepository.findAll()).thenReturn(users);

		List<UserEntry> users1 = userQueryRepository.findAll();
		assertEquals(1, users1.size());
	}

}
