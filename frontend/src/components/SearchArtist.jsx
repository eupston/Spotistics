import React, {Component} from 'react';
import Image from "react-bootstrap/Image";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import '../App.css';

class SearchArtist extends Component {
    state = {};

    render() {

        return (
            <div className="item">
                <Image src={this.props.artistInfo.artistImageURL} height="320" width="320"  roundedCircle/>
                <p></p>
                <p></p>
                <Form>
                    <Form.Group controlId="formSearchArtist">
                        <Form.Control ref={input => this.textInput = input} type="artist" placeholder="Enter Artist" />
                        <Form.Text className="text-muted">
                        </Form.Text>
                    </Form.Group>
                    <Button onClick={() => this.props.onGetArtistAudioInfo(this.textInput.value)} variant="outline-primary" block>
                        Get Artist Statistics
                    </Button>
                </Form>
                <p></p>
                <h1 > {this.props.artistInfo.artistName}</h1>
            </div>
        );
    }
}

export default SearchArtist;