package ua.skuhtin.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
    private final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${auth.datasource.url}")
    private String jdbcUrl;
    @Value("${auth.datasource.username}")
    private String dbUser;
    @Value("${auth.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private long connTimeOut;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int poolSize;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minimumIdle;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private long connMaxLife;
    @Value("${spring.jpa.show-sql}")
    private boolean showSql;
    @Value("${db.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;
    @Value("${db.hibernate.sql.dialect}")
    private String hibernateDialect;
    @Value("${db.hibernate.connection.charset}")
    private String charsetEncoding;
    @Value("${db.hibernate.use.unicode}")
    private boolean useUnicode;
    @Value("${db.hibernate.order.inserts}")
    private boolean orderInserts;
    @Value("${db.hibernate.order.updates}")
    private boolean orderUpdates;
    @Value("${db.hibernate.statistic}")
    private boolean hibernateStatistic;
    @Value("${db.hibernate.batch.size}")
    private int batchSize;

    @Bean
    public DataSource dataSource() {
        logger.debug("Init database");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(dbUser);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setConnectionTimeout(connTimeOut);
        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setMinimumIdle(minimumIdle);
        hikariConfig.setMaxLifetime(connMaxLife);
        return new HikariDataSource(hikariConfig);
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:/database/schema");
        flyway.setDataSource(dataSource());
        return flyway;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(showSql);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan("ua.skuhtin");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("connection.pool_size", poolSize);
        props.put("hibernate.show_sql", showSql);
        props.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        props.put("hibernate.dialect", hibernateDialect);
        props.put("hibernate.connection.charSet", charsetEncoding);
        props.put("connection.characterEncoding", charsetEncoding);
        props.put("hibernate.connection.Useunicode", useUnicode);
        props.put("hibernate.jdbc.batch_size", batchSize);
        props.put("hibernate.order_inserts", orderInserts);
        props.put("hibernate.order_updates", orderUpdates);
        props.put("hibernate.generate_statistics", hibernateStatistic);
        props.put("hibernate.format_sql", true);
        props.put("hibernate.use_sql_comments", true);
        //see https://docs.jboss.org/envers/docs/#configuration
        props.put("org.hibernate.envers.store_data_at_delete", true);
        return props;
    }
}
