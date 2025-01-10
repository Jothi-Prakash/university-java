package com.university_java.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiDataSource{
	
	@Value("${spring.datasource.url}")
	private String universityUrl;
	
	@Value("${spring.datasource.joseph.url}")
	private String josephUrl;
	
	@Value("${spring.datasource.bishop.url}")
	private String bishopUrl;
	
	@Value("${spring.datasource.jamal.url}")
	private String jamalUrl;
	
	@Value("${spring.datasource.national.url}")
	private String nationalUrl;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	

    @Bean
    @Primary
    public DataSource dataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();

        DataSource universityDataSource = createDataSource(universityUrl, username, password);
        DataSource josephDataSource = createDataSource(josephUrl, username, password);
        DataSource bishopDataSource = createDataSource(bishopUrl, username, password);
        DataSource jamalDataSource = createDataSource(jamalUrl, username, password);
        DataSource nationalDataSource = createDataSource(nationalUrl, username, password);

        dataSourceMap.put("university", universityDataSource);
        dataSourceMap.put("joseph", josephDataSource);
        dataSourceMap.put("bishop", bishopDataSource);
        dataSourceMap.put("jamal", jamalDataSource);
        dataSourceMap.put("national", nationalDataSource);

        // Create Routing DataSource
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(universityDataSource); 
        routingDataSource.setTargetDataSources(dataSourceMap); 
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }

    private DataSource createDataSource(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    public static class RoutingDataSource extends AbstractRoutingDataSource {
        @Override
        protected Object determineCurrentLookupKey() {
            
            return TenantContext.getCurrentTenant();
        }
    }
}
