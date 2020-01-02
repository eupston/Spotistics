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
                                    "url":"https://dummyimage.com/320X320/4bb6e6/4bb6e6"
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
            .then(function(response) {
                if(response.ok){
                    return response.json();
                }
                throw new Error('Something went wrong with request.');
                })
            .then(response => {this.setState({"artistStats":response})})
            .catch( error => {
                this.setState({
                        artistStats: Object.assign({}, this.state.artistStats, {
                            name: "Artist Not Found",
                    }),
                })
                this.setState({
                    artistStats: Object.assign({}, this.state.artistStats, {
                        images:[{
                            "height": null,
                            "width": null,
                            "url":"https://dummyimage.com/320X320/4bb6e6/4bb6e6"
                        }],
                    }),
                })
                console.log("request failed", error)
            });
        fetch('/api/artists/' + artist + "/toptracks/audiofeatures/mean")
            .then(function(response) {
                if(response.ok){
                    return response.json();
                }
                throw new Error('Something went wrong with request.');
            })
            .then(response => {this.setState({audiofeatures:response})})
            .catch( error => {
                const defaultAudioFeatures = {
                    "Acousticness": 0,
                    "Danceability": 0,
                    "Valence":0,
                    "Energy": 0,
                    "Speechiness": 0,
                    "Liveness": 0,
                    "Instrumentalness": 0
                };
                this.setState({audiofeatures:defaultAudioFeatures})
                console.log("request failed", error)
            });
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