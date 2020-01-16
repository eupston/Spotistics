package com.eugeneupston.spotistics.wrapper;

import com.eugeneupston.spotistics.entity.AudioFeature;
import com.eugeneupston.spotistics.entity.SpotifyArtist;
import com.eugeneupston.spotistics.entity.SpotifyTrack;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import com.wrapper.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpotifyRestWrapper {

    private SpotifyApi spotifyApi;
    private ClientCredentialsRequest clientCredentialsRequest;

    public SpotifyRestWrapper(String clientId, String clientSecret){
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();
    }

    public void clientCredentialsSync() {
        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            System.out.println("Access token: " + spotifyApi.getAccessToken());

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Artist searchArtist(String artistName) {
        Artist result;
        String type = ModelObjectType.ARTIST.getType();
        SearchItemRequest searchItemRequest = spotifyApi.searchItem(artistName, type).build();
        try {
            SearchResult searchResult = searchItemRequest.execute();
            Artist firstArtistFound = searchResult.getArtists().getItems()[0];
            System.out.println("Artist Name: " + firstArtistFound.getName());
            System.out.println("Artist ID: " + firstArtistFound.getId());
            result = firstArtistFound;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
            result = null;
        }
        return result;
    }


    public Track[] getArtistsTopTracks(String artistId) {
        CountryCode countryCode = CountryCode.US; //temporary
        Track[] result;
        GetArtistsTopTracksRequest getArtistsTopTracksRequest = spotifyApi
                .getArtistsTopTracks(artistId, countryCode)
                .build();
        try {
            Track[] tracks = getArtistsTopTracksRequest.execute();
            System.out.println("Total tracks Length: " + tracks.length);
            result = tracks;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            result = null;
        }
        return result;
    }



    public List<SpotifyTrack> getArtistsTopTracksAudioFeatures(Track[] artistTracks, SpotifyArtist theSpotifyArtist) {
        List<SpotifyTrack> tracks = new ArrayList<SpotifyTrack>();
        for(Track track : artistTracks){
            String currentId = track.getId();
            String currentTrackName = track.getName();

            GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyApi
                    .getAudioFeaturesForTrack(currentId)
                    .build();
            try {

                AudioFeatures audioFeatures = getAudioFeaturesForTrackRequest.execute();
                AudioFeature currentAudioFeature = new AudioFeature(audioFeatures.getAcousticness(),
                                                                    audioFeatures.getDanceability(),
                                                                    audioFeatures.getEnergy(),
                                                                    audioFeatures.getInstrumentalness(),
                                                                    audioFeatures.getLiveness(),
                                                                    audioFeatures.getValence(),
                                                                    audioFeatures.getSpeechiness());
                SpotifyTrack currentTrack = new SpotifyTrack(currentId, currentTrackName, theSpotifyArtist, currentAudioFeature);
                tracks.add(currentTrack);

            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
        return tracks;
    }

    public SpotifyArtist meanArtistsTopTracksAudioFeatures(Artist theArtist, SpotifyArtist theSpotifyArtist) {
        String artistId = theArtist.getId();
        Track[] tracks = getArtistsTopTracks(artistId);
        List<SpotifyTrack> allTracks = getArtistsTopTracksAudioFeatures(tracks, theSpotifyArtist);
        AudioFeature allAudioFeaturesMean = new AudioFeature();
        for (SpotifyTrack track : allTracks) {
            AudioFeature features = track.getAudioFeature();
            allAudioFeaturesMean.setAcousticness(allAudioFeaturesMean.getAcousticness() + features.getAcousticness()/allTracks.size());
            allAudioFeaturesMean.setDanceability(allAudioFeaturesMean.getDanceability() + features.getDanceability()/allTracks.size());
            allAudioFeaturesMean.setEnergy(allAudioFeaturesMean.getEnergy() + features.getEnergy()/allTracks.size());
            allAudioFeaturesMean.setInstrumentalness(allAudioFeaturesMean.getInstrumentalness() + features.getInstrumentalness()/allTracks.size());
            allAudioFeaturesMean.setLiveness(allAudioFeaturesMean.getLiveness() + features.getLiveness()/allTracks.size());
            allAudioFeaturesMean.setValence(allAudioFeaturesMean.getValence() + features.getValence()/allTracks.size());
            allAudioFeaturesMean.setSpeechiness(allAudioFeaturesMean.getSpeechiness() + features.getSpeechiness()/allTracks.size());
        }
        theSpotifyArtist.setTopTracksAudioFeaturesMean(allAudioFeaturesMean);
        theSpotifyArtist.setSpotifyTracks(allTracks);
        return theSpotifyArtist;
    }
}
