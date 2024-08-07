package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

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
    private String imageUrl;
    private String trailerUrl;

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
    public Movie(String name, Date releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public Movie setName(String name) {
        this.name = name;
        return this ;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Movie setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this ;
    }

    public String getDescription() {
        return description;
    }

    public Movie setDescription(String description) {
        this.description = description;
        return this ;
    }

    public Language getLanguage() {
        return language;
    }

    public Movie setLanguage(Language language) {
        this.language = language;
        return this ;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public Movie setGenre(MovieGenre genre) {
        this.genre = genre;
        return this ;
    }

    public Country getCountry() {
        return country;
    }

    public Movie setCountry(Country country) {
        this.country = country;
        return this ;
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
        screening.setMovie(null);
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

    public Date getDate() {
        return this.getReleaseDate();
    }

    public Movie setDate(Date date) {
        this.setReleaseDate(date);
        return this;
    }

    public String getImageUrl() { return imageUrl;
    }

    public String getTrailerUrl() { return trailerUrl;
    }
    public Movie setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }
    public Movie setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}