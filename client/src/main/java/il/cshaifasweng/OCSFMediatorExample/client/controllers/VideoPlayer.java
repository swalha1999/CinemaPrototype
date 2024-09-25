package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VideoPlayer {

    // Reference to the root AnchorPane from the FXML
    @FXML AnchorPane rootPane;

    private MediaView mediaView;

    public void initialize() {
        // Register this controller with EventBus
        EventBus.getDefault().register(this);

        // Create a MediaView and add it to the AnchorPane
        mediaView = new MediaView();
        rootPane.getChildren().add(mediaView);
        AnchorPane.setTopAnchor(mediaView, 0.0);
        AnchorPane.setBottomAnchor(mediaView, 0.0);
        AnchorPane.setLeftAnchor(mediaView, 0.0);
        AnchorPane.setRightAnchor(mediaView, 0.0);

        // Start playing the video when the controller is initialized
        String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4";
        playVideo(videoUrl);
    }

    private void playVideo(String videoUrl) {
        Media media = new Media(videoUrl); // Create a Media object from the URL
        MediaPlayer mediaPlayer = new MediaPlayer(media); // Create a MediaPlayer for the media
        mediaView.setMediaPlayer(mediaPlayer); // Set the MediaPlayer to the MediaView

        // Play the video
        mediaPlayer.setAutoPlay(true);

        // Handle possible exceptions in the media loading
        mediaPlayer.setOnError(() -> {
            System.out.println("Error occurred while playing video: " + mediaPlayer.getError().getMessage());
        });
    }

    @Subscribe
    public void getMovieDetails(ShowSideUIEvent event) {
        // Make sure this event is for this controller
        if (!event.getUIName().equals("VideoPlayer")) {
            return;
        }
    }

    public void dispose() {
        // Unregister from EventBus when no longer needed
        EventBus.getDefault().unregister(this);
    }
}
