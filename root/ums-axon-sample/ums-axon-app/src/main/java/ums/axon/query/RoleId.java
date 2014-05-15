// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.query;

import java.io.Serializable;

import org.axonframework.common.Assert;
import org.axonframework.domain.IdentifierFactory;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class RoleId implements Serializable {

    private static final long serialVersionUID = 8340311073636127792L;
    private String identifier;

    public RoleId() {
        this.identifier = IdentifierFactory.getInstance().generateIdentifier();
    }

    public RoleId(String identifier) {
        Assert.notNull(identifier, "Identifier may not be null");
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RoleId userId = (RoleId) o;

        return identifier.equals(userId.identifier);

    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public String toString() {
        return identifier;
    }

}
