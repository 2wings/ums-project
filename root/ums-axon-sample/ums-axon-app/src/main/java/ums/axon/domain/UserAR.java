// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.domain;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import ums.axon.event.UserAuthenticatedEvent;
import ums.axon.event.UserCreatedEvent;
import ums.axon.event.UserDeletedEvent;
import ums.axon.event.UserUpdatedEvent;
import ums.axon.query.RoleEntry;
import ums.axon.query.UserEntry;
import ums.axon.utils.DigestUtils;

/**
 * User Aggregate Root handle all properties as well as role domain event
 * 
 * @author liushuangyi@126.com
 */
public class UserAR extends AbstractAnnotatedAggregateRoot<UserId> {

    private static final long serialVersionUID = 893025080473878172L;
    @AggregateIdentifier
    private UserId userId;
    private char[] passwordHash;

    protected UserAR() {
    }

    /**
     * DOC crazyLau UserAR constructor comment.
     * 
     * @param id
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param role
     */
    public UserAR(String id, String firstName, String lastName, String userName, String password, RoleEntry role) {
        apply(new UserCreatedEvent(id, firstName, lastName, userName, password, role));
    }

    @EventHandler
    public void onUserCreated(UserCreatedEvent event) {
        this.userId = event.getUserIdentifier();
        this.passwordHash = event.getPassword().toCharArray();
    }

    @EventHandler
    public void onUserCreated(UserDeletedEvent event) {
        this.userId = new UserId(event.getUserEntry().getId());
    }

    @Override
    public UserId getIdentifier() {
        return userId;
    }

    /**
     * DOC crazyLau Comment method "update".
     * 
     * @param userEntry
     * @return
     */
    public boolean update(UserEntry userEntry) {
        apply(new UserUpdatedEvent(userEntry));
        return true;
    }

    /**
     * DOC crazyLau Comment method "delete".
     * 
     * @param userEntry
     */
    public boolean delete(UserEntry userEntry) {
        apply(new UserDeletedEvent(userEntry));
        return true;

    }

    /**
     * DOC crazyLau Comment method "authenticate".
     * 
     * @param password
     * @return
     */
    public boolean authenticate(char[] password) {
        boolean success = this.passwordHash.equals(hashOf(password));
        if (success) {
            apply(new UserAuthenticatedEvent(userId));
        }
        return success;
    }

    /**
     * DOC crazyLau Comment method "hashOf".
     * 
     * @param password
     * @return
     */

    private String hashOf(char[] password) {
        return DigestUtils.sha1(String.valueOf(password));
    }
}
