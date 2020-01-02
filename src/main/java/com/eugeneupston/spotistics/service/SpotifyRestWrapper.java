package com.eugeneupston.spotistics.service;

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
import java.util.HashMap;
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

    public HashMap meanArtistsTopTracksAudioFeatures(List<AudioFeatures> allAudioFeatures) {
        HashMap meanAudioFeatures = new HashMap();
        // sum all features
        for (AudioFeatures features : allAudioFeatures) {
            float Acousticness = features.getAcousticness();
            float Danceability = features.getDanceability();
            float Energy = features.getEnergy();
            float Instrumentalness = features.getInstrumentalness();
            float Liveness = features.getLiveness();
            float Valence = features.getValence();
            float Speechiness = features.getSpeechiness();
            float DurationMs = features.getDurationMs();
            float Key = features.getKey();
            float Loudness = features.getLoudness();
            float Tempo = features.getTempo();
            if(!meanAudioFeatures.containsKey("Acousticness")){
                meanAudioFeatures.put("Acousticness", Acousticness);
            }
            else{
                float oldvalue = (float) meanAudioFeatures.get("Acousticness");
                meanAudioFeatures.put("Acousticness", Acousticness + oldvalue);
            }
            if(!meanAudioFeatures.containsKey("Danceability")){
                meanAudioFeatures.put("Danceability", Danceability);
            }
            else{
                float oldvalue = (float) meanAudioFeatures.get("Danceability");
                meanAudioFeatures.put("Danceability", Danceability + oldvalue);
            }

            if(!meanAudioFeatures.containsKey("Energy")){
                meanAudioFeatures.put("Energy", Energy);
            }
            else{
                float oldvalue = (float) meanAudioFeatures.get("Energy");
                meanAudioFeatures.put("Energy", Energy + oldvalue);
            }
            if(!meanAudioFeatures.containsKey("Instrumentalness")){
                meanAudioFeatures.put("Instrumentalness", Instrumentalness);
            }
            else{
                float oldvalue = (float) meanAudioFeatures.get("Instrumentalness");
                meanAudioFeatures.put("Instrumentalness", Instrumentalness + oldvalue);
            }

            if(!meanAudioFeatures.containsKey("Liveness")){
                meanAudioFeatures.put("Liveness", Liveness);
            }
            else{
                float oldvalue = (float) meanAudioFeatures.get("Liveness");
                meanAudioFeatures.put("Liveness", Liveness + oldvalue);
            }
            if(!meanAudioFeatures.containsKey("Valence")){
                meanAudioFeatures.put("Valence", Valence);
            }
            else{
                float oldvalue = (float) meanAudioFeatures.get("Valence");
                meanAudioFeatures.put("Valence", Valence + oldvalue);
            }
            if(!meanAudioFeatures.containsKey("Speechiness")){
                meanAudioFeatures.put("Speechiness", Speechiness);
            }
            else{
                float oldvalue = (float) meanAudioFeatures.get("Speechiness");
                meanAudioFeatures.put("Speechiness", Speechiness + oldvalue);
            }

            //            if(!meanAudioFeatures.containsKey("DurationMs")){
//                meanAudioFeatures.put("DurationMs", DurationMs);
//            }
//            else{
//                float oldvalue = (float) meanAudioFeatures.get("DurationMs");
//                meanAudioFeatures.put("DurationMs", DurationMs + oldvalue);
//            }

            //            if(!meanAudioFeatures.containsKey("Key")){
//                meanAudioFeatures.put("Key", Key);
//            }
//            else{
//                float oldvalue = (float) meanAudioFeatures.get("Key");
//                meanAudioFeatures.put("Key", Key + oldvalue);
//            }
//            if(!meanAudioFeatures.containsKey("Loudness")){
//                meanAudioFeatures.put("Loudness", Loudness);
//            }
//            else{
//                float oldvalue = (float) meanAudioFeatures.get("Loudness");
//                meanAudioFeatures.put("Loudness", Loudness + oldvalue);
//            }

//            if(!meanAudioFeatures.containsKey("Tempo")){
//                meanAudioFeatures.put("Tempo", Tempo);
//            }
//            else{
//                float oldvalue = (float) meanAudioFeatures.get("Tempo");
//                meanAudioFeatures.put("Tempo", Tempo + oldvalue);
//            }


        }
        // calculate mean for each feature
        for(Object key : meanAudioFeatures.keySet()){
            meanAudioFeatures.put(key, ((float) meanAudioFeatures.get(key))/allAudioFeatures.size());

        }
        return meanAudioFeatures;
    }
}
