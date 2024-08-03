package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private Date releaseDate;
    private String description;
    private Language language;
    private MovieGenre genre;
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<Screening> screenings = new HashSet<>();

    public Movie(){}
    public Movie(String name, Date date) {
        this.name = name;
        this.releaseDate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return releaseDate;
    }

    public void setDate(Date date) {
        this.releaseDate = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void addScreening(Screening screening) {
        this.screenings.add(screening);
        screening.setMovie(this);
    }

    public void removeScreening(Screening screening) {
        this.screenings.remove(screening);
        screening.setMovie(null); //TODO: not sure if this is the right way to do it (might cause problems) or should we delete the screening from the database maybe? (server problem) :)
    }

    public void addActor(Actor actor) {
        this.actors.add(actor);
        actor.addMovie(this);
    }

    public void removeActor(Actor actor) {
        this.actors.remove(actor);
        actor.removeMovie(this);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", description='" + description + '\'' +
                ", language=" + language +
                ", genre=" + genre +
                ", country=" + country +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public String getTitle() {
        return name;
    }
}