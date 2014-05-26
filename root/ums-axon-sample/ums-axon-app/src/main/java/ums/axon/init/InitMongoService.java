package ums.axon.init;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import ums.axon.command.CreateUserCommand;
import ums.axon.domain.UserId;
import ums.axon.query.RoleEntry;
import ums.axon.utils.DigestUtils;

/**
 * Service for initializing MongoDB with sample data using {@link MongoTemplate}
 */
@Component
public class InitMongoService {

    @Autowired
    private CommandBus commandBus;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void init() {
        // Drop existing collections
        mongoTemplate.dropCollection("role");
        mongoTemplate.dropCollection("userEntry");

        createuser("lau", "wings", "liusy");
        createuser("fan", "zhui", "fanfree");
    }

    private void createuser(String firstName, String lastName, String userName) {
        UserId userId = new UserId();
        CreateUserCommand createUser = new CreateUserCommand(userId.toString(), firstName, lastName, userName,
                DigestUtils.sha1(userName), new RoleEntry(1));
        commandBus.dispatch(new GenericCommandMessage<CreateUserCommand>(createUser));
    }
}
