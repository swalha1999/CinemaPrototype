package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CinemaView {
    private final Cinema cinema;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<String> city;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty managerName;

    public CinemaView(Cinema cinema) {
        this.cinema = cinema;
        id = new SimpleIntegerProperty(cinema.getId());
        name = new SimpleStringProperty(cinema.getName());
        city = new SimpleObjectProperty<>(cinema.getCity());
        address = new SimpleStringProperty(cinema.getAddress());
        phoneNumber = new SimpleStringProperty(cinema.getPhoneNumber());
        email = new SimpleStringProperty(cinema.getEmail());
        managerName = new SimpleStringProperty(cinema.getManager().getUsername());
    }

    // Parameterized Constructor
    public CinemaView(Cinema cinema, int id, String name, String city, String address, String phoneNumber, String email, String managerName) {
        this.cinema = cinema;
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.city = new SimpleObjectProperty<>(city);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.managerName = new SimpleStringProperty(managerName);
    }

    // Copy method to return a new instance
    public CinemaView copy(Cinema other) {
        return new CinemaView(other);  // Create and return a new instance
    }

    // Getters
    public SimpleIntegerProperty getId() {
        return id;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleObjectProperty<String> getCity() {
        return city;
    }

    public SimpleStringProperty getAddress() {
        return address;
    }

    public SimpleStringProperty getPhoneNumber() {
        return phoneNumber;
    }

    public SimpleStringProperty getEmail() {
        return email;
    }

    public String getManagerName() {
        return managerName.get();
    }

    // Property methods for JavaFX bindings
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleObjectProperty<String> cityProperty() {
        return city;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty managerNameProperty() {
        return managerName;
    }

    // Setters with method chaining
    public CinemaView setId(int id) {
        this.id.set(id);
        return this;
    }

    public CinemaView setName(String name) {
        this.name.set(name);
        return this;
    }

    public CinemaView setCity(String city) {
        this.city.set(city);
        return this;
    }

    public CinemaView setAddress(String address) {
        this.address.set(address);
        return this;
    }

    public CinemaView setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
        return this;
    }

    public CinemaView setEmail(String email) {
        this.email.set(email);
        return this;
    }

    public CinemaView setManagerName(String managerName) {
        this.managerName.set(managerName);
        return this;
    }

    public Cinema getCinema() {
        return cinema;
    }

    // Override toString for ComboBox display
    @Override
    public String toString() {
        return name.get();  // This will display the cinema name in the ComboBox
    }
}
