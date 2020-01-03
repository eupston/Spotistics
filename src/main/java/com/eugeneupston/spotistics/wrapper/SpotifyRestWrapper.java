package com.eugeneupston.spotistics.wrapper;

import com.eugeneupston.spotistics.entity.SpotifyArtist;
import com.eugeneupston.spotistics.entity.TopTracksAudioFeaturesMean;
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



    public List<AudioFeatures> getArtistsTopTracksAudioFeatures(Track[] artistTracks) {
        List<AudioFeatures> allAudioFeatures = new ArrayList<AudioFeatures>();

        for(Track track : artistTracks){
            String currentId = track.getId();

            GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyApi
                    .getAudioFeaturesForTrack(currentId)
                    .build();
            try {
                AudioFeatures audioFeatures = getAudioFeaturesForTrackRequest.execute();
                allAudioFeatures.add(audioFeatures);

            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
        return allAudioFeatures;
    }

    public SpotifyArtist meanArtistsTopTracksAudioFeatures(Artist theArtist, SpotifyArtist theSpotifyArtist) {
        String artistId = theArtist.getId();
        Track[] tracks = getArtistsTopTracks(artistId);
        List<AudioFeatures> allAudioFeatures = getArtistsTopTracksAudioFeatures(tracks);
        TopTracksAudioFeaturesMean allAudioFeaturesMean = new TopTracksAudioFeaturesMean();
        for (AudioFeatures features : allAudioFeatures) {
            allAudioFeaturesMean.setAcousticness(allAudioFeaturesMean.getAcousticness() + features.getAcousticness()/allAudioFeatures.size());
            allAudioFeaturesMean.setDanceability(allAudioFeaturesMean.getDanceability() + features.getDanceability()/allAudioFeatures.size());
            allAudioFeaturesMean.setEnergy(allAudioFeaturesMean.getEnergy() + features.getEnergy()/allAudioFeatures.size());
            allAudioFeaturesMean.setInstrumentalness(allAudioFeaturesMean.getInstrumentalness() + features.getInstrumentalness()/allAudioFeatures.size());
            allAudioFeaturesMean.setLiveness(allAudioFeaturesMean.getLiveness() + features.getLiveness()/allAudioFeatures.size());
            allAudioFeaturesMean.setValence(allAudioFeaturesMean.getValence() + features.getValence()/allAudioFeatures.size());
            allAudioFeaturesMean.setSpeechiness(allAudioFeaturesMean.getSpeechiness() + features.getSpeechiness()/allAudioFeatures.size());
        }
        theSpotifyArtist.setTopTracksAudioFeaturesMean(allAudioFeaturesMean);
        return theSpotifyArtist;
    }
}
