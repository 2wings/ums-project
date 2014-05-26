// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.event;

import ums.axon.query.UserEntry;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserDeletedEvent {

    private UserEntry userEntry;

    public UserDeletedEvent(UserEntry userEntry) {
        this.userEntry = userEntry;
    }

    /**
     * DOC crazyLau Comment method "getUserEntry".
     * @return
     */
    public UserEntry getUserEntry() {
        return this.userEntry;
    }
}
