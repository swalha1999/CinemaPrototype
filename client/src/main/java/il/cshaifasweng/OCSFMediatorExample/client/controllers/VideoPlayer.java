package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VideoPlayer {

    @FXML private AnchorPane rootPane;  // AnchorPane from the FXML
    @FXML private Button muteButton;    // Button for muting the video

    private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    public void initialize() {
        // Register this controller with EventBus
        EventBus.getDefault().register(this);

        // Create a MediaView and add it to the AnchorPane
        mediaView = new MediaView();
        rootPane.getChildren().add(mediaView);

        // Adjust the MediaView to be larger
        mediaView.setFitHeight(600);
        mediaView.setFitWidth(800);

        AnchorPane.setTopAnchor(mediaView, 0.0);
        AnchorPane.setBottomAnchor(mediaView, 0.0);
        AnchorPane.setLeftAnchor(mediaView, 0.0);
        AnchorPane.setRightAnchor(mediaView, 0.0);

        // Start playing the video
        String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4";
        playVideo(videoUrl);

        // Configure the mute button action
        muteButton.setOnAction(event -> toggleMute());
    }

    private void playVideo(String videoUrl) {
        Media media = new Media(videoUrl); // Create a Media object from the URL
        mediaPlayer = new MediaPlayer(media); // Create a MediaPlayer for the media
        mediaView.setMediaPlayer(mediaPlayer); // Set the MediaPlayer to the MediaView

        // Play the video
        mediaPlayer.setAutoPlay(true);

        // Handle possible exceptions in the media loading
        mediaPlayer.setOnError(() -> {
            System.out.println("Error occurred while playing video: " + mediaPlayer.getError().getMessage());
        });
    }

    // Toggle mute/unmute on the video
    private void toggleMute() {
        if (mediaPlayer.isMute()) {
            mediaPlayer.setMute(false);
            muteButton.setText("Mute");
        } else {
            mediaPlayer.setMute(true);
            muteButton.setText("Unmute");
        }
    }

    @Subscribe
    public void getMovieDetails(ShowSideUIEvent event) {
        if (!event.getUIName().equals("VideoPlayer")) {
            return;
        }
    }

    public void dispose() {
        EventBus.getDefault().unregister(this);
    }
}
