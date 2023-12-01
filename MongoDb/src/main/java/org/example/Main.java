package org.example;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://<name>:<password>@cluster0.u58z1in.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("sample_geospatial");
                //database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                MongoCollection<Document> collection = database.getCollection("shipwrecks");
                collection.find().forEach(document -> System.out.println(document.toJson()));

                Document shipwreck = new Document()
                        .append("chart", "US,US,graph,1234")
                        .append("latdec", 41.283333)
                        .append("londec", -70.1)
                        .append("gp_quality", 1)
                        .append("depth", 18)
                        .append("sounding_type", "fathoms")
                        .append("history", "Sank in collision with SS CITY OF SALISBURY, October 18, 1884.")
                        .append("quasou", "false")
                        .append("watlev", "always dry")
                        .append("coordinates", new Document("type", "Point")
                                .append("coordinates", Arrays.asList(-70.1, 41.283333)));
                collection.insertOne(shipwreck);

            } catch (MongoException e) {
                e.getMessage();
            }
        }catch (MongoException e){
            e.getMessage();
        }
        finally {

        }
    }
}
