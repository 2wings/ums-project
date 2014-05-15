package ums.axon.controller;

import java.util.UUID;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import samples.ums.axon.command.CreateUserCommand;
import samples.ums.axon.command.DeleteUserCommand;
import samples.ums.axon.command.UpdateUserCommand;
import samples.ums.axon.domain.UserId;
import samples.ums.axon.dto.UserListDto;
import samples.ums.axon.query.RoleEntry;
import samples.ums.axon.query.UserEntry;
import samples.ums.axon.service.UserQueryService;
import samples.ums.axon.utils.UserFactory;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private UserQueryService service;

    @RequestMapping
    public String getUsersPage() {
        return "users";
    }

    @RequestMapping(value = "/records")
    public @ResponseBody
    UserListDto getUsers() {
        UserListDto userListDto = new UserListDto();
        userListDto.setUsers(service.readAll());
        return userListDto;
    }

    @RequestMapping(value = "/get")
    public @ResponseBody
    UserEntry get(@RequestBody
    UserEntry user) {
        return service.read(user);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    String create(@RequestParam
    String userName, @RequestParam
    String password, @RequestParam
    String firstName, @RequestParam
    String lastName, @RequestParam
    Integer role) {
        CreateUserCommand command = new CreateUserCommand(new UserId().toString(), firstName, lastName, userName,
                password, new RoleEntry(role));
        UserCreatedCB userCB = new UserCreatedCB();
        commandBus.dispatch(new GenericCommandMessage<Object>(command), userCB);
        return userCB.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Boolean update(@RequestParam
    String id, @RequestParam
    String firstName, @RequestParam
    String lastName, @RequestParam
    Integer role) {
        UserEntry existingUser = new UserEntry();
        existingUser.setId(id);
        existingUser.setFirstName(firstName);
        existingUser.setLastName(lastName);
        existingUser.setRole(new RoleEntry(role));
        UpdateUserCommand command = new UpdateUserCommand(existingUser);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
        return true;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Boolean delete(@RequestParam
    String id) {
        DeleteUserCommand command = new DeleteUserCommand(id);
        commandBus.dispatch(new GenericCommandMessage<Object>(command));
        return true;
    }

    class UserCreatedCB implements CommandCallback<String> {

        private String id;

        @Override
        public void onSuccess(String userId) {
            id = userId;

        }

        /**
         * DOC crazyLau Comment method "getId".
         * 
         * @return
         */
        public String getId() {
            return this.id;
        }

        @Override
        public void onFailure(Throwable cause) {

        }
    }
}
