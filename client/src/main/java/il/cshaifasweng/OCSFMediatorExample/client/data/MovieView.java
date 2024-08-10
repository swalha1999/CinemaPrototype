package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class MovieView {
    private int id;
    private SimpleStringProperty englishTitle;
    private SimpleStringProperty hebrewTitle;
    private SimpleObjectProperty<Date> releaseDate;
    private SimpleStringProperty description;
    private SimpleStringProperty language;
    private SimpleStringProperty genre;
    private SimpleStringProperty country;
    private SimpleStringProperty imageUrl;
    private SimpleStringProperty trailerUrl;


    public MovieView(int id, String englishTitle,String hebrewTitle, Date releaseDate, String description, String language, String genre, String country, String imageUrl, String trailerUrl, boolean hasScreenings) {
        this.id = id;
        this.englishTitle = new SimpleStringProperty(englishTitle);
        this.hebrewTitle = new SimpleStringProperty(hebrewTitle);
        this.releaseDate = new SimpleObjectProperty<>(releaseDate);
        this.description = new SimpleStringProperty(description);
        this.language = new SimpleStringProperty(language);
        this.genre = new SimpleStringProperty(genre);
        this.country = new SimpleStringProperty(country);
        this.imageUrl = new SimpleStringProperty(imageUrl);
        this.trailerUrl = new SimpleStringProperty(trailerUrl);

    }

    public MovieView(Movie movie) {
        this.id = movie.getId();
        this.englishTitle = new SimpleStringProperty(movie.getEnglishTitle());
        this.hebrewTitle = new SimpleStringProperty(movie.getHebrewTitle());
        this.releaseDate = new SimpleObjectProperty<>(movie.getReleaseDate());
        this.description = new SimpleStringProperty(movie.getDescription());

        // Null checks before calling toString()
        this.language = new SimpleStringProperty(
                movie.getLanguage() != null ? movie.getLanguage().toString() : "Unknown"
        );

        this.genre = new SimpleStringProperty(
                movie.getGenre() != null ? movie.getGenre().toString() : "Unknown"
        );

        this.country = new SimpleStringProperty(
                movie.getCountry() != null ? movie.getCountry().toString() : "Unknown"
        );

        this.imageUrl = new SimpleStringProperty(movie.getImageUrl());
        this.trailerUrl = new SimpleStringProperty(movie.getTrailerUrl());

    }

    // Getter methods for properties
    public String getEnglishTitle() { return englishTitle.get(); }
    public String getHebrewTitle() { return hebrewTitle.get(); }
    public Date getReleaseDate() { return releaseDate.get(); }
    public String getDescription() { return description.get(); }
    public String getLanguage() { return language.get(); }
    public String getGenre() { return genre.get(); }
    public String getCountry() { return country.get(); }
    public String getImageUrl() { return imageUrl.get(); }
    public String getTrailerUrl() { return trailerUrl.get(); }
    public int getId() { return id; }


    // Property methods for JavaFX bindings
    public SimpleStringProperty hebrewTitleProperty() { return hebrewTitle; }
    public SimpleStringProperty englishTitleProperty() { return englishTitle; }
    public SimpleObjectProperty<Date> releaseDateProperty() { return releaseDate; }
    public SimpleStringProperty descriptionProperty() { return description; }
    public SimpleStringProperty languageProperty() { return language; }
    public SimpleStringProperty genreProperty() { return genre; }
    public SimpleStringProperty countryProperty() { return country; }
    public SimpleStringProperty imageUrlProperty() { return imageUrl; }
    public SimpleStringProperty trailerUrlProperty() { return trailerUrl; }
    public SimpleStringProperty idProperty() { return new SimpleStringProperty(String.valueOf(id)); }

    public MovieView copy(Movie movie) {
        this.id = movie.getId();
        this.englishTitle = new SimpleStringProperty(movie.getEnglishTitle());
        this.hebrewTitle = new SimpleStringProperty(movie.getHebrewTitle());
        this.releaseDate = new SimpleObjectProperty<>(movie.getReleaseDate());
        this.description = new SimpleStringProperty(movie.getDescription());

        // Null checks before calling toString()
        this.language = new SimpleStringProperty(
                movie.getLanguage() != null ? movie.getLanguage().toString() : "Unknown"
        );

        this.genre = new SimpleStringProperty(
                movie.getGenre() != null ? movie.getGenre().toString() : "Unknown"
        );

        this.country = new SimpleStringProperty(
                movie.getCountry() != null ? movie.getCountry().toString() : "Unknown"
        );

        this.imageUrl = new SimpleStringProperty(movie.getImageUrl());
        this.trailerUrl = new SimpleStringProperty(movie.getTrailerUrl());
        return this;
    }
}
