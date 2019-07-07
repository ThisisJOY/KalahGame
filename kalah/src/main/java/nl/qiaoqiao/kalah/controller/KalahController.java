package nl.qiaoqiao.kalah.controller;

import nl.qiaoqiao.kalah.components.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class KalahController {

    @Autowired
    private Board board;

    @GetMapping("/{pit}")
    public ResponseEntity<Board> play(@PathVariable int pit) {
        if (board.canPerformMove(pit)) {
            boolean extraMove = board.performMove(pit);
            while (extraMove) {
                board.performMove(pit);
            }
            if (board.isEndOfGame()) {
                board.result();
            }
        }
        return ResponseEntity.ok(board);
    }
}
