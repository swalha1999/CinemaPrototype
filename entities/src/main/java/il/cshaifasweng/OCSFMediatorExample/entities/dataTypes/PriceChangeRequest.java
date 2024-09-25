package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "price_change_requests")
public class PriceChangeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "content_manager_id")
    private User contentManager;


    private double newPrice;

    public PriceChangeRequest() {
        this.screening = null;
        this.movie = null;
        this.newPrice = 0;
    }

    public PriceChangeRequest(Screening screening, Movie movie, double newPrice) {
        this.screening = screening;
        this.movie = movie;
        this.newPrice = newPrice;
    }

    public Long getId() {
        return id;
    }

    public PriceChangeRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public Screening getScreening() {
        return screening;
    }

    public PriceChangeRequest setScreening(Screening screening) {
        this.screening = screening;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public PriceChangeRequest setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public PriceChangeRequest setNewPrice(double newPrice) {
        this.newPrice = newPrice;
        return this;
    }

    public User getContentManager() {
        return contentManager;
    }

    public PriceChangeRequest setContentManager(User contentManager) {
        this.contentManager = contentManager;
        return this;
    }

    @Override
    public String toString() {
        return "PriceChangeRequest{" +
                "id=" + id +
                ", screening=" + screening +
                ", movie=" + movie +
                ", newPrice=" + newPrice +
                '}';
    }

}
