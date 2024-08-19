package com.dss.ia.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;

public record CityResponse(@JsonPropertyDescription("Name of the city") String name,
                           @JsonPropertyDescription("Latitude") BigDecimal latitude,
                           @JsonPropertyDescription("Longitude") BigDecimal longitude,
                           @JsonPropertyDescription("name of the country") String country,
                           @JsonPropertyDescription("Population") Integer population,
                           @JsonPropertyDescription("Flag is capital city") Boolean isCapital) {
}
