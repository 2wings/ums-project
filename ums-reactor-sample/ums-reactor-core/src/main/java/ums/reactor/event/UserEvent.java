// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.reactor.event;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public interface UserEvent {

    String USER_QUERY = "user.query";

    String USER_COMMAND = "user.command";

    String USER_UPDATE = "user.update";
    
    String USER_CREATE = "user.create";

    String USER_DELETE = "user.delete";

    String USER_GET_ALL = "user.getall";
    
    String USER_QUERY_REPLY = "user.queryall.reply";

    String USER_LOGIN = "user.login";

    String USER_LOGIN_REPLY = "user.login.reply";
    

}
