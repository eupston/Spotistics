import React, {useState} from 'react'
import './Chart.css';
import { ResponsiveBar } from '@nivo/bar'
import DropdownButton from 'react-bootstrap/DropdownButton'
import Dropdown from 'react-bootstrap/Dropdown'

const Chart = (props) => {

    const currentData = Object.keys(props.data).map(key => {
        return ({[props.xaxis_id]: key , [key] : props.data[key]});
    });

    return(
        <div className='chart'>
            <ResponsiveBar
                data={currentData}
                keys={Object.keys(props.data)}
                indexBy= {props.xaxis_id}
                margin={{ top: 10, right: 50, bottom: 50, left: 50 }}
                padding={0.2}
                maxValue={1.0}
                groupMode="stacked"
                colors={{ scheme: 'blues' }}
                colorBy="id"
                theme={{
                  axis: {
                      fontSize: "14px",
                      tickColor: "#eee",
                      ticks: {
                        line: {
                          stroke: "#555555"
                        },
                        text: {
                          fill:"#FFFFFF"
                        }
                      },
                      legend: {
                        text: {
                          fill: "#aaaaaa"
                        }
                      }
                    },
                }}
                defs={[
                    {
                        id: 'dots',
                        type: 'patternDots',
                        background: 'inherit',
                        color: '#38bcb2',
                        size: 4,
                        padding: 1,
                        stagger: true
                    },
                    {
                        id: 'lines',
                        type: 'patternLines',
                        background: 'inherit',
                        color: '#eed312',
                        rotation: -45,
                        lineWidth: 6,
                        spacing: 10
                    }
                ]}
                fill={[]}
                borderWidth={2}
                borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
                axisTop={null}
                axisRight={null}
                axisBottom={{
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 0,
                    legend:  props.xaxis_id,
                    legendPosition: 'middle',
                    legendOffset: 45,
                    color: '#282c34',

                }}
                enableLabel={false}
                labelSkipWidth={12}
                labelSkipHeight={12}
                labelTextColor={{ from: 'color', modifiers: [ [ 'darker', '0.8' ] ] }}
                legends={[]}
                // tooltip={e => {
                //     return (
                //         <div>
                //             {"l"
                //                 ? "test4"
                //                 : "No data"}
                //         </div>
                //     );
                // }}
                animate={true}
                motionStiffness={90}
                motionDamping={15}
            />
            <DropdownButton
                className='dropdown'
                onSelect={props.dropdown_selection_handler.bind(this)}
                size="sm"
                variant="outline-primary"
                id="dropdown-basic-button" title={props.dropdown_current_selected}>
                {props.dropdown_options.map((option, i) => (
                    <Dropdown.Item  key={i} eventKey={i}>
                        {option}
                    </Dropdown.Item >
                ))}
            </DropdownButton>
        </div>

    );

};

export default Chart;