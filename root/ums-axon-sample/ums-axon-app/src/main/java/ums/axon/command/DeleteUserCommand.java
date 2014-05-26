// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.command;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class DeleteUserCommand {

    private final String id;

    /**
     * DOC crazyLau DeleteUserCommand constructor comment.
     * 
     * @param existingUser
     */
    public DeleteUserCommand(String id) {
        this.id = id;
    }

    /**
     * DOC crazyLau Comment method "getUserEntry".
     * 
     * @return
     */
    public String getId() {
        return this.id;
    }

}
