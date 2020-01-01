import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Image from 'react-bootstrap/Image'

class App extends Component {

    state = {
        "name":null,
        "followers": {
            "href": null,
            "total": null
        },
        "images":[{
                    "height": null,
                    "width": null,
                    "url":null
                    },
                    {
                    "height": null,
                    "width": null,
                    "url":null
        }]
    };

    componentDidMount() {
    }

    testrestapi = (artist) => {
        console.log("recieved arist:" + artist)

        fetch('/api/artists/' + artist)
            .then(response => response.json())
            .then(response => {this.setState(response)})
            .then(console.log(this.state));
    };


render() {
    return (
        <div className="App">
        <header className="App-header">
        <p></p>
            {/*<img src={logo} className="App-logo" alt="logo"/>*/}
        <Image src={this.state.images[1].url} height={this.state.images[1].height} width={this.state.images[1].width} roundedCircle/>
            <p></p>
            <Form>
                <Form.Group controlId="formSearchArtist">
                    <Form.Control ref={input => this.textInput = input} type="artist" placeholder="Enter artist" />
                    <Form.Text className="text-muted">
                    </Form.Text>
                </Form.Group>
                <Button onClick={() => this.testrestapi(this.textInput.value)} variant="primary">
                    Get Artist
                </Button>
            </Form>
        <h1 className="App-title">Artist {this.state.name}</h1>
        <h1 className="App-title">Followers {this.state.followers.total}</h1>
        </header>
        <p className="App-intro">
        To get started, edit <code>src/App.js</code> and save to reload.
    </p>
    </div>
);
}
}

export default App;