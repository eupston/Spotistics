package com.eugeneupston.spotistics.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="spotifytracks")
public class SpotifyTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="track_id")
    private String trackId;

    @Column(name="track_name")
    private String trackName;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="artist_id")
    private SpotifyArtist spotifyArtist;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="audio_features_id")
    private AudioFeature audioFeature;

    public SpotifyTrack() {
    }

    public SpotifyTrack(String trackId, String trackName, SpotifyArtist spotifyArtist, AudioFeature audioFeature) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.spotifyArtist = spotifyArtist;
        this.audioFeature = audioFeature;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public AudioFeature getAudioFeature() {
        return audioFeature;
    }

    public void setAudioFeature(AudioFeature audioFeature) {
        this.audioFeature = audioFeature;
    }

    public SpotifyArtist getSpotifyArtist() {
        return spotifyArtist;
    }

    public void setSpotifyArtist(SpotifyArtist spotifyArtist) {
        this.spotifyArtist = spotifyArtist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SpotifyTrack{" +
                ", trackId='" + trackId + '\'' +
                ", trackName='" + trackName + '\'' +
                ", audioFeature=" + audioFeature +
                '}';
    }
}
