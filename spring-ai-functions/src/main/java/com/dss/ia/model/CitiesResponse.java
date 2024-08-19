package com.dss.ia.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.Arrays;
import java.util.Objects;

public record CitiesResponse(@JsonPropertyDescription("List of the cities") CityResponse[] cities) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitiesResponse that = (CitiesResponse) o;
        return Objects.deepEquals(cities, that.cities);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cities);
    }

    @Override
    public String toString() {
        return "CitiesResponse{" +
                "cities=" + Arrays.toString(cities) +
                '}';
    }
}
