package com.eugeneupston.spotistics.service;

import com.eugeneupston.spotistics.dao.SpotifyArtistDAO;
import com.eugeneupston.spotistics.entity.SpotifyArtist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpotifyArtistServiceImpl implements SpotifyArtistService {

    private SpotifyArtistDAO mySpotifyArtistDAO;

    @Autowired
    public SpotifyArtistServiceImpl(SpotifyArtistDAO theSpotifyArtistDAO){
        mySpotifyArtistDAO = theSpotifyArtistDAO;
    }

    @Override
    @Transactional
    public List<SpotifyArtist> findAll() {
        return mySpotifyArtistDAO.findAll();
    }

    @Override
    @Transactional
    public SpotifyArtist findById(int theId) {
        return mySpotifyArtistDAO.findById(theId);
    }

    @Override
    @Transactional
    public SpotifyArtist findByName(String artistName) {
        return mySpotifyArtistDAO.findByName(artistName);
    }

    @Override
    @Transactional
    public void save(SpotifyArtist theSpotifyArtist) {
        mySpotifyArtistDAO.save(theSpotifyArtist);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        mySpotifyArtistDAO.deleteById(theId);
    }
}
