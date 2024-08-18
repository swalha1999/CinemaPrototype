package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.City;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CinemaView {
    private final Cinema cinema;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<City> city;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private  final SimpleStringProperty managerName;


   public CinemaView(Cinema cinema) {
       this.cinema = cinema;
       id = new SimpleIntegerProperty(cinema.getId());
       name = new SimpleStringProperty(cinema.getName());
       city = new SimpleObjectProperty<>(cinema.getCity());
       address = new SimpleStringProperty(cinema.getAddress());
       phoneNumber = new SimpleStringProperty(cinema.getPhoneNumber());
       email = new SimpleStringProperty(cinema.getEmail());
       this.managerName = new SimpleStringProperty((cinema.getManager().getUsername()));
   }


    // Parameterized Constructor
    public CinemaView(Cinema cinema, int id, String name, City city, String address, String phoneNumber, String email, String managerName) {
        this.cinema = cinema;
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.city = new SimpleObjectProperty<>(city);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.managerName = new SimpleStringProperty(managerName);
    }
    // Copy method
    public CinemaView copy(Cinema other) {
        this.id.set(other.getId());
        this.name.set(other.getName());
        this.city.set(other.getCity());
        this.address.set(other.getAddress());
        this.phoneNumber.set(other.getPhoneNumber());
        this.email.set(other.getEmail());
        return this;
    }
    // Getters
   public SimpleIntegerProperty getId() {
        return id;
   }
   public SimpleStringProperty getName() {
        return name;
   }
   public SimpleObjectProperty<City> getCity() {
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
    // Property methods for JavaFX bindings
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleObjectProperty<City> cityProperty() {
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

    // Setters with method chaining
    public CinemaView setId(int id) {
        this.id.set(id);
        return this;
    }

    public CinemaView setName(String name) {
        this.name.set(name);
        return this;
    }

    public CinemaView setCity(City city) {
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

    public String getManagerName() {
        return managerName.get();
    }
    public SimpleStringProperty managerNameProperty() {
       return managerName;
    }
    public CinemaView setManagerName(String managerName) {
       this.managerName.set(managerName);
       return this;
    }

    public Cinema getCinema() {
        return cinema;
    }

}
