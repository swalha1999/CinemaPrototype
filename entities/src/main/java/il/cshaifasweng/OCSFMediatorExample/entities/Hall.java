package il.cshaifasweng.OCSFMediatorExample.entities;

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
    private int id;

    private String name;
    private int rows;
    private int columns;

    @OneToMany(mappedBy = "hall")
    private Set<Screening> screenings = new HashSet<>();

    public Hall(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public Hall() {
        this.name = "";
        this.rows = 0;
        this.columns = 0;
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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
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

    public int getAvailableSeats() {
        return rows * columns;
    }
}