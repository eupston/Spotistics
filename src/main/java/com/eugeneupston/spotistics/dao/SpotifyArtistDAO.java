package com.eugeneupston.spotistics.dao;

import com.eugeneupston.spotistics.entity.SpotifyArtist;

import java.util.List;

public interface SpotifyArtistDAO {

    public List<SpotifyArtist> findAll();

    public SpotifyArtist findById(int theId);

    public SpotifyArtist findByName(String artistName);

    public void save(SpotifyArtist theSpotifyArtist);

    public void deleteById(int theId);
}
