# Spotistics
A Springboot/React Web App for viewing Spotify statistics

### Welcome to the Spotistics repository!
Spotistics is a web app for viewing artist spotify statistics. At present you can view audio features for any spotify artist for any of their top tracks.

You can visit the Web App here : https://spotistics.herokuapp.com/

![](/frontend/src/assets/images/spotistics_screenshot.png)

### Technology Stack

- Spring Boot
- Spring Rest
- JDBC
- Hibernate
- Postgres Database
- Heroku CI/CD
- React js

### How it Works

#### Backend
1. Spotistics uses a spotify API wrapper to make requests to Spotify's web api. 
2. The backend is setup so that it first searches the Heroku Postgres Database for the given artist, and if the artist is not found it will attempt to find that artist through the Spotify web api. 
3. If an artist is found through the web api it will then store that artist and they're metadata in the database for faster queries.
4. Spring Rest API Endpoints include: 
    - All artists
    - Artists top tracks
    - Track Audio Features
    - Top Tracks Audio Features mean
    
#### Frontend
1. User can search any artist through the Form Element.
2. On the Form Element Submitation a request goes out to the backend for that given artist and all their top track's audio features.
3. If that artist is found it then parses the json response and populates the react state with the full list of artist info and top track's data. This is so a request only has to be made once during an artist search. 
4. The graph component will intially be populated with the top track's audio features.
4. A user can then browser through the various track's audio features for a responsive graph update.
