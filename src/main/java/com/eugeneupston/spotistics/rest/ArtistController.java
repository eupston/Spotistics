package com.eugeneupston.spotistics.rest;

import com.eugeneupston.spotistics.service.SpotifyRestWrapper;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.model_objects.specification.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Value("${spotify.clientId}")
    private String clientId;
    @Value("${spotify.clientSecret}")
    private String clientSecret;
    private SpotifyRestWrapper mySpotifyRestWrapper;

    @PostConstruct
    public void initialize() {
        mySpotifyRestWrapper = new SpotifyRestWrapper(clientId, clientSecret);
    }

    @GetMapping("/{artistName}")
    public Artist getArtist(@PathVariable String artistName){
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        return firstArtistFound;
    }


    @GetMapping("/{artistName}/toptracks")
    public Track[] getArtistsTopTracks(@PathVariable String artistName) {
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        String artistId = firstArtistFound.getId();
        Track[] tracks = mySpotifyRestWrapper.getArtistsTopTracks(artistId);
        return tracks;
    }

    @GetMapping("/{artistName}/toptracks/audiofeatures")
    public List<AudioFeatures> getArtistsTopTracksAudioFeatures(@PathVariable String artistName) {
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        String artistId = firstArtistFound.getId();
        Track[] tracks = mySpotifyRestWrapper.getArtistsTopTracks(artistId);
        List<AudioFeatures> allAudioFeatures = mySpotifyRestWrapper.getArtistsTopTracksAudioFeatures(tracks);
        return allAudioFeatures;
    }

    @GetMapping("/{artistName}/toptracks/audiofeatures/mean")
    public HashMap meanArtistsTopTracksAudioFeatures(@PathVariable String artistName) {
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        String artistId = firstArtistFound.getId();
        Track[] tracks = mySpotifyRestWrapper.getArtistsTopTracks(artistId);
        List<AudioFeatures> allAudioFeatures = mySpotifyRestWrapper.getArtistsTopTracksAudioFeatures(tracks);
        HashMap meanAudioFeatures = mySpotifyRestWrapper.meanArtistsTopTracksAudioFeatures(allAudioFeatures);
        return meanAudioFeatures;
    }


}
