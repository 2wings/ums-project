package samples.usermanagement.axon.handler;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ums.axon.command.CreateUserCommand;
import ums.axon.command.DeleteUserCommand;
import ums.axon.command.UpdateUserCommand;
import ums.axon.domain.UserAR;
import ums.axon.domain.UserId;
import ums.axon.event.UserCreatedEvent;
import ums.axon.event.UserDeletedEvent;
import ums.axon.event.UserUpdatedEvent;
import ums.axon.handler.UserCommandHandler;
import ums.axon.query.RoleEntry;
import ums.axon.query.UserEntry;
import ums.axon.query.repository.UserQueryRepository;
import ums.axon.utils.UserFactory;

/**
 * @author liushuangyi@126.com
 */
public class UserCommandHandlerTest {

    private FixtureConfiguration fixture;

    private UserQueryRepository userQueryRepository;

    private UserEntry user1;
    private UserEntry user2;

    @Before
    public void setUp() {
        userQueryRepository = Mockito.mock(UserQueryRepository.class);

        fixture = Fixtures.newGivenWhenThenFixture(UserAR.class);

        UserId aggregateIdentifier = new UserId();
        user1 = UserFactory.create(aggregateIdentifier.toString(), "admin", "test", "admin", "aaaaaa", new RoleEntry(1));
        user2 = UserFactory.create(aggregateIdentifier.toString(), "common", "test", "common", "aaaaaa", new RoleEntry(1));

        UserCommandHandler commandHandler = new UserCommandHandler();
        commandHandler.setRepository(fixture.getRepository());
        commandHandler.setUserRepository(userQueryRepository);

        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void testHandleCreateUser() throws Exception {
        fixture.given()
                .when(new CreateUserCommand(user1.getId(), user1.getFirstName(), user1.getLastName(), user1
                        .getUserName(), user1.getPassword(), user1.getRole()))
                .expectEvents(
                        new UserCreatedEvent(user1.getId(), user1.getFirstName(), user1.getLastName(), user1
                                .getUserName(), user1.getPassword(), user1.getRole()));
    }

    @Test
    public void testHandleDeleteUser() throws Exception {
        Mockito.when(userQueryRepository.findById(user1.getId())).thenReturn(user1);

        fixture.given(
                new UserCreatedEvent(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getUserName(),
                        user1.getPassword(), user1.getRole())).when(new DeleteUserCommand(user1.getId()))
                .expectEvents(new UserDeletedEvent(user1));
    }

    @Test
    public void testHandleUpdateUser() throws Exception {
        Mockito.when(userQueryRepository.findById(user1.getId())).thenReturn(user1);

        fixture.given(
                new UserCreatedEvent(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getUserName(),
                        user1.getPassword(), user1.getRole())).when(new UpdateUserCommand(user2))
                .expectEvents(new UserUpdatedEvent(user2));
    }
}
