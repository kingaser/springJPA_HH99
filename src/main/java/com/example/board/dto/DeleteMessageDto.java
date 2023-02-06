package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteMessageDto {

    private String message;

    public DeleteMessageDto(String message) {
        this.message = message;
    }
}
