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
    private String city;
    private String address;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hall> halls = new HashSet<>();

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Screening> screenings = new HashSet<>();

    @OneToOne
    private User manager;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupportTicket> supportTickets = new HashSet<>();

    public Cinema(String name, String city, String address, String phoneNumber, String email) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Cinema() {
        this.name = "";
        this.city = "TEL_AVIV";
        this.address = "";
        this.phoneNumber = "";
        this.email = "";
    }

    // Getters and Setters
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

    public String getCity() {
        return city;
    }

    public Cinema setCity(String city) {
        this.city = city;
        return this;
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

    public Cinema setManager(User manager) {
        this.manager = manager;
        return this;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }

    public Set<SupportTicket> getSupportTickets() {
        return supportTickets;
    }

    public void setSupportTickets(Set<SupportTicket> supportTickets) {
        this.supportTickets = supportTickets;
    }

    public void addHall(Hall hall) {
        this.halls.add(hall);
        if (hall.getCinema() != this) {
            hall.setCinema(this);
        }
    }

    public void addScreening(Screening screening) {
        this.screenings.add(screening);
        if (screening.getCinema() != this) {
            screening.setCinema(this);
        }
    }

    public void addSupportTicket(SupportTicket ticket) {
        this.supportTickets.add(ticket);
        ticket.setCinema(this);
    }

    public void removeHall(Hall hall) {
        this.halls.remove(hall);
        if (hall.getCinema() == this) {
            hall.setCinema(null);
        }
    }

    public void removeScreening(Screening screening) {
        this.screenings.remove(screening);
        if (screening.getCinema() == this) {
            screening.setCinema(null);
        }
    }

    public void removeSupportTicket(SupportTicket ticket) {
        this.supportTickets.remove(ticket);
        if (ticket.getCinema() == this) {
            ticket.setCinema(null);
        }
    }

    public void removeAllHalls() {
        for (Hall hall : halls) {
            hall.setCinema(null);
        }
        this.halls.clear();
    }

    public void removeAllScreenings() {
        for (Screening screening : screenings) {
            screening.setCinema(null);
        }
        this.screenings.clear();
    }

    public void removeAllSupportTickets() {
        for (SupportTicket ticket : supportTickets) {
            ticket.setCinema(null);
        }
        this.supportTickets.clear();
    }

    public void removeManager(User manager) {
        this.manager = null;
    }

    public Cinema deepCopy() {
        Cinema cinema = new Cinema();
        cinema.setId(this.id);
        cinema.setName(this.name);
        cinema.setCity(this.city);
        cinema.setAddress(this.address);
        cinema.setPhoneNumber(this.phoneNumber);
        cinema.setEmail(this.email);
        cinema.setManager(this.manager);
        cinema.setHalls(new HashSet<>(this.halls)); // Deep copy if needed
        cinema.setScreenings(new HashSet<>(this.screenings)); // Deep copy if needed
        cinema.setSupportTickets(new HashSet<>(this.supportTickets)); // Deep copy if needed
        return cinema;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", manager=" + (manager != null ? manager.getUsername() : "None") +
                ", supportTickets=" + supportTickets +
                '}';
    }
}
