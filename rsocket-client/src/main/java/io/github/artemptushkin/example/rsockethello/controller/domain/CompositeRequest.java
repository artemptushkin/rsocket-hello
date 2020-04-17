package io.github.artemptushkin.example.rsockethello.controller.domain;

import lombok.Data;

import java.util.List;

@Data
public class CompositeRequest {
    private List<UnitRequest> unitRequests;
}
