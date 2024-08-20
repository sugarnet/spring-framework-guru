package com.dss.ia.functions;

import com.dss.ia.model.CityResponse;
import com.dss.ia.model.WeatherRequest;
import com.dss.ia.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.util.Optional;
import java.util.function.Function;

public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceFunction.class);
    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";
    public static final String CITY_URL = "https://api.api-ninjas.com/v1/city";

    private final String apiNinjasKey;

    public WeatherServiceFunction(String apiNinjasKey) {
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(CITY_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();


        CityResponse[] cityResponse = restClient.get().uri(uriBuilder -> {
            LOGGER.info("Building URI for city request: {}", weatherRequest);

            uriBuilder.queryParam("name", weatherRequest.location());

            if (weatherRequest.state() != null && !weatherRequest.state().isBlank()) {
                uriBuilder.queryParam("name", weatherRequest.state());
            }
            if (weatherRequest.country() != null && !weatherRequest.country().isBlank()) {
                uriBuilder.queryParam("country", weatherRequest.country());
            }
            return uriBuilder.build();
        }).retrieve().body(CityResponse[].class);

        LOGGER.info("City response: {}", cityResponse);

        restClient = RestClient.builder()
                .baseUrl(WEATHER_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();

        WeatherResponse response = null;
        if (Optional.ofNullable(cityResponse).isPresent()) {
            response = restClient.get().uri(uriBuilder -> {
                LOGGER.info("Building URI for weather request. Latitude: {}, Longitude {}", cityResponse[0].latitude(), cityResponse[0].longitude());

                uriBuilder.queryParam("lat", cityResponse[0].latitude());
                uriBuilder.queryParam("lon", cityResponse[0].longitude());

                return uriBuilder.build();
            }).retrieve().body(WeatherResponse.class);

            LOGGER.info("Weather response: {}", response);

        }

        return response;
    }
}
