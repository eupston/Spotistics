import React, {Component} from 'react';
import './App.css';
import EugLogo from './assets/Logo/eug_logo.png';
import GitHubIcon from './assets/images/GitHub-Mark-Light-64px.png';
import SearchArtist from "./components/SearchArtist/SearchArtist";
import Chart from "./components/Chart/Chart"
import Navbar from 'react-bootstrap/Navbar';

class App extends Component {

    constructor() {
        super();
        window.addEventListener("resize", this.updateWindowSizeState);
        document.addEventListener("keydown", this.handleEnterKeyDown.bind(this));
    }

    componentDidMount() {
        this.updateWindowSizeState();
    }

    state = {
            "artistName": null,
            "artistImageURL": null,
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
                "acousticness": 0.1,
                "danceability": 0.2,
                "energy": 0.3,
                "instrumentalness": 0.45,
                "liveness":  0.6,
                "valence": 0.8,
                "speechiness": 1.0
            },
            "window_height": 0,
            "window_width": 0,
            "min_mobile_display_width": 925,
            "audioFeatureDescriptions" :{
                acousticness : "A confidence measure from 0.0 to 1.0 of whether the track is acoustic. 1.0 represents high confidence the track is acoustic.",
                danceability : "Danceability describes how suitable a track is for dancing based on a combination of musical elements including tempo, rhythm stability, beat strength, and overall regularity. A value of 0.0 is least danceable and 1.0 is most danceable.",
                energy : "Energy is a measure from 0.0 to 1.0 and represents a perceptual measure of intensity and activity. Typically, energetic tracks feel fast, loud, and noisy. For example, death metal has high energy, while a Bach prelude scores low on the scale. Perceptual features contributing to this attribute include dynamic range, perceived loudness, timbre, onset rate, and general entropy.",
                instrumentalness :"Predicts whether a track contains no vocals. “Ooh” and “aah” sounds are treated as instrumental in this context. Rap or spoken word tracks are clearly “vocal”. The closer the instrumentalness value is to 1.0, the greater likelihood the track contains no vocal content. Values above 0.5 are intended to represent instrumental tracks, but confidence is higher as the value approaches 1.0.",
                liveness :"Detects the presence of an audience in the recording. Higher liveness values represent an increased probability that the track was performed live. A value above 0.8 provides strong likelihood that the track is live.",
                valence :"A measure from 0.0 to 1.0 describing the musical positiveness conveyed by a track. Tracks with high valence sound more positive (e.g. happy, cheerful, euphoric), while tracks with low valence sound more negative (e.g. sad, depressed, angry). ",
                speechiness :"Speechiness detects the presence of spoken words in a track. The more exclusively speech-like the recording (e.g. talk show, audio book, poetry), the closer to 1.0 the attribute value. Values above 0.66 describe tracks that are probably made entirely of spoken words. Values between 0.33 and 0.66 describe tracks that may contain both music and speech, either in sections or layered, including such cases as rap music. Values below 0.33 most likely represent music and other non-speech-like tracks. ",
            }
    };

    updateWindowSizeState = () => {
        this.setState({
            window_height: window.innerHeight,
            window_width: window.innerWidth
        });
    };

    handleEnterKeyDown(e) {
        if ( e.which === 13) {
            const artistSearchButton = document.getElementById("artistSearchButton");
            const searchForm = document.getElementById("formSearchArtist");
            searchForm.blur();
            artistSearchButton.click();
        }
    }

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
                        "artistImageURL": null,
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
                <Navbar className="navbar"  bg="dark" variant="dark">
                    <Navbar.Brand href="https://www.eugeneupston.com/">
                    <img
                        src={EugLogo}
                        width="30"
                        height="30"
                    />
                    </Navbar.Brand>

                    <Navbar.Brand href="#home">Spotistics</Navbar.Brand>
                    <Navbar.Brand href="https://github.com/eupston"  className="ml-auto">
                        <img
                            alt=""
                            src={GitHubIcon}
                            width="32"
                            height="32"
                        />
                    </Navbar.Brand>

                </Navbar>
                <div className="App-body">
                    <SearchArtist
                        onGetArtistAudioInfo={this.handleGetArtistAudioInfo}
                        artistInfo={this.state}
                    />
                    <Chart data={this.state.currently_selected_audiofeatures}
                           xaxis_id="Audio Features"
                           xaxis_tilt = {this.state.window_width < this.state.min_mobile_display_width ? -45 : 0}
                           bottom_margin = {this.state.window_width < this.state.min_mobile_display_width ? 100 : 50}
                           legend_bottom_offset = {this.state.window_width < this.state.min_mobile_display_width ? 95 : 45}
                           dropdown_options={this.state.dropdown_options}
                           dropdown_selection_handler={this.handleDropDownSelection}
                           dropdown_current_selected={this.state.current_track_selected}
                           tooltips={this.state.audioFeatureDescriptions}
                    />
                </div>
            </div>
        );
    }
}

export default App;