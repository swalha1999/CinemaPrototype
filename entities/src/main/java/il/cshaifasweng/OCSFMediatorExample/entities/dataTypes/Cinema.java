package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
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
    private Set<Hall> halls = new HashSet<>();

    @OneToMany(mappedBy = "cinema")
    private Set<Hall> screening = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "cinema_id")
    private User manager;

    public Cinema(String name, City city, String address, String phoneNumber, String email) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Cinema() {
        this.name = "";
        this.city = City.TEL_AVIV;
        this.address = "";
        this.phoneNumber = "";
        this.email = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Hall> getHalls() {
        return halls;
    }

    public void setHalls(Set<Hall> halls) {
        this.halls = halls;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
        if (manager.getCinema() != this) {
            manager.setCinema(this);
        }
    }

    public Set<Hall> getScreening() {
        return screening;
    }

    public void setScreening(Set<Hall> screening) {
        this.screening = screening;
    }

    public void addHall(Hall hall) {
        this.halls.add(hall);
        if (hall.getCinema() != this) {
            hall.setCinema(this);
        }
    }

    public void addScreening(Hall hall) {
        this.screening.add(hall);
        if (hall.getCinema() != this) {
            hall.setCinema(this);
        }
    }

    public void removeHall(Hall hall) {
        this.halls.remove(hall);
        if (hall.getCinema() == this) {
            hall.setCinema(null);
        }
    }

    public void removeScreening(Hall hall) {
        this.screening.remove(hall);
        if (hall.getCinema() == this) {
            hall.setCinema(null);
        }
    }

    public void removeAllHalls() {
        for (Hall hall : halls) {
            hall.setCinema(null);
        }
        this.halls.clear();
    }

    public void removeAllScreenings() {
        for (Hall hall : screening) {
            hall.setCinema(null);
        }
        this.screening.clear();
    }

    public void removeManager(User manager) {
        this.manager = null;
        if (manager.getCinema() == this) {
            manager.setCinema(null);
        }
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", halls=" + halls +
                ", manager=" + manager +
                '}';
    }
}
