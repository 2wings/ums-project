// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.service;

import ums.plus.domain.User;
import ums.plus.dto.UserDTO;


/**
 * DOC crazyLau  class global comment. Detailled comment
 * @author liushuangyi@126.com
 */
public class UserTestUtil {

    /**
     * DOC crazyLau Comment method "createDTO".
     * @param object
     * @param firstName
     * @param lastName
     * @return
     */
    public static UserDTO createDTO(Long id, String firstName, String lastName) {
        UserDTO userDto = new UserDTO();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        
        return userDto;
    }

    /**
     * DOC crazyLau Comment method "createModelObject".
     * @param userId
     * @param firstName
     * @param lastName
     * @return
     */
    public static User createModelObject(Long userId, String firstName, String lastName) {
        return User.getBuilder(firstName, lastName).id(userId).build();
    }

}
