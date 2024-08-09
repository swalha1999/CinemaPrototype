package il.cshaifasweng.OCSFMediatorExample.client.events;

public class ShowSideUIEvent {
    private String UIName;
    private int movieId;

    public ShowSideUIEvent(String UIName) {
        this.UIName = UIName;
    }

    public ShowSideUIEvent(String UIName, int movieId) {
        this.UIName = UIName;
        this.movieId = movieId;
    }

    public String getUIName() {
        return UIName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setUIName(String UIName) {
        this.UIName = UIName;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String toString() {
        return "ShowSideUIEvent{" +
                "UIName='" + UIName + '\'' +
                ", movieId='" + movieId + '\'' +
                '}';
    }

}
