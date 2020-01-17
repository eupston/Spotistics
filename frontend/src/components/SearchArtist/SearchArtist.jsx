import React, {Component} from 'react';
import Image from "react-bootstrap/Image";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import './SearchArtist.css'

class SearchArtist extends Component {
    render() {
        return (
            <div className="searchArtist">
                <Image className='image' src={this.props.artistInfo.artistImageURL} fluid roundedCircle/>
                <Form className='form' >
                    <Form.Group controlId="formSearchArtist">
                        <Form.Control ref={input => this.textInput = input} type="artist" placeholder="Enter Artist" />
                        <Form.Text >
                        </Form.Text>
                    </Form.Group>
                    <Button onClick={() => this.props.onGetArtistAudioInfo(this.textInput.value)}
                            variant="outline-primary"
                            disabled={this.props.artistInfo.isLoading}
                            block>
                        {this.props.artistInfo.isLoading ? 'Loading…' : 'Get Artist Statistics'}
                    </Button>
                </Form>
                <h1 > {this.props.artistInfo.artistName === null ? null : this.props.artistInfo.artistName.toUpperCase()}</h1>
            </div>
        );
    }
}

export default SearchArtist;