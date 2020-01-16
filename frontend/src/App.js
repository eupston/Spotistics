import React, {Component} from 'react';
import './App.css';
import SearchArtist from "./components/SearchArtist/SearchArtist";
import Chart from "./components/Chart/Chart"
import Navbar from 'react-bootstrap/Navbar';

class App extends Component {

    state = {
            "artistName": null,
            "artistImageURL": "https://dummyimage.com/320X320/4bb6e6/4bb6e6",
            "topTracksAudioFeaturesMean": {
                "acousticness": 0.0,
                "danceability": 0.2,
                "energy": 0.3,
                "instrumentalness": 0.45,
                "liveness":  0.6,
                "valence": 0.8,
                "speechiness": 1.0
            },
            "spotifyTracks":[],
            "isLoading":false,
            "dropdown_options" : ["All Top Tracks"],
            "current_track_selected" : "All Top Tracks",
            "currently_selected_audiofeatures":{
                "acousticness": 0.0,
                "danceability": 0.2,
                "energy": 0.3,
                "instrumentalness": 0.45,
                "liveness":  0.6,
                "valence": 0.8,
                "speechiness": 1.0
            }
    };

    handleDropDownSelection = (eventKey) => {
        const selected_option = this.state.dropdown_options[eventKey];
        this.setState({current_track_selected:selected_option});
        var select_track_audio_features = {};
        if (selected_option === "All Top Tracks") {
            select_track_audio_features = this.state.topTracksAudioFeaturesMean;
        }
        else {
            this.state.spotifyTracks.forEach(function (track) {
                if (track.trackName === selected_option) {
                    select_track_audio_features = track.audioFeature;
                }
            });
        }
        this.setState({currently_selected_audiofeatures:select_track_audio_features});
    };

    handleGetArtistAudioInfo = (artist) => {
        this.setState({ isLoading: true }, () => {
            fetch('/api/artists/' + artist + "/toptracks/audiofeatures/mean")
                .then(function (response) {
                    if (response.ok) {
                        return response.json();
                    }
                    throw new Error('Something went wrong with request.');
                })
                .then(response => {
                    this.setState(response);
                    const trackNames = response.spotifyTracks.map(track => track.trackName);
                    trackNames.unshift("All Top Tracks");
                    this.setState({dropdown_options:trackNames});
                    this.setState({currently_selected_audiofeatures:response.topTracksAudioFeaturesMean});
                    this.setState({current_track_selected:["All Top Tracks"]});
                })
                .then(response => {
                    this.setState({"isLoading": false})
                })
                .catch(error => {
                    const defaultState = {
                        "artistName": "Artist Not Found",
                        "artistImageURL": "https://dummyimage.com/320X320/4bb6e6/4bb6e6",
                        "currently_selected_audiofeatures": {
                            "acousticness": 0,
                            "danceability": 0,
                            "energy": 0,
                            "instrumentalness": 0,
                            "liveness": 0,
                            "valence": 0,
                            "speechiness": 0
                        },
                        "topTracksAudioFeaturesMean": {
                            "acousticness": 0,
                            "danceability": 0,
                            "energy": 0,
                            "instrumentalness": 0,
                            "liveness": 0,
                            "valence": 0,
                            "speechiness": 0
                        },
                        "dropdown_options" : ["All Top Tracks"],
                        "current_track_selected" : "All Top Tracks"
                    };
                    this.setState(defaultState)
                    this.setState({"isLoading": false})
                    console.log("request failed", error)
                });
        })
    };


    render() {
            return (
            <div className="App" >
                <Navbar className="navbar" sticky="top"  bg="dark" variant="dark">
                    <img
                        src="eug_logo.png"
                        width="30"
                        height="30"
                        className="navbar-image"
                    />
                    <Navbar.Brand href="#home">Spotistics</Navbar.Brand>
                </Navbar>
                <header className="App-header">
                    <SearchArtist onGetArtistAudioInfo={this.handleGetArtistAudioInfo} artistInfo={this.state} />
                    <Chart data={this.state.currently_selected_audiofeatures}
                           xaxis_id="Audio Features"
                           dropdown_options={this.state.dropdown_options}
                           dropdown_selection_handler={this.handleDropDownSelection}
                           dropdown_current_selected={this.state.current_track_selected}
                    />
                </header>

            </div>
        );
    }
}

export default App;