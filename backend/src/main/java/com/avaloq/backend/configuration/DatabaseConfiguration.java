package com.avaloq.backend.configuration;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@ComponentScan
@EnableJpaRepositories(
        basePackages = "com.avaloq.backend",
        entityManagerFactoryRef = "diceEntityManager",
        transactionManagerRef = "diceTransactionManager"
)
@EntityScan(basePackages = {"com.avaloq.backend.model"})
@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseConfiguration.class);

    @Autowired
    private Environment env;

    @Bean(name = "diceEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean diceEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(diceDataSource());
        em.setPackagesToScan(
                new String[]{"com.avaloq.backend.model"});

        HibernateJpaVendorAdapter diceAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(diceAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",
                env.getProperty("spring.jpa.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @Primary
    public DataSource diceDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        try {
            dataSource.setDriverClassName(
                    env.getProperty("spring.datasource.driver-class-name"));
            dataSource.setUrl(env.getProperty("spring.datasource.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.password"));

        } catch (NullPointerException npe) {
            LOGGER.error(npe.getMessage());
        }
        return dataSource;
    }

    @Bean(name = "diceTransactionManager")
    @Primary
    public PlatformTransactionManager diceTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                diceEntityManager().getObject());
        return transactionManager;
    }
}

