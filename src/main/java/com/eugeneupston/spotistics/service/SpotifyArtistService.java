package com.eugeneupston.spotistics.service;

import com.eugeneupston.spotistics.entity.SpotifyArtist;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SpotifyArtistService {

    public List<SpotifyArtist> findAll();

    public SpotifyArtist findById(int theId);

    public SpotifyArtist findByName(String artistName);

    public void save(SpotifyArtist theSpotifyArtist);

    public void deleteById(int theId);

}
