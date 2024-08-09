package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class MovieView {
    private int id;
    private final SimpleStringProperty title;
    private final SimpleObjectProperty<Date> releaseDate;
    private final SimpleStringProperty description;
    private final SimpleStringProperty language;
    private final SimpleStringProperty genre;
    private final SimpleStringProperty country;
    private final SimpleStringProperty imageUrl;
    private final SimpleStringProperty trailerUrl;


    public MovieView(String name, Date releaseDate, String description, String language, String genre, String country, String imageUrl, String trailerUrl, boolean hasScreenings) {
        this.title = new SimpleStringProperty(name);
        this.releaseDate = new SimpleObjectProperty<>(releaseDate);
        this.description = new SimpleStringProperty(description);
        this.language = new SimpleStringProperty(language);
        this.genre = new SimpleStringProperty(genre);
        this.country = new SimpleStringProperty(country);
        this.imageUrl = new SimpleStringProperty(imageUrl);
        this.trailerUrl = new SimpleStringProperty(trailerUrl);

    }

    public MovieView(Movie movie) {
        this.title = new SimpleStringProperty(movie.getName());
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
    public String getTitle() { return title.get(); }
    public Date getReleaseDate() { return releaseDate.get(); }
    public String getDescription() { return description.get(); }
    public String getLanguage() { return language.get(); }
    public String getGenre() { return genre.get(); }
    public String getCountry() { return country.get(); }
    public String getImageUrl() { return imageUrl.get(); }
    public String getTrailerUrl() { return trailerUrl.get(); }


    // Property methods for JavaFX bindings
    public SimpleStringProperty titleProperty() { return title; }
    public SimpleObjectProperty<Date> releaseDateProperty() { return releaseDate; }
    public SimpleStringProperty descriptionProperty() { return description; }
    public SimpleStringProperty languageProperty() { return language; }
    public SimpleStringProperty genreProperty() { return genre; }
    public SimpleStringProperty countryProperty() { return country; }
    public SimpleStringProperty imageUrlProperty() { return imageUrl; }
    public SimpleStringProperty trailerUrlProperty() { return trailerUrl; }

}
