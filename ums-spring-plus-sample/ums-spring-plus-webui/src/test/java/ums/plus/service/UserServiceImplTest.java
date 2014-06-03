package ums.plus.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.engine.spi.SessionImplementor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import ums.plus.context.JpaTestConfig;
import ums.plus.context.TestConfig;
import ums.plus.domain.User;
import ums.plus.repository.UserRepository;
import ums.plus.service.IUserService;
import ums.plus.service.impl.UserService;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaTestConfig.class, TestConfig.class })
// @ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class UserServiceImplTest {

    // @Autowired
    // private UserService myService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("classpath:usersDataSet.xml")
    public void testGetAllFakeUsers() {
        List users = userRepository.getAllUsers();
        assertEquals(11, users.size());
    }

    @Test
    @DatabaseSetup("classpath:usersDataSet.xml")
    public void searchNotFoundShouldReturnEmptyList() {
        List<User> users = userRepository.searchById("NOT FOUND");
        assertThat(users.size(), is(0));
    }

    @Test
    @DatabaseSetup("classpath:usersDataSet.xml")
    @ExpectedDatabase("classpath:expectedDataSet.xml")
    public void searchOneUserFoundShouldReturnAListOfOneUser() {
        List<User> users = userRepository.searchById("1");
        assertThat(users.size(), is(1));
    }

    @Test
    @DatabaseSetup("classpath:usersDataSet.xml")
    public void searchTwoUsersFoundShouldReturnAListOfTwoUser() {
        List users = userRepository.searchByUsername("liusya");
        assertThat(users.size(), is(2));
        assertThat(
                (List<Object>) users,
                contains(
                        allOf(hasProperty("id", is(1)), hasProperty("firstname", is("liu")),
                                hasProperty("lastname", is("sy"))),
                        allOf(hasProperty("id", is(2)), hasProperty("firstname", is("liu")),
                                hasProperty("lastname", is("sy")))

                ));
    }
}
