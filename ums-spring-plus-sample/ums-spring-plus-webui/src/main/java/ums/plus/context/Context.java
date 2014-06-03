// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.context;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public interface Context {

    String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    String PROPERTY_NAME_DATABASE_URL = "db.url";
    String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    String PROPERTY_PACKAGES_TO_SCAN = "net.petrikainulainen.spring.datajpa.todo.model";

}
