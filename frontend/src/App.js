import React, {Component} from 'react';
import './App.css';
import Graph from "./components/Graph/Graph";
import SearchArtist from "./components/SearchArtist/SearchArtist";
import Chart from "./components/Chart/Chart"
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

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
            "isLoading":false,
    };

    componentDidMount() {
    }


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
                    this.setState(response)
                })
                .then(response => {
                    this.setState({"isLoading": false})
                })
                .catch(error => {
                    const defaultState = {
                        "artistName": "Artist Not Found",
                        "artistImageURL": "https://dummyimage.com/320X320/4bb6e6/4bb6e6",
                        "topTracksAudioFeaturesMean": {
                            "acousticness": 0,
                            "danceability": 0,
                            "energy": 0,
                            "instrumentalness": 0,
                            "liveness": 0,
                            "valence": 0,
                            "speechiness": 0
                        }
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
                    <Nav className="mr-auto">
                        <Nav.Link href="#home">Home</Nav.Link>
                    </Nav>
                </Navbar>
                <header className="App-header">
                    <SearchArtist onGetArtistAudioInfo={this.handleGetArtistAudioInfo} artistInfo={this.state} />
                    <Chart  data={this.state.topTracksAudioFeaturesMean} xaxis_id="Audio Features" />
                </header>
            </div>
        );
    }
}

export default App;