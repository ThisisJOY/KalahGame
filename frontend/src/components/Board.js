import React, { Component } from 'react'
import Button from 'react-bootstrap/Button'
import BoardDataService from '../service/BoardDataService'

class Board extends Component {

    constructor(props) {
        super(props)
        this.state = {
            pits: [4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0],
            turn: true,
            extra_turn: false,
            end_of_game: false,
            result: ""
        }
        this.handleClick = this.handleClick.bind(this)
    }
    
    handleClick(pit_id) {
        BoardDataService.retrieveAllPits(pit_id)
        .then(
            response => {
                console.log(response)
                this.setState({ 
                    pits: response.data.pits,
                    turn: response.data.turn,
                    extra_turn: response.data.extraTurn,
                    end_of_game: response.data.endOfGame,
                    result: response.data.result
                 })
            }
        )
    }

    render() {
        const { pits, turn, extra_turn, end_of_game, result } = this.state;
        return (
            <div>
                <div className="board">{turn ? "Player One's Turn" : "Player Two's Turn"}</div>
                <div className="board">{extra_turn ? "You Get Extra Turn" : ""}</div>
                <div className="board">{end_of_game ? "End of Game" : ""}</div>
                <div className="board">{end_of_game ? result: ""}</div>
                <div className="board">
                    <div className="store">
                        <Button variant="warning" size="lg">{pits[13]}<br /><br /></Button>
                    </div>
                    <div className="pit">
                        <div className="firstRow">
                            <div className="one">
                                <Button variant="warning" onClick={() => this.handleClick(12)}>{pits[12]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="warning" onClick={() => this.handleClick(11)}>{pits[11]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="warning" onClick={() => this.handleClick(10)}>{pits[10]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="warning" onClick={() => this.handleClick(9)}>{pits[9]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="warning" onClick={() => this.handleClick(8)}>{pits[8]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="warning" onClick={() => this.handleClick(7)}>{pits[7]}</Button>
                            </div>
                        </div>
                        <div className="secondRow">
                            <div className="one">
                                <Button variant="primary" onClick={() => this.handleClick(0)}>{pits[0]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="primary" onClick={() => this.handleClick(1)}>{pits[1]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="primary" onClick={() => this.handleClick(2)}>{pits[2]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="primary" onClick={() => this.handleClick(3)}>{pits[3]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="primary" onClick={() => this.handleClick(4)}>{pits[4]}</Button>
                            </div>
                            <div className="one">
                                <Button variant="primary" onClick={() => this.handleClick(5)}>{pits[5]}</Button>
                            </div>
                        </div>
                    </div>
                    <div className="store">
                        <Button variant="primary" size="lg">{pits[6]}<br /><br /></Button>
                    </div> 
                </div>
            </div>
            
        )
    }
}

export default Board
