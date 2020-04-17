package io.github.artemptushkin.example.rsockethello.controller;

import lombok.Data;

import java.util.List;

@Data
public class IntegerRequest {
    private List<Integer> integers;
}
