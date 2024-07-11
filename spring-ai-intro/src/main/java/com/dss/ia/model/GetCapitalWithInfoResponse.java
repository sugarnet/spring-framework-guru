package com.dss.ia.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalWithInfoResponse(
        @JsonPropertyDescription("This is the capital name") String capital,
        @JsonPropertyDescription("This is the population") String population,
        @JsonPropertyDescription("This is the location") String region,
        @JsonPropertyDescription("This is the primary spoken language") String language,
        @JsonPropertyDescription("This is the currency used") String currency
) {
}
