package ro.bluedreamshisha.backend.model.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    private User user;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String picture;
    private Boolean emailVerified;
    private String locale;
}
