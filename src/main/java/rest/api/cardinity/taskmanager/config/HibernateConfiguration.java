package rest.api.cardinity.taskmanager.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@ComponentScans(value = {
        @ComponentScan("rest.api.cardinity.taskmanager.repository"),
        @ComponentScan("rest.api.cardinity.taskmanager.service")
})
public class HibernateConfiguration {

    @Value("${db.driver}")
    private String driverClass;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String userName;
    @Value("${db.password}")
    private String password;

//    @Value("${hibernate.dialect}")
//    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String ddlAuto;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.format_sql}")
    private String formatSql;
    @Value("${spring.datasource.initialization-mode}")
    private String dataSourceInitialization;



    @Bean
    public DataSource getDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
//        dataSource.setDriverClassName(driverClass);
        return dataSource;
    }

    private Properties getHibernateProps(){
        Properties properties = new Properties();
//        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);
        properties.put("spring.jpa.hibernate.ddl-auto", "none");
        properties.put("spring.datasource.initialization-mode", dataSourceInitialization);
        return properties;
    }

    private String[] getPackagesToScan(){
        return new String[]{
                "rest.api.cardinity.taskmanager.models.entity"
        };
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory(){
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());
        factoryBean.setHibernateProperties(getHibernateProps());
        factoryBean.setPackagesToScan(getPackagesToScan());
        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}


