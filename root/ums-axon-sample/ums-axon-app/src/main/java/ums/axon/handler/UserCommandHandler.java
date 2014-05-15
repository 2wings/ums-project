package ums.axon.handler;

import java.util.UUID;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ums.axon.command.CreateUserCommand;
import ums.axon.command.DeleteUserCommand;
import ums.axon.command.UpdateUserCommand;
import ums.axon.domain.UserAR;
import ums.axon.domain.UserId;
import ums.axon.query.UserEntry;
import ums.axon.query.repository.UserQueryRepository;

/**
 * @author liushuangyi@126.com
 */
@Component
public class UserCommandHandler {

    private Repository<UserAR> repository;

    private UserQueryRepository userQueryRepository;

    @CommandHandler
    public String handleCreateUser(CreateUserCommand command) {
        UserAR user = new UserAR(command.getId(), command.getFirstName(), command.getLastName(), command.getUserName(),
                command.getPassword(), command.getRole());
        repository.add(user);
        return command.getId();
    }

    @CommandHandler
    public String handleDeleteUser(DeleteUserCommand command) {
        UserEntry account = userQueryRepository.findById(command.getId());
        if (account == null) {
            return null;
        }

        onUser(account.getId()).delete(account);
        return account.getId();
    }

    @CommandHandler
    public boolean handleUpdateUser(UpdateUserCommand command) {
        UserEntry account = userQueryRepository.findById(command.getUserEntry().getId());
        if (account == null) {
            return false;
        }
        boolean success = onUser(account.getId()).update(command.getUserEntry());
        return success;
    }

    private UserAR onUser(String userId) {
        return repository.load(new UserId(userId), null);
    }

    @Autowired
    @Qualifier("userRepository")
    public void setRepository(Repository<UserAR> userRepository) {
        this.repository = userRepository;
    }

    @Autowired
    public void setUserRepository(UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

}
