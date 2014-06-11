// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.context;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "ums.plus.controller", "ums.plus.service", "ums.plus.repository" })
@Import({ SecurityConfig.class })
//@ImportResource("classpath:applicationContext.xml")
@PropertySource("classpath:application.properties")
public class ApplicationContext {

    public static final String USER_PERSISTENCE = "user-persistence";

    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public EntityManager entityManager() throws ClassNotFoundException {
        return entityManagerFactory().createEntityManager();
    }

    @Bean 
    public EntityManagerFactory entityManagerFactory() throws ClassNotFoundException {
        return entityManagerFactoryBean().getObject();
    }
    
    /**
     * replace persistence.xml DOC crazyLau Comment method "entityManagerFactoryBean".
     * 
     * @return
     * @throws ClassNotFoundException
     */
//    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName(USER_PERSISTENCE);
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(environment
                .getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);

        Properties jpaProterties = new Properties();
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

        entityManagerFactoryBean.setJpaProperties(jpaProterties);

        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean;
    }
    
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
        return viewResolver;
    }
    
}
