// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ums.plus.domain.User;
import ums.plus.repository.UserRepository;
import ums.plus.service.IUserService;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

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
    public User createUser(User user) {
        userRepository.createUser(user);
        return user;
    }

    /**
     * DOC crazyLau Comment method "count".
     * @param searchTerm
     * @return
     */
    public long count(String searchTerm) {
       return userRepository.findUserCount(searchTerm);
    }

    /**
     * DOC crazyLau Comment method "delete".
     * @param personId
     * @return
     */
    public User delete(Long personId) {
        // TODO Auto-generated method stub
        return null;
    }

}
