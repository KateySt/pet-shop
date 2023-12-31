package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import layer.service.APIGatewayService;
import layer.service.impl.APIGatewayServiceImpl;
import layer.service.DynamoDBService;
import layer.service.impl.DynamoDBServiceImpl;

import java.util.Map;


public class GetUserListByQueryFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final DynamoDBService dynamoDBService = new DynamoDBServiceImpl();
    private static final APIGatewayService apiGatewayService = new APIGatewayServiceImpl();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        try {
            Map<String, String> queryStringParameters = input.getQueryStringParameters();

            String output = dynamoDBService.getUsersListByQueryResponse(queryStringParameters, input.getBody());

            return apiGatewayService.getApiGatewayProxyResponseEvent(output, 200);

        } catch (Exception e) {

            return apiGatewayService.getApiGatewayProxyResponseEvent(
                    "An error occurred while executing the lambda function: "
                            + e.getClass() + "; message: " + e.getMessage(),
                    503);
        }
    }

}