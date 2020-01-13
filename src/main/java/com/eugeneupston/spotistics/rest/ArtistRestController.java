package com.eugeneupston.spotistics.rest;

import com.eugeneupston.spotistics.entity.AudioFeature;
import com.eugeneupston.spotistics.entity.SpotifyArtist;
import com.eugeneupston.spotistics.service.SpotifyArtistService;
import com.eugeneupston.spotistics.wrapper.SpotifyRestWrapper;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.model_objects.specification.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistRestController {

    @Value("${spotify.clientId}")
    private String clientId;
    @Value("${spotify.clientSecret}")
    private String clientSecret;

    private SpotifyRestWrapper mySpotifyRestWrapper;
    private SpotifyArtistService mySpotifyArtistService;

    @Autowired
    public ArtistRestController(SpotifyArtistService theSpotifyArtistService){
        mySpotifyArtistService = theSpotifyArtistService;
    }

    @PostConstruct
    public void initialize() {
        mySpotifyRestWrapper = new SpotifyRestWrapper(clientId, clientSecret);
    }

    @GetMapping("/")
    public List<SpotifyArtist> getAllArists(){
        List<SpotifyArtist> allArtists = mySpotifyArtistService.findAll();
        return allArtists;
    }

    @GetMapping("/{artistName}")
    public SpotifyArtist getArtist(@PathVariable String artistName){
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        String artist_name = firstArtistFound.getName().toLowerCase();
        String artist_img = firstArtistFound.getImages()[0].getUrl();
        SpotifyArtist theArtist = mySpotifyArtistService.findByName(artist_name);
        //if not in database yet save to database
        if(theArtist == null){
            theArtist = new SpotifyArtist(artist_name, artist_img);
            mySpotifyArtistService.save(theArtist);
        }
        return theArtist;
    }

    //TODO implement in DATABASE
    @GetMapping("/{artistName}/toptracks")
    public Track[] getArtistsTopTracks(@PathVariable String artistName) {
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        String artistId = firstArtistFound.getId();
        Track[] tracks = mySpotifyRestWrapper.getArtistsTopTracks(artistId);
        return tracks;
    }

    //TODO implement in DATABASE
    @GetMapping("/{artistName}/toptracks/audiofeatures")
    public List<AudioFeature> getArtistsTopTracksAudioFeatures(@PathVariable String artistName) {
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        String artistId = firstArtistFound.getId();
        Track[] tracks = mySpotifyRestWrapper.getArtistsTopTracks(artistId);
        List<AudioFeature> allAudioFeatures = mySpotifyRestWrapper.getArtistsTopTracksAudioFeatures(tracks);
        return allAudioFeatures;
    }

    @GetMapping("/{artistName}/toptracks/audiofeatures/mean")
    public SpotifyArtist meanArtistsTopTracksAudioFeatures(@PathVariable String artistName) {
        mySpotifyRestWrapper.clientCredentialsSync();
        Artist firstArtistFound = mySpotifyRestWrapper.searchArtist(artistName);
        String artist_name = firstArtistFound.getName().toLowerCase();
        String artist_img = firstArtistFound.getImages()[0].getUrl();
        SpotifyArtist theArtist = mySpotifyArtistService.findByName(artist_name);
        //if not in database yet calculate audio features and save to database
        if(theArtist == null) {
            SpotifyArtist theSpotifyArtist = new SpotifyArtist(artist_name, artist_img);
            theArtist = mySpotifyRestWrapper.meanArtistsTopTracksAudioFeatures(firstArtistFound, theSpotifyArtist);
            mySpotifyArtistService.save(theArtist);
        }
        //else check if audio features have been calculated yet
        else if(theArtist.getTopTracksAudioFeaturesMean() == null){
            theArtist = mySpotifyRestWrapper.meanArtistsTopTracksAudioFeatures(firstArtistFound, theArtist);
            mySpotifyArtistService.save(theArtist);
        }

        return theArtist;
    }

}
