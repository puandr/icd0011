package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
//klass võetud harjutustunnist
@Configuration
public class PostgresDataSource {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername(env.getProperty("postgres.user"));
        ds.setPassword(env.getProperty("postgres.pass"));
        ds.setUrl(env.getProperty("postgres.url"));
        return ds;
    }

    @Bean("dialect")
    public String dialect() {
        return "org.hibernate.dialect.PostgreSQL10Dialect";
    }

}