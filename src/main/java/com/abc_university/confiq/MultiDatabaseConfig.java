package com.abc_university.confiq;


	import com.zaxxer.hikari.HikariConfig;
	import com.zaxxer.hikari.HikariDataSource;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

	import javax.sql.DataSource;
	import java.util.HashMap;
	import java.util.Map;

	@Configuration
	public class MultiDatabaseConfig {

	    @Value("${spring.datasource.university.url}")
	    private String universityUrl;
	    @Value("${spring.datasource.university.username}")
	    private String universityUsername;
	    @Value("${spring.datasource.university.password}")
	    private String universityPassword;

	    @Value("${spring.datasource.joseph.url}")
	    private String josephUrl;
	    @Value("${spring.datasource.joseph.username}")
	    private String josephUsername;
	    @Value("${spring.datasource.joseph.password}")
	    private String josephPassword;

	    @Value("${spring.datasource.bishop.url}")
	    private String bishopUrl;
	    @Value("${spring.datasource.bishop.username}")
	    private String bishopUsername;
	    @Value("${spring.datasource.bishop.password}")
	    private String bishopPassword;

	    @Value("${spring.datasource.jamal.url}")
	    private String jamalUrl;
	    @Value("${spring.datasource.jamal.username}")
	    private String jamalUsername;
	    @Value("${spring.datasource.jamal.password}")
	    private String jamalPassword;

	    @Value("${spring.datasource.national.url}")
	    private String nationalUrl;
	    @Value("${spring.datasource.national.username}")
	    private String nationalUsername;
	    @Value("${spring.datasource.national.password}")
	    private String nationalPassword;

	    @Bean
	    public DataSource dataSource() {
	        Map<Object, Object> dataSourceMap = new HashMap<>();

	        dataSourceMap.put("university", createDataSource(universityUrl, universityUsername, universityPassword));
	        dataSourceMap.put("joseph", createDataSource(josephUrl, josephUsername, josephPassword));
	        dataSourceMap.put("bishop", createDataSource(bishopUrl, bishopUsername, bishopPassword));
	        dataSourceMap.put("jamal", createDataSource(jamalUrl, jamalUsername, jamalPassword));
	        dataSourceMap.put("national", createDataSource(nationalUrl, nationalUsername, nationalPassword));

	        RoutingDataSource routingDataSource = new RoutingDataSource();
	        routingDataSource.setDefaultTargetDataSource(dataSourceMap.get("university"));
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
