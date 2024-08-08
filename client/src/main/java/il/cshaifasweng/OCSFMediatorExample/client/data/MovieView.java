package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Date;

public class MovieView {
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<Date> releaseDate;
    private final SimpleStringProperty description;
    private final SimpleStringProperty language;
    private final SimpleStringProperty genre;
    private final SimpleStringProperty country;
    private final SimpleStringProperty imageUrl;
    private final SimpleStringProperty trailerUrl;
    private final SimpleBooleanProperty hasScreenings;

    public MovieView(String name, Date releaseDate, String description, String language, String genre, String country, String imageUrl, String trailerUrl, boolean hasScreenings) {
        this.name = new SimpleStringProperty(name);
        this.releaseDate = new SimpleObjectProperty<>(releaseDate);
        this.description = new SimpleStringProperty(description);
        this.language = new SimpleStringProperty(language);
        this.genre = new SimpleStringProperty(genre);
        this.country = new SimpleStringProperty(country);
        this.imageUrl = new SimpleStringProperty(imageUrl);
        this.trailerUrl = new SimpleStringProperty(trailerUrl);
        this.hasScreenings = new SimpleBooleanProperty(hasScreenings);
    }

    public MovieView(Movie movie) {
        this.name = new SimpleStringProperty(movie.getName());
        this.releaseDate = new SimpleObjectProperty<>(movie.getReleaseDate());
        this.description = new SimpleStringProperty(movie.getDescription());
        this.language = new SimpleStringProperty(movie.getLanguage().toString());
        this.genre = new SimpleStringProperty(movie.getGenre().toString());
        this.country = new SimpleStringProperty(movie.getCountry().toString());
        this.imageUrl = new SimpleStringProperty(movie.getImageUrl());
        this.trailerUrl = new SimpleStringProperty(movie.getTrailerUrl());
        this.hasScreenings = new SimpleBooleanProperty(!movie.getScreenings().isEmpty());
    }

    // Getter methods for properties
    public String getName() { return name.get(); }
    public Date getReleaseDate() { return releaseDate.get(); }
    public String getDescription() { return description.get(); }
    public String getLanguage() { return language.get(); }
    public String getGenre() { return genre.get(); }
    public String getCountry() { return country.get(); }
    public String getImageUrl() { return imageUrl.get(); }
    public String getTrailerUrl() { return trailerUrl.get(); }
    public boolean getHasScreenings() { return hasScreenings.get(); }

    // Property methods for JavaFX bindings
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleObjectProperty<Date> releaseDateProperty() { return releaseDate; }
    public SimpleStringProperty descriptionProperty() { return description; }
    public SimpleStringProperty languageProperty() { return language; }
    public SimpleStringProperty genreProperty() { return genre; }
    public SimpleStringProperty countryProperty() { return country; }
    public SimpleStringProperty imageUrlProperty() { return imageUrl; }
    public SimpleStringProperty trailerUrlProperty() { return trailerUrl; }
    public SimpleBooleanProperty hasScreeningsProperty() { return hasScreenings; }
}
