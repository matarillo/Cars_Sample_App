package supercars.repositories;

import io.agroal.api.AgroalDataSource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import supercars.entities.Enquiry;

import javax.enterprise.context.RequestScoped;
import java.util.Collection;
import java.util.Optional;

@RequestScoped
public class EnquiryRepository {
    private final AgroalDataSource dataSource;

    public EnquiryRepository(AgroalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Enquiry> findById(int enquiryId) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT ENQUIRY_ID, NAME, EMAIL, COMMENT, CAR_ID FROM ENQUIRIES WHERE ENQUIRY_ID = :enquiryId")
                    .addParameter("enquiryId", enquiryId)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(Enquiry.class)
                    .stream()
                    .findFirst();
        }
    }

    public Collection<Enquiry> findAllByCarId(int carId) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT ENQUIRY_ID, NAME, EMAIL, COMMENT, CAR_ID FROM ENQUIRIES WHERE CAR_ID = :carId")
                    .addParameter("carId", carId)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(Enquiry.class);
        }
    }

    public Enquiry save(Enquiry enquiry) {
        if (enquiry.getEnquiryId() == null) {
            return insert(enquiry);
        } else {
            return update(enquiry);
        }
    }

    public Enquiry update(Enquiry enquiry) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            connection.createQuery("UPDATE ENQUIRIES SET NAME = :name, EMAIL = :email, COMMENT = :comment, CAR_ID = :carId, DUMMY = :dummy WHERE ENQUIRY_ID = :enquiryId")
                    .setAutoDeriveColumnNames(true)
                    .bind(enquiry)
                    .executeUpdate();
            Enquiry newEnquiry = enquiry.clone();
            return newEnquiry;
        }
    }

    public  Enquiry insert(Enquiry enquiry) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            Object enquiryId = connection.createQuery("INSERT INTO ENQUIRIES (NAME, EMAIL, COMMENT, CAR_ID, DUMMY) VALUES (:name, :email, :comment, :carId, :dummy)", true)
                    .setAutoDeriveColumnNames(true)
                    .bind(enquiry)
                    .executeUpdate()
                    .getKey();
            enquiry.setEnquiryId((Integer)enquiryId);
            return enquiry;
        }
    }
}
