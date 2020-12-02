package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoomType {
    @JsonProperty("Laboratory")
    LABORATORY,
    @JsonProperty("Office")
    OFFICE,
    @JsonProperty("Accommodation")
    ACCOMMODATION
}
