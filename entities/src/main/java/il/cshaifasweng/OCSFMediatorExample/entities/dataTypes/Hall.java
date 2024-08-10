package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "halls")
public class Hall implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "hall")
    private Set<Screening> screenings = new HashSet<>();

    @OneToMany(mappedBy = "hall")
    private Set<Seat> seats = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    public Hall(String name) {
        this.name = name;
    }

    public Hall() {
        this.name = "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void addScreening(Screening screening) {
        this.screenings.add(screening);
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void removeScreening(Screening screening) {
        this.screenings.remove(screening);
    }

    public void removeSeat(Seat seat) {
        this.seats.remove(seat);
    }

    public void removeAllSeats() {
        this.seats.clear();
    }

    public void removeAllScreenings() {
        this.screenings.clear();
    }

    public Cinema getCinema() {
        return cinema;
    }

    public Hall setCinema(Cinema cinema) {
        this.cinema = cinema;
        if (!cinema.getHalls().contains(this)) {
            cinema.addHall(this);
        }
        return this;
    }

}