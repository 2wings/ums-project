// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.repository;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import ums.plus.domain.User;
import ums.plus.service.IUserService;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mysema.query.types.Predicate;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    private static final String PROPERTY_LASTNAME = "lastName";

    private static final String SEARCH_TERM = "username";

    private static final int PAGE_INDEX = 5;

    private PaginatingUserRepositoryImpl userRepository;

    @Mock
    private QueryDslJpaRepository userJpaRepositoryMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Injector injector = Guice.createInjector(new UserRepositoryModule());
        userRepository = injector.getInstance(PaginatingUserRepositoryImpl.class);
    }

    class UserRepositoryModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(QueryDslJpaRepository.class).toInstance(userJpaRepositoryMock);
        }
    }

    @Test
    public void findAllUsers() {
        userRepository.findAllUsers();

        ArgumentCaptor<Sort> sortArgument = ArgumentCaptor.forClass(Sort.class);
        verify(userJpaRepositoryMock, times(1)).findAll(sortArgument.capture());

        Sort sort = sortArgument.getValue();
        assertEquals(Sort.Direction.ASC, sort.getOrderFor(PROPERTY_LASTNAME).getDirection());
    }

    @Test
    public void findUserPage() {
        List<User> expected = new ArrayList();
        Page foundPage = new PageImpl<User>(expected);

        when(userJpaRepositoryMock.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);

        List<User> actual = userRepository.findUsersForPage(SEARCH_TERM, PAGE_INDEX);

        ArgumentCaptor<Pageable> pageSpecificationArgument = ArgumentCaptor.forClass(Pageable.class);

        verify(userJpaRepositoryMock, times(1)).findAll(any(Predicate.class), pageSpecificationArgument.capture());

        Pageable pageSpecification = pageSpecificationArgument.getValue();
        assertEquals(PAGE_INDEX, pageSpecification.getPageNumber());
        assertEquals(userRepository.NUMBER_OF_PERSONS_PER_PAGE, pageSpecification.getPageSize());
        assertEquals(Sort.Direction.ASC, pageSpecification.getSort().getOrderFor(PROPERTY_LASTNAME).getDirection());

        assertEquals(expected, actual);
    }


    @Test
    public void createUser() throws Exception {
        User newUser = new User();

        doAnswer(new Answer<User>() {

            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                User User = (User) args[0];
                User.setId(1L);
                return User;
            }

        }).when(userJpaRepositoryMock).save(any(User.class));

        User persistedUser = userRepository.createUser(newUser);
        assertNotNull(persistedUser.getId());
    }

}
