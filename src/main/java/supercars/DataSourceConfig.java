package supercars;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfig {
    private final DataSourceProperties props;

    public DataSourceConfig(DataSourceProperties props) {
        this.props = props;
    }

    @Bean
    public DataSource getDataSource() {
        overrideDataSource();
        DataSourceBuilder builder = DataSourceBuilder.create();
        return builder
                .url(props.getUrl())
                .username(props.getUsername())
                .password(props.getPassword())
                .driverClassName(props.getDriverClassName())
                .type(props.getType())
                .build();
    }

    private void overrideDataSource() {
        String connstr = System.getenv("MYSQLCONNSTR_localdb");
        System.out.println(connstr);
        if (connstr == null || connstr.isEmpty()) {
            return;
        }
        Map<String, String> connmap = parse(connstr);
        String hostAndPort = connmap.get("Data Source");
        String database = connmap.get("Database");
        String url = String.format("jdbc:mysql://%s/%s?serverTimezone=UTC", hostAndPort, database);
        String username = connmap.get("User Id");
        String password = connmap.get("Password");
        props.setUrl(url);
        props.setUsername(username);
        props.setPassword(password);
        System.out.println(props.getUrl());
        System.out.println(props.getUsername());
        System.out.println(props.getPassword());
    }

    private Map<String, String> parse(String connstr) {
        String[] pairs = connstr.split(";");
        Map<String, String> connmap = new HashMap<>();
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            if (pair == null || pair.isEmpty()) {
                continue;
            }
            int sep = pair.indexOf('=');
            if (sep < 0) {
                continue;
            }
            String key = pair.substring(0, sep);
            String value = pair.substring(sep + 1);
            connmap.put(key, value);
        }
        return connmap;
    }
}
