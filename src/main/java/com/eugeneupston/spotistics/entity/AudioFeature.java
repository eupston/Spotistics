package com.eugeneupston.spotistics.entity;

import javax.persistence.*;

@Entity
@Table(name="audio_features")
public class AudioFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="acousticness")
    private float Acousticness;

    @Column(name="danceability")
    private float Danceability;

    @Column(name="energy")
    private float Energy;

    @Column(name="instrumentalness")
    private float Instrumentalness;

    @Column(name="liveness")
    private float Liveness;

    @Column(name="valence")
    private float Valence;

    @Column(name="speechiness")
    private float Speechiness;
    public AudioFeature() {
    }

    public AudioFeature(float acousticness, float danceability, float energy, float instrumentalness, float liveness, float valence, float speechiness) {
        Acousticness = acousticness;
        Danceability = danceability;
        Energy = energy;
        Instrumentalness = instrumentalness;
        Liveness = liveness;
        Valence = valence;
        Speechiness = speechiness;
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

    @Override
    public String toString() {
        return "AudioFeature{" +
                "id=" + id +
                ", Acousticness=" + Acousticness +
                ", Danceability=" + Danceability +
                ", Energy=" + Energy +
                ", Instrumentalness=" + Instrumentalness +
                ", Liveness=" + Liveness +
                ", Valence=" + Valence +
                ", Speechiness=" + Speechiness +
                '}';
    }
}
