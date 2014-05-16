// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.event;

import ums.axon.domain.UserId;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class UserAuthenticatedEvent {

    private final UserId userId;

    /**
     * DOC crazyLau UserAuthenticatedEvent constructor comment.
     * 
     * @param userId
     */
    public UserAuthenticatedEvent(UserId userId) {
        this.userId = userId;
    }

}
