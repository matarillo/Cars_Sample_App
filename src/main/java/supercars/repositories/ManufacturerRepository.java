package supercars.repositories;

import io.agroal.api.AgroalDataSource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import supercars.entities.Manufacturer;

import javax.enterprise.context.RequestScoped;
import java.util.Collection;
import java.util.Optional;

@RequestScoped
public class ManufacturerRepository {
    private final AgroalDataSource dataSource;

    public ManufacturerRepository(AgroalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Manufacturer> findById(int manufacturerId) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT MANUFACTURER_ID, NAME, WEB, EMAIL, LOGO FROM MANUFACTURER WHERE MANUFACTURER_ID = :manufacturerId")
                    .addParameter("manufacturerId", manufacturerId)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(Manufacturer.class)
                    .stream()
                    .findFirst();
        }
    }

    public Collection<Manufacturer> findAllOrderByNameAsc() {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT MANUFACTURER_ID, NAME, WEB, EMAIL, LOGO FROM MANUFACTURER ORDER BY NAME")
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(Manufacturer.class);
        }
    }
}
