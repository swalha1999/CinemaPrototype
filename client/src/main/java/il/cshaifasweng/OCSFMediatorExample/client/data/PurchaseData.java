package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;

import java.time.LocalDate;
import java.util.List;

public class PurchaseData {
    private String MovieTitle;
    private String MovieDuration;
    private LocalDate ScreeningTime;
    private int SeatPrice;
    private String OrderId;
    private final List<Seat> seats;

    public PurchaseData() {
        this.MovieTitle = "";
        this.MovieDuration = "";
        this.ScreeningTime = LocalDate.now();
        this.SeatPrice = 0;
        this.OrderId = "";
        this.seats = null;
    }

    public PurchaseData(String MovieTitle, String MovieDuration, LocalDate ScreeningTime, int SeatPrice, int TotalPrice, String OrderId, int ticketsNumber, List<Seat> seats) {
        this.MovieTitle = MovieTitle;
        this.MovieDuration = MovieDuration;
        this.ScreeningTime = ScreeningTime;
        this.SeatPrice = SeatPrice;
        this.OrderId = OrderId;
        this.seats = seats;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public PurchaseData setMovieTitle(String movieTitle) {
        this.MovieTitle = movieTitle;
        return this;
    }

    public String getMovieDuration() {
        return MovieDuration;
    }

    public PurchaseData setMovieDuration(String movieDuration) {
        this.MovieDuration = movieDuration;
        return this;
    }

    public LocalDate getScreeningTime() {
        return ScreeningTime;
    }

    public PurchaseData setScreeningTime(LocalDate screeningTime) {
        this.ScreeningTime = screeningTime;
        return this;
    }

    public int getSeatPrice() {
        return SeatPrice;
    }

    public PurchaseData setSeatPrice(int seatPrice) {
        this.SeatPrice = seatPrice;
        return this;
    }

    public int getTotalPrice() {
        return seats.size() * SeatPrice;
    }

    public String getOrderId() {
        return OrderId;
    }

    public PurchaseData setOrderId(String orderId) {
        this.OrderId = orderId;
        return this;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public PurchaseData addSeat(Seat seat) {
        seats.add(seat);
        return this;
    }

    public String toString() {
        return "PurchaseData{" +
                "MovieTitle='" + MovieTitle + '\'' +
                ", MovieDuration='" + MovieDuration + '\'' +
                ", ScreeningTime=" + ScreeningTime +
                ", SeatPrice=" + SeatPrice +
                ", OrderId='" + OrderId + '\'' +
                ", seats=" + seats +
                '}';
    }
}
