package il.cshaifasweng.OCSFMediatorExample.client.data;

import java.time.LocalDate;

public class PurchaseData {
    private String MovieTitle;
    private String MovieDuration;
    private LocalDate ScreeningTime;
    private int SeatNumber;
    private int SeatPrice;
    private int TotalPrice;
    private String OrderId;
    private int ticketsNumber;

    // Getter and Setter for MovieTitle
    public String getMovieTitle() {
        return MovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.MovieTitle = movieTitle;
    }

    // Getter and Setter for MovieDuration
    public String getMovieDuration() {
        return MovieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        this.MovieDuration = movieDuration;
    }

    // Getter and Setter for ScreeningTime
    public LocalDate getScreeningTime() {
        return ScreeningTime;
    }

    public void setScreeningTime(LocalDate screeningTime) {
        this.ScreeningTime = screeningTime;
    }

    // Getter and Setter for SeatNumber
    public int getSeatNumber() {
        return SeatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.SeatNumber = seatNumber;
    }

    // Getter and Setter for SeatPrice
    public int getSeatPrice() {
        return SeatPrice;
    }

    public void setSeatPrice(int seatPrice) {
        this.SeatPrice = seatPrice;
    }

    // Getter and Setter for TotalPrice
    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.TotalPrice = totalPrice;
    }

    // Getter and Setter for OrderId
    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    // Getter and Setter for ticketsNumber
    public int getTicketsNumber() {
        return ticketsNumber;
    }

    public void setTicketsNumber(int ticketsNumber) {
        this.ticketsNumber = ticketsNumber;
    }
}
