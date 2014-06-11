// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.service;

import java.util.List;

import ums.plus.domain.User;
import ums.plus.dto.UserDTO;


/**
 * DOC crazyLau  class global comment. Detailled comment
 * @author liushuangyi@126.com
 */

public interface IUserService {

    /**
     * DOC crazyLau Comment method "getPageList".
     * @param page
     * @param rows
     * @return
     */
    List<User> getPageList(int page, int rows);
    

    /**
     * DOC crazyLau Comment method "findUserByUsername".
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * DOC crazyLau Comment method "createUser".
     * @param userDto
     * @return
     */
    User createUser(UserDTO userDto);


    /**
     * DOC crazyLau Comment method "findAll".
     * @return
     */
    List<User> findAll();

}
