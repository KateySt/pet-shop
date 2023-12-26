package layer.service.impl;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import layer.service.APIGatewayService;

import java.util.HashMap;
import java.util.Map;

public class APIGatewayServiceImpl implements APIGatewayService {


    @Override
    public APIGatewayProxyResponseEvent getApiGatewayProxyResponseEvent(String output, int statusCode) {

        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put("Access-Control-Allow-Methods", "*");

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(headers)
                .withBody(output);
    }
}