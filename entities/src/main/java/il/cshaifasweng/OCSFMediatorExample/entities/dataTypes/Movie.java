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
    private String englishTitle;
    private String hebrewTitle;
    private String producer;
    private int durationInMinutes;

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


    // Warning: don't copy the id of the movie (it's unique)
    public Movie(Movie other){
        this.name = other.name;
        this.releaseDate = other.releaseDate;
        this.description = other.description;
        this.language = other.language;
        this.genre = other.genre;
        this.country = other.country;
        this.imageUrl = other.imageUrl;
        this.trailerUrl = other.trailerUrl;
        this.englishTitle = other.englishTitle;
        this.hebrewTitle = other.hebrewTitle;
        this.durationInMinutes = other.durationInMinutes;

        //TODO: check if this is the right way to copy the set (this is a relationship ot another table)
    //    this.actors = other.actors;
    //    this.producer = other.producer;
    //    this.screenings = other.screenings;
    }

    public Movie(String name, Date releaseDate) {
        this.name = name;
        this.englishTitle = name;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public Movie setName(String name) {
        this.name = name;
        this.englishTitle = name;
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

    public String getEnglishTitle() {
        return englishTitle;
    }

    public Movie setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
        this.name = englishTitle;
        return this;
    }

    public String getHebrewTitle() {
        return hebrewTitle;
    }

    public Movie setHebrewTitle(String hebrewTitle) {
        this.hebrewTitle = hebrewTitle;
        return this;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Movie setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", englishTitle='" + englishTitle + '\'' +
                ", hebrewTitle='" + hebrewTitle + '\'' +
                ", releaseDate=" + releaseDate +
                ", description='" + description + '\'' +
                ", language=" + language +
                ", genre=" + genre +
                ", country=" + country +
                ", imageUrl='" + imageUrl + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", durationInMinutes=" + durationInMinutes +
//                ", actors=" + actors +
//                ", producer=" + producer +
//                ", screenings=" + screenings +
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

    public String getProducer() {
        return producer;
    }

    public Movie setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public Movie setId(int id) {
        this.id = id;
        return this;
    }

    public int getMovieId() {
        return id;
    }




}