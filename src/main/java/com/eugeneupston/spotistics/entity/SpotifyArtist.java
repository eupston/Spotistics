package com.eugeneupston.spotistics.entity;

import javax.persistence.*;

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
    private TopTracksAudioFeaturesMean topTracksAudioFeaturesMean;

    public SpotifyArtist() {
    }

    public SpotifyArtist(String artistName, String artistImageURL) {
        this.artistName = artistName;
        this.artistImageURL = artistImageURL;
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

    public TopTracksAudioFeaturesMean getTopTracksAudioFeaturesMean() {
        return topTracksAudioFeaturesMean;
    }

    public void setTopTracksAudioFeaturesMean(TopTracksAudioFeaturesMean topTracksAudioFeaturesMean) {
        this.topTracksAudioFeaturesMean = topTracksAudioFeaturesMean;
    }

    @Override
    public String toString() {
        return "SpotifyArtist{" +
                "id=" + id +
                ", artistName='" + artistName + '\'' +
                ", artistImageURL='" + artistImageURL + '\'' +
                ", topTracksAudioFeaturesMean=" + topTracksAudioFeaturesMean +
                '}';
    }
}
