import React from 'react'
import { Chart } from 'react-charts'
import '../App.css';
import Carousel from 'react-bootstrap/Carousel'


const Graph = ({newData}) => {

    const myData = Object.keys(newData).map(function(key) {
        return [key, Number(newData[key])];
    });

    const data = [
            {
                label: 'Series 1',
                data: myData
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
           <p></p>

       {/*<Carousel interval={false}*/}
       {/*          indicators={false} style={{ width: '800px',  height: '428px'  }}>*/}

       {/*    <Carousel.Item style={{ width: '800px',  height: '428px'  }}>*/}
           <Chart
            data={data}
            axes={axes}
            series={series}
            getSeriesStyle={getSeriesStyle}
            getDatumStyle={getDatumStyle}
            dark/>
               <h1 >Mean of Top Tracks Audio Features</h1>
        {/*   </Carousel.Item>*/}
        {/*   <Carousel.Item>*/}
        {/*       Yo*/}
        {/*   </Carousel.Item>*/}
        {/*</Carousel>*/}
       </div>
    );

};

export default Graph;