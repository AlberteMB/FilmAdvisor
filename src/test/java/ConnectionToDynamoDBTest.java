import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionToDynamoDBTest {

    @Test
    void testConnectionToDynamoDB() {

        try (DynamoDbClient dynamoDb = DynamoDbClient.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .build()) {
            ListTablesResponse response = dynamoDb.listTables();
            assertNotNull(response);
            System.out.println("Conexión a DynamoDB exitosa. Tablas disponibles:");
            response.tableNames().forEach(System.out::println);


            assertTrue(response.hasTableNames());
        } catch (DynamoDbException e) {
            fail("Fallo al conectar con DynamoDB. Causa: " + e.awsErrorDetails().errorMessage());
        }
    }
}

