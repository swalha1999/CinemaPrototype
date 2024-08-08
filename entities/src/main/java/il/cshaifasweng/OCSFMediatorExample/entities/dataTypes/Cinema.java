package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "cinemas")
public class Cinema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private City city;
    private String address;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "cinema")
    private Set<Hall> halls;



//    private User manager; // TODO: For future use
//    private Set<User> employees;


}
