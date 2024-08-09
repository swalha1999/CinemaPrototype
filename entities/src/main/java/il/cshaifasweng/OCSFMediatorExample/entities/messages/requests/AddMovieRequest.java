package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Country;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Language;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieGenre;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.util.Date;

public class AddMovieRequest implements Request {
    private static final long serialVersionUID = -5386248350340650197L;

    private String sessionKey;
    private int userId;
    private String username;

    private String name;
    private Date releaseDate;
    private String description;
    private Language language;
    private MovieGenre genre;
    private Country country;
    private String imageUrl;
    private String trailerUrl;

    public AddMovieRequest() {
    }

    public AddMovieRequest(String name, Date releaseDate, String description, Language language, MovieGenre genre, Country country, String imageUrl, String trailerUrl) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
        this.language = language;
        this.genre = genre;
        this.country = country;
        this.imageUrl = imageUrl;
        this.trailerUrl = trailerUrl;
    }

    public String getName() {
        return name;
    }

    public AddMovieRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public AddMovieRequest setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddMovieRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public AddMovieRequest setLanguage(Language language) {
        this.language = language;
        return this;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public AddMovieRequest setGenre(MovieGenre genre) {
        this.genre = genre;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public AddMovieRequest setCountry(Country country) {
        this.country = country;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AddMovieRequest setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public AddMovieRequest setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public AddMovieRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public AddMovieRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AddMovieRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "AddMovieRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", description='" + description + '\'' +
                ", language=" + language +
                ", genre=" + genre +
                ", country=" + country +
                ", imageUrl='" + imageUrl + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                '}';
    }
}
