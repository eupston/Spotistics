import React, {Component} from 'react';
import './App.css';
import Graph from "./components/graph";
import SearchArtist from "./components/SearchArtist";

class App extends Component {

    state = {
        "artistStats":{
                        "name": null,
                        "followers": {
                            "href": null,
                            "total": null
                        },
                        "images":[{
                                    "height": null,
                                    "width": null,
                                    "url":"https://dummyimage.com/320X320/1e90ff/1e90ff"
                                    }]
                        },
        "audiofeatures":{"Acousticness": 0,
            "Danceability": 0,
            "Valence":0,
            "Energy": 0,
            "Speechiness": 0,
            "Liveness": 0,
            "Instrumentalness": 0}
    };

    componentDidMount() {
    }


    handleGetArtistAudioInfo = (artist) => {
        fetch('/api/artists/' + artist)
            .then(response => response.json())
            .then(response => {this.setState({"artistStats":response})});
        fetch('/api/artists/' + artist + "/toptracks/audiofeatures/mean")
            .then(response => response.json())
            .then(response => {this.setState({audiofeatures:response})});
    };


    render() {
            return (
                <div className="App" >
                <header className="App-header">
                    <div className="flexbox-container">
                    <SearchArtist  className="item" onGetArtistAudioInfo={this.handleGetArtistAudioInfo} artistStats={this.state.artistStats} />
                        <Graph className="item" artistAudioFeatures={this.state.audiofeatures}/>
                </div>
                </header>
            </div>
        );
    }
}

export default App;