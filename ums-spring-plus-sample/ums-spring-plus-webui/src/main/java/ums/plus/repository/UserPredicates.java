// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.repository;

import ums.plus.domain.QUser;

import com.mysema.query.types.Predicate;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserPredicates {

    public static Predicate usernameIs(final String searchTerm) {
        QUser user = QUser.user;
        return user.username.equalsIgnoreCase(searchTerm);
    }
}
