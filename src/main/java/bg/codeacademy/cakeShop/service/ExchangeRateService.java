package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.error_handling.exception.ConversionException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//This class use external API at address:
//https://www.exchangerate-api.com/docs/pair-conversion-requests
//to exchange rate.

@Service
public class ExchangeRateService {
    @Value("${exchange-rate.base-url}")
    private String BASE_URL;
    @Value("${exchange-rate.api-key}")
    private String API_KEY;
    @Value("${exchange-rate.convert-from-to}")
    private String CONVERT_FROM_TO;
    public static String API_URL;
    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convert(double value) {
        API_URL = BASE_URL.
                replace("API-KEY", API_KEY);
        API_URL = API_URL.replace("CONVERT-FROM-TO", CONVERT_FROM_TO);
        API_URL = API_URL.replace("AMOUNT", String.valueOf(value));
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(API_URL, JsonNode.class);
        JsonNode node = response.getBody();
        if (node != null) {
            return node.findValue("conversion_result").asDouble();
        } else {
            throw new ConversionException("Error during currency conversion, API:" + API_URL + " not respond!");
        }
    }
}
