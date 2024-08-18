package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "actors")
public class Producer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Country country;

    @OneToMany(mappedBy = "producer")
    private Set<Movie> movies = new HashSet<>();


    public Producer(String firstName, String lastName, Date birthDate, Country country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.country = country;
    }

    public Producer() {
        this.firstName = "";
        this.lastName = "";
        this.birthDate = new Date();
        this.country = Country.USA;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Country getCountry() {
        return country;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer actor = (Producer) o;
        return id == actor.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + getFullName() + '\'' +
                ", birthDate=" + birthDate +
                ", country=" + country +
                '}';
    }

    public String getCountryName() {
        return country.name();
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }
}
