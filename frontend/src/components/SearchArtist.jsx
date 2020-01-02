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
                <Image src={this.props.artistStats.images[1].url} height={this.props.artistStats.images[1].height} width={this.props.artistStats.images[1].width}  roundedCircle/>
                <p></p>
                <Form>
                    <Form.Group controlId="formSearchArtist">
                        <Form.Control ref={input => this.textInput = input} type="artist" placeholder="Enter Artist" />
                        <Form.Text className="text-muted">
                        </Form.Text>
                    </Form.Group>
                    <Button onClick={() => this.props.onGetArtistAudioInfo(this.textInput.value)} variant="primary">
                        Get Artist Statistics
                    </Button>
                </Form>
            </div>
        );
    }
}

export default SearchArtist;