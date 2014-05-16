// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.handler;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ums.axon.event.UserCreatedEvent;
import ums.axon.event.UserDeletedEvent;
import ums.axon.event.UserUpdatedEvent;
import ums.axon.query.UserEntry;
import ums.axon.query.repository.RoleQueryRepository;
import ums.axon.query.repository.UserQueryRepository;
import ums.axon.utils.UserFactory;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */

@Component
public class UserEventHandler {

    @Autowired
    private UserQueryRepository userRepository;

    @Autowired
    private RoleQueryRepository roleRepository;

    @EventHandler
    public void handleUserCreated(UserCreatedEvent event) {
        UserEntry user = UserFactory.create(event.getUserIdentifier().toString(), event.getFirstName(),
                event.getLastName(), event.getUserName(), event.getPassword(), event.getRole());
        roleRepository.save(event.getRole());
        userRepository.save(user);
    }

    @EventHandler
    public boolean handleUserUpdate(UserUpdatedEvent event) {
        UserEntry existingUser = userRepository.findById(event.getUserEntry().getId());
        if (existingUser == null) {
            return false;
        }
        existingUser.setFirstName(event.getUserEntry().getFirstName());
        existingUser.setLastName(event.getUserEntry().getLastName());
        existingUser.setRole(event.getUserEntry().getRole());

        // We must save both separately since there is no cascading feature
        // in Spring Data MongoDB (for now)
        roleRepository.save(existingUser.getRole());
        userRepository.save(existingUser);
        return true;
    }

    @EventHandler
    public boolean handleUserDelete(UserDeletedEvent event) {
        UserEntry existingUser = (UserEntry) userRepository.findById(event.getUserEntry().getId());

        if (existingUser == null) {
            return false;
        }
        roleRepository.delete(existingUser.getRole());
        userRepository.delete(existingUser);
        return true;
    }

}
