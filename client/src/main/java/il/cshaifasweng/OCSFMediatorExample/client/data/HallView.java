package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class HallView {
    private final Hall hall;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty seats;

    public HallView(Hall hall) {
        this.hall = hall;
        this.name = new SimpleStringProperty(hall.getName());
        this.seats = new SimpleIntegerProperty(hall.getSeatsNum());
    }

    // Getter for hall
    public Hall getHall() {
        return hall;
    }

    // Getter for name property
    public String getName() {
        return name.get();
    }

    // Property for name
    public StringProperty nameProperty() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name.set(name);
    }

    // Getter for seats property
    public int getSeats() {
        return seats.get();
    }

    // Property for seats
    public IntegerProperty seatsProperty() {
        return seats;
    }

    // Setter for seats
    public void setSeats(int seats) {
        this.seats.set(seats);
    }
}
