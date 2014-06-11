// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ums.plus.domain.User;
import ums.plus.dto.UserDTO;
import ums.plus.repository.PaginatingUserRepositoryImpl;
import ums.plus.repository.UserRepository;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ums.plus.service.IUserService#getPageList(int, int)
     */
    @Override
    public List<User> getPageList(int page, int rows) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ums.plus.service.IUserService#createUser(ums.plus.domain.User)
     */
    @Override
    public User createUser(UserDTO userDto) {
        LOGGER.debug("creating a new user " + userDto);
        User user = User.getBuilder(userDto.getFirstName(), userDto.getLastName()).build();
        userRepository.save(user);
        return user;
    }

    /**
     * DOC crazyLau Comment method "count".
     * 
     * @param searchTerm
     * @return
     */
    public long count(String searchTerm) {
        return userRepository.findUserCount(searchTerm);
    }

    /**
     * DOC crazyLau Comment method "delete".
     * 
     * @param personId
     * @return
     */
    public User delete(Long personId) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ums.plus.service.IUserService#findUserByUsername(java.lang.String)
     */
    @Override
    public User findUserByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DOC crazyLau Comment method "findAll".
     * 
     * @return
     */
    public List<User> findAll() {
        return userRepository.findAllUsers();
    }

}
