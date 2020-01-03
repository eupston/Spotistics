import React, {Component} from 'react';
import './App.css';
import Graph from "./components/Graph";
import SearchArtist from "./components/SearchArtist";

class App extends Component {

    state = {
        // "artistStats":{
        //                 "artistName": null,
        //                 "artistImageURL":"https://dummyimage.com/320X320/4bb6e6/4bb6e6"
        //                 },
        // "audiofeatures":{
        //     "topTracksAudioFeaturesMean":{
        //             "Acousticness": 0,
        //             "Danceability": 0,
        //             "Valence":0,
        //             "Energy": 0,
        //             "Speechiness": 0,
        //             "Liveness": 0,
        //             "Instrumentalness": 0
        //     }
        // }

            "artistName": null,
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

    componentDidMount() {
    }


    handleGetArtistAudioInfo = (artist) => {
        fetch('/api/artists/' + artist + "/toptracks/audiofeatures/mean")
            .then(function(response) {
                if(response.ok){
                    return response.json();
                }
                throw new Error('Something went wrong with request.');
            })
            .then(response => {this.setState(response)})
            .catch( error => {
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
                console.log("request failed", error)
            });
    };

    render() {
            return (
                <div className="App" >
                <header className="App-header">
                    <h1>Spotistics</h1>
                    <p></p>
                    <p></p>
                    <div className="flexbox-container">
                    <SearchArtist  className="item" onGetArtistAudioInfo={this.handleGetArtistAudioInfo} artistInfo={this.state} />
                        <Graph className="item" newData={this.state.topTracksAudioFeaturesMean}/>
                </div>
                </header>
            </div>
        );
    }
}

export default App;