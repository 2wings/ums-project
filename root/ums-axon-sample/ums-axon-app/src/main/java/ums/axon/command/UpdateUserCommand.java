// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.command;

import ums.axon.query.UserEntry;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UpdateUserCommand {

    private UserEntry user;

    /**
     * DOC crazyLau UpdateUserCommand constructor comment.
     * 
     * @param existingUser
     */
    public UpdateUserCommand(UserEntry user) {
        this.user = user;
    }

    /**
     * DOC crazyLau Comment method "getUserEntry".
     * 
     * @return
     */
    public UserEntry getUserEntry() {
        return user;
    }

}
