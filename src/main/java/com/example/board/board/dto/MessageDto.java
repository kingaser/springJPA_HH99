package com.example.board.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class MessageDto {

    private String message;

    public MessageDto(String message) {
        this.message = message;
    }
}
