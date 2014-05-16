package ums.axon.controller;

import java.util.concurrent.ExecutionException;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ums.axon.command.CreateUserCommand;
import ums.axon.command.DeleteUserCommand;
import ums.axon.command.UpdateUserCommand;
import ums.axon.domain.UserId;
import ums.axon.dto.UserListDto;
import ums.axon.query.RoleEntry;
import ums.axon.query.UserEntry;
import ums.axon.service.UserQueryService;

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
        FutureCallback<UserEntry> accountCallback = new FutureCallback<UserEntry>();
        commandBus.dispatch(new GenericCommandMessage<Object>(command), accountCallback);
        try {
            return accountCallback.get().getId();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
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

}
