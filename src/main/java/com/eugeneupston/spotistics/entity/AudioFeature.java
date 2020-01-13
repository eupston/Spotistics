package com.eugeneupston.spotistics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

public class AudioFeature {

    public String trackId;
    public String trackName;
    private float Acousticness;
    private float Danceability;
    private float Energy;
    private float Instrumentalness;
    private float Liveness;
    private float Valence;
    private float Speechiness;

    public AudioFeature() {
    }

    public AudioFeature(String trackId, String trackName, float acousticness, float danceability, float energy, float instrumentalness, float liveness, float valence, float speechiness) {
        this.trackId = trackId;
        this.trackName = trackName;
        Acousticness = acousticness;
        Danceability = danceability;
        Energy = energy;
        Instrumentalness = instrumentalness;
        Liveness = liveness;
        Valence = valence;
        Speechiness = speechiness;
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

    public float getAcousticness() {
        return Acousticness;
    }

    public void setAcousticness(float acousticness) {
        Acousticness = acousticness;
    }

    public float getDanceability() {
        return Danceability;
    }

    public void setDanceability(float danceability) {
        Danceability = danceability;
    }

    public float getEnergy() {
        return Energy;
    }

    public void setEnergy(float energy) {
        Energy = energy;
    }

    public float getInstrumentalness() {
        return Instrumentalness;
    }

    public void setInstrumentalness(float instrumentalness) {
        Instrumentalness = instrumentalness;
    }

    public float getLiveness() {
        return Liveness;
    }

    public void setLiveness(float liveness) {
        Liveness = liveness;
    }

    public float getValence() {
        return Valence;
    }

    public void setValence(float valence) {
        Valence = valence;
    }

    public float getSpeechiness() {
        return Speechiness;
    }

    public void setSpeechiness(float speechiness) {
        Speechiness = speechiness;
    }


}
