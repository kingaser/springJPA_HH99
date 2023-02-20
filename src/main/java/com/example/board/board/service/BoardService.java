package com.example.board.board.service;

import com.example.board.board.dto.BoardRequestDto;
import com.example.board.board.dto.BoardResponseDto;
import com.example.board.board.dto.MessageDto;
import com.example.board.board.entity.Board;
import com.example.board.board.repository.BoardRepository;
import com.example.board.board.entity.User;
import com.example.board.board.jwt.JwtUtil;
import com.example.board.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Claims token(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;
        // 토큰이 있는 경우에만 게시글 작성 가능
        if (token != null) {
            // 토큰에서 사용자 정보 가져오기
            if (jwtUtil.validateToken(token)) {
                 claims = jwtUtil.getUserInfoFromToken(token);
            }
        } else {
            throw new IllegalArgumentException("Token Error");
        }
        return claims;
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoard() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDto list = new BoardResponseDto(board);
            boardResponseDtoList.add(list);
        }
        return boardResponseDtoList;
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        Claims claims = token(request);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new NullPointerException("사용자가 존재하지 않습니다.")
        );
        Board board = boardRepository.save(new Board(requestDto, user.getId()));
        return new BoardResponseDto(board);

    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        Claims claims = token(request);
        if (null == claims) throw new AssertionError("사용자가 존재하지 않습니다.");
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
        );
        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public MessageDto delete(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        Claims claims = token(request);
        if (null == claims) throw new AssertionError("사용자가 존재하지 않습니다.");
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
        );

        if (requestDto.getUsername().equals(board.getUsername())) {
            boardRepository.deleteById(id);
            return new MessageDto("삭제 완료");
        } else {
            return new MessageDto("삭제 실패");
        }
    }
}
