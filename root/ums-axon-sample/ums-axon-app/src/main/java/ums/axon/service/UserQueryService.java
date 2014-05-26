package ums.axon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ums.axon.query.UserEntry;
import ums.axon.query.repository.RoleQueryRepository;
import ums.axon.query.repository.UserQueryRepository;

@Service
public class UserQueryService {

    @Autowired
    private UserQueryRepository userRepository;

    @Autowired
    private RoleQueryRepository roleRepository;

    public UserEntry getUser(String id) {
        return userRepository.findById(id);
    }

    public List<UserEntry> readAll() {
        return userRepository.findAll();
    }

    public Boolean delete(UserEntry user) {
        UserEntry existingUser = (UserEntry) userRepository.findById(user.getId());

        if (existingUser == null) {
            return false;
        }

        // We must delete both separately since there is no cascading feature
        // in Spring Data MongoDB (for now)
        roleRepository.delete(existingUser.getRole());
        userRepository.delete(existingUser);
        return true;
    }

    public UserEntry read(UserEntry user) {
        return user;
    }
}
