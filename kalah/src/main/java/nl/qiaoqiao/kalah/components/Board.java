package nl.qiaoqiao.kalah.components;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Represents a state of the Kalah board.
 */
@Component
@Data
public class Board {

    // Store the number of stones in each pit as a list.
    // Pit 0 is the bottom left, and they revolve anticlockwise.
    // So pit 6 is P1's store and pit 13 is P2's store.
    private int[] pits;

    // Whose turn?
    // True means P1, false means P2.
    private boolean turn;

    private boolean extraTurn;

    private boolean endOfGame;

    private String result;

    // Creates a new board with default number of stones in each pit.
    public Board() {
        pits = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
        turn = true;
        extraTurn = false;
        endOfGame = false;
        result = "";
    }

    /**
     * Perform a move.
     * The current player is updated when running this method.
     *
     * @param pit the pit is chosen for moving.
     * @return true if the current player has an extra move.
     */
    public boolean performMove(int pit) {
        // Picks up all the stones in a pit.
        int stones = pits[pit];
        pits[pit] = 0;
        // Deposits each stone in subsequent pits anticlockwise around the board.
        int currentPit = pit + 1;
        while (stones > 0) {
            // You can't put a stone in the opponent's store.
            if (turn && currentPit == 13) {
                currentPit = 0;
            }
            if (!turn && currentPit == 6) {
                currentPit = 7;
            }

            pits[currentPit]++;
            stones--;
            // If the last pit is empty, the player gets an extra turn.
            if (stones == 0 && pits[currentPit] == 1) {
                // And if there is at least one stone in the opposite pit,
                // they collect all of the stones in both pits and put it into their own store.
                int oppositePit = getOppositePit(currentPit);
                int oppositeStones = pits[oppositePit];
                if (oppositeStones > 0) {
                    pits[oppositePit] = 0;
                    pits[currentPit] = 0;
                    int totalStones = oppositeStones + 1;
                    if (turn) {
                        pits[6] += totalStones;
                    } else {
                        pits[13] += totalStones;
                    }
                }
                extraTurn = true;
            }

            currentPit++;
            currentPit = currentPit % 14;
        }

        int lastPit = currentPit - 1;
        // If the player deposited their last stone in their store, they get an extra turn.
        if (lastPit == 6 || lastPit == 13) {
            if (isEndOfGame()) {
                checkEndOfGame();
            }
            extraTurn = true;
        }

        turn = !turn;
        return extraTurn;
    }


    // Determines whether a given move is legal.
    // True if the pit is not empty and is not a store.
    // Pit number is relative to the current player.
    // P1, within range [0-5]. P2: [7-12].
    public boolean canPerformMove(int pit) {
        if (turn && (pit < 0 || pit > 6)) {
            return false;
        }
        if (!turn && (pit < 7 || pit > 12)) {
            return false;
        }
        return pits[pit] > 0;
    }

    /**
     * Return the pit directly opposite to this pit.
     * This method is only valid for the main pits, not the end stores.
     *
     * @param pit
     * @return opposite pit
     */
    public int getOppositePit(int pit) {
        return 12 - pit;
    }

    public void checkEndOfGame() {
        int sumPlayerOne = 0;
        int sumPlayerTwo = 0;
        for (int i = 0; i <= 5; i++) {
            sumPlayerOne += pits[i];
        }
        for (int i = 7; i <= 12; i++) {
            sumPlayerTwo += pits[i];
        }

        if (sumPlayerOne == 0 && sumPlayerTwo > 0) {
            // Clean up P2's side.
            pits[13] = sumPlayerTwo;
            for (int i = 7; i <= 12; i++) {
                pits[i] = 0;
            }
        }
        if (sumPlayerTwo == 0 && sumPlayerOne > 0) {
            // Clean up P1's side.
            pits[6] = sumPlayerOne;
            for (int i = 0; i <= 5; i++) {
                pits[i] = 0;
            }
        }

    }

    public boolean isEndOfGame() {
        // If all stones are in the stores, the game is finished.
        return endOfGame = pits[6] + pits[13] == 48;
    }

    public String result() {
        // P2 won
        if (pits[13] > pits[6]) {
            return result = "Player Two Wins";
        }
        // P1 won
        if (pits[13] < pits[6]) {
            return result = "Player One Wins";
        }
        // Draw
        return result = "Draw";
    }
}
