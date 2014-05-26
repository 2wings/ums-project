package ums.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ums.spring.domain.UserEntry;
import ums.spring.repository.RoleQueryRepository;
import ums.spring.repository.UserQueryRepository;

@Service
public class UserService {

    @Autowired
    private UserQueryRepository userRepository;

    @Autowired
    private RoleQueryRepository roleRepository;

    public UserEntry create(UserEntry user) {
        roleRepository.save(user.getRole());
        return userRepository.save(user);
    }

    public List<UserEntry> readAll() {
        return userRepository.findAll();
    }

    public UserEntry update(UserEntry user) {
        UserEntry existingUser = (UserEntry) userRepository.findById(user.getId());

        if (existingUser == null) {
            return null;
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setRole(user.getRole());

        // We must save both separately since there is no cascading feature
        // in Spring Data MongoDB (for now)
        roleRepository.save(existingUser.getRole());
        return userRepository.save(existingUser);
    }

    public Boolean delete(UserEntry user) {
    	UserEntry existingUser = (UserEntry) userRepository.findById(user.getId());
        if (existingUser == null) {
            return false;
        }
        roleRepository.delete(existingUser.getRole());
        userRepository.delete(existingUser);
        return true;
    }

	public UserEntry read(UserEntry user) {
		return user;
	}
}
