package com.eugeneupston.spotistics.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="artist")
public class SpotifyArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="artist_name")
    private String artistName;

    @Column(name="artist_image")
    private String artistImageURL;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="top_tracks_audio_features_mean_id")
    private AudioFeature topTracksAudioFeaturesMean;

    @JsonManagedReference
    @OneToMany(mappedBy = "spotifyArtist", cascade = CascadeType.ALL)
    private List<SpotifyTrack> spotifyTracks;

    public SpotifyArtist() {
    }

    public SpotifyArtist(String artistName, String artistImageURL) {
        this.artistName = artistName;
        this.artistImageURL = artistImageURL;
    }

    public SpotifyArtist(String artistName, String artistImageURL, AudioFeature topTracksAudioFeaturesMean, List<SpotifyTrack> spotifyTracks) {
        this.artistName = artistName;
        this.artistImageURL = artistImageURL;
        this.topTracksAudioFeaturesMean = topTracksAudioFeaturesMean;
        this.spotifyTracks = spotifyTracks;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImageURL() {
        return artistImageURL;
    }

    public void setArtistImageURL(String artistImageURL) {
        this.artistImageURL = artistImageURL;
    }

    public AudioFeature getTopTracksAudioFeaturesMean() {
        return topTracksAudioFeaturesMean;
    }

    public void setTopTracksAudioFeaturesMean(AudioFeature topTracksAudioFeaturesMean) {
        this.topTracksAudioFeaturesMean = topTracksAudioFeaturesMean;
    }

    public List<SpotifyTrack> getSpotifyTracks() {
        return spotifyTracks;
    }

    public void setSpotifyTracks(List<SpotifyTrack> spotifyTracks) {
        this.spotifyTracks = spotifyTracks;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SpotifyArtist{" +
                "id=" + id +
                ", artistName='" + artistName + '\'' +
                ", artistImageURL='" + artistImageURL + '\'' +
                ", topTracksAudioFeaturesMean=" + topTracksAudioFeaturesMean +
                ", spotifyTracks=" + spotifyTracks +
                '}';
    }
}
