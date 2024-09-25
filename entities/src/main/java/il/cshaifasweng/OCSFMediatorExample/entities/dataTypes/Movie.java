package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

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

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] imageBytes = null;

    private String trailerUrl;
    private String englishTitle;
    private String hebrewTitle;
    private String producer;
    private int durationInMinutes;
    private boolean comingSoon;
    private boolean isOnlineMovie;

    private String actors;

    @OneToMany(mappedBy = "movie")
    private Set<Screening> screenings = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<PriceChangeRequest> priceChangeRequests = new HashSet<>();

    public Movie(){}

    // Warning: don't copy the id of the movie (it's unique)
    public Movie(Movie other){
        this.name = other.name;
        this.releaseDate = other.releaseDate;
        this.description = other.description;
        this.language = other.language;
        this.genre = other.genre;
        this.country = other.country;
        this.imageBytes = other.imageBytes;
        this.trailerUrl = other.trailerUrl;
        this.englishTitle = other.englishTitle;
        this.hebrewTitle = other.hebrewTitle;
        this.durationInMinutes = other.durationInMinutes;
        this.comingSoon = other.comingSoon;
        this.isOnlineMovie = other.isOnlineMovie;
        this.actors = other.actors;
    }

    public Movie(String name, Date releaseDate) {
        this.name = name;
        this.englishTitle = name;
        this.releaseDate = releaseDate;
        this.comingSoon = false;
        this.isOnlineMovie = false;
    }

    // Getters and Setters
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

    public String getActors() {
        return actors;
    }

    public Movie setActors(String actors) {
        this.actors=actors;
        return this;
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

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public Movie setImageBytes(byte[] image) {
        this.imageBytes = image;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public Movie setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
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

    public boolean isComingSoon() {
        return comingSoon;
    }

    public Movie setComingSoon(boolean comingSoon) {
        this.comingSoon = comingSoon;
        return this;
    }

    public boolean isOnlineMovie() {
        return isOnlineMovie;
    }

    public Movie setOnlineMovie(boolean onlineMovie) {
        isOnlineMovie = onlineMovie;
        return this;
    }

    public Movie copy(Movie movie ) {
        this.name = movie.name == null ? this.name : movie.name;
        this.releaseDate = movie.releaseDate == null ? this.releaseDate : movie.releaseDate;
        this.description = movie.description == null ? this.description : movie.description;
        this.language = movie.language == null ? this.language : movie.language;
        this.genre = movie.genre == null ? this.genre : movie.genre;
        this.country = movie.country == null ? this.country : movie.country;
        this.imageBytes = movie.imageBytes == null ? this.imageBytes : movie.imageBytes;
        this.trailerUrl = movie.trailerUrl == null ? this.trailerUrl : movie.trailerUrl;
        this.englishTitle = movie.englishTitle == null ? this.englishTitle : movie.englishTitle;
        this.hebrewTitle = movie.hebrewTitle == null ? this.hebrewTitle : movie.hebrewTitle;
        this.durationInMinutes = movie.durationInMinutes == 0 ? this.durationInMinutes : movie.durationInMinutes;
        this.producer = movie.producer == null ? this.producer : movie.producer;
        this.actors = movie.actors == null ? this.actors : movie.actors;
        this.screenings = movie.screenings == null ? this.screenings : movie.screenings;
        this.imageBytes = movie.imageBytes;
        this.comingSoon = movie.comingSoon;
        this.isOnlineMovie = movie.isOnlineMovie;
        return this;
    }

    public Set<PriceChangeRequest> getPriceChangeRequests() {
        return priceChangeRequests;
    }

    public void addPriceChangeRequest(PriceChangeRequest priceChangeRequest) {
        this.priceChangeRequests.add(priceChangeRequest);
        priceChangeRequest.setMovie(this);
    }

    @Override
    public String toString() {
        return this.getTitle();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public Movie setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
