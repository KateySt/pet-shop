package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import layer.service.APIGatewayService;
import layer.service.DynamoDBService;
import layer.service.impl.APIGatewayServiceImpl;
import layer.service.impl.DynamoDBServiceImpl;


public class CreateUserFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final DynamoDBService dynamoDBService = new DynamoDBServiceImpl();
    private static final APIGatewayService apiGatewayService = new APIGatewayServiceImpl();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {

        try {
            String output = dynamoDBService.createUser(input.getBody());
            return apiGatewayService.getApiGatewayProxyResponseEvent(output, 201);
        } catch (Exception e) {
            return apiGatewayService.getApiGatewayProxyResponseEvent(
                    "An error occurred while executing the lambda function: "
                            + e.getClass() + "; message: " + e.getMessage(),
                    503);
        }
    }

}