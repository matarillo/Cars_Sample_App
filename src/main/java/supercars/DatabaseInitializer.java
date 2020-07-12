package supercars;

import io.agroal.api.AgroalDataSource;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.Scanner;

import static org.jboss.logging.Logger.Level.INFO;

@Startup
@ApplicationScoped
public class DatabaseInitializer {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    private final AgroalDataSource dataSource;

    @Inject
    public DatabaseInitializer(AgroalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void initialize(String name, Config config, Connection connection) throws Exception {
        String propKey = "application.datasource." + name;
        Optional<String> optionalValue = config.getOptionalValue(propKey, String.class);
        if (optionalValue.isEmpty()) {
            return;
        }
        String propValue = optionalValue.get();
        if (!propValue.startsWith("classpath:")) {
            return;
        }
        String path = propValue.substring("classpath:".length());
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                return;
            }
            String sql = new Scanner(in).useDelimiter("\\Z").next();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.execute();
            }
            logger.log(INFO, name + " initialized.");
        }
    }

    void onStart(@Observes StartupEvent ev) throws Exception {
        logger.log(INFO, "The application is starting...");
        Config config = ConfigProvider.getConfig();
        try (Connection connection = dataSource.getConnection()) {
            initialize("schema", config, connection);
            initialize("data", config, connection);
        }
    }

    void onStop(@Observes ShutdownEvent ev) throws Exception {
        logger.log(INFO, "The application is stopping...");
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
