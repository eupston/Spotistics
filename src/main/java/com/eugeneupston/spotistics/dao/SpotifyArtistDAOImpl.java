package com.eugeneupston.spotistics.dao;

import com.eugeneupston.spotistics.entity.SpotifyArtist;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SpotifyArtistDAOImpl implements SpotifyArtistDAO {

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public SpotifyArtistDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<SpotifyArtist> findAll() {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query
        Query<SpotifyArtist> theQuery = currentSession.createQuery("from SpotifyArtist", SpotifyArtist.class);

        //execute query and get result list
        List<SpotifyArtist> artists = theQuery.getResultList();

        //return the results
        return artists;
    }

    @Override
    public SpotifyArtist findById(int theId) {
        return null;
    }

    @Override
    public SpotifyArtist findByName(String artistName) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<SpotifyArtist> theQuery = currentSession.createQuery("from SpotifyArtist where artist_name=:name", SpotifyArtist.class);
        theQuery.setParameter("name", artistName);
        try {
            SpotifyArtist theArtist = theQuery.getSingleResult();
            return theArtist;
        }
        catch(Exception error){
            return null;
        }
    }

    @Override
    public void save(SpotifyArtist theSpotifyArtist) {
        Session currentSession = entityManager.unwrap(Session.class);
        //save/update the SpotifyArtist
        currentSession.saveOrUpdate(theSpotifyArtist);
    }

    @Override
    public void deleteById(int theId) {

    }
}
