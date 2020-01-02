import React from 'react'
import { Chart } from 'react-charts'
import '../App.css';

const Graph = ({artistAudioFeatures}) => {

    const audiofeatures = Object.keys(artistAudioFeatures).map(function(key) {
        return [key, Number(artistAudioFeatures[key])];
    });

    const data = [
            {
                label: 'Series 1',
                data: audiofeatures
            }
        ]

    const series = React.useMemo(
        () => ({
            type: 'bar'
        }),
        []
    )

    const getSeriesStyle = React.useCallback(
        () => ({
            transition: 'all .5s ease'
        }),
        []
    )
    const getDatumStyle = React.useCallback(
        () => ({
            transition: 'all .5s ease'
        }),
        []
    )
    const axes = React.useMemo(
        () => [
            {primary: true, type: 'ordinal', position: 'bottom'},
            {type: 'linear', position: 'left', stacked: false, max:10}
        ],
        []
    )


   return( <div className="item" style={{ width: '800px',  height: '528px'  }}>
        <Chart
            data={data}
            axes={axes}
            series={series}
            getSeriesStyle={getSeriesStyle}
            getDatumStyle={getDatumStyle}
            dark/>
           <p></p>
           <h1 >Mean of Top Tracks Audio Features</h1>
       </div>
    );

};

export default Graph;