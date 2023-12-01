package org.example;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String token = "";
        String bucket = "";
        String org = "";

        InfluxDBClient client = InfluxDBClientFactory.create("https://eu-central-1-1.aws.cloud2.influxdata.com/", token.toCharArray());

        while (true) {
            System.out.printf("%-10s %-80s%n",
                    "Numero", "Descrizione");
            System.out.printf("%-10s %-80s%n",
                    "1", "Inserisci");
            System.out.printf("%-10s %-80s%n",
                    "2", "Visualizza");
            System.out.printf("%-10s %-80s%n",
                    "0", "Esci");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    insertData(client, bucket, org);
                    break;
                case 2:
                    queryAndPrintData(client, bucket, org);
                    break;
                case 0:
                    ((InfluxDBClient) client).close();
                    System.exit(0);
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private static void insertData(InfluxDBClient client, String bucket, String org) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci i dati (es. mem,host=host1 used_percent=76.43234543):");
        String data = scanner.nextLine();
        String finalData = "piloti,pilota=dani velocità=" + data;
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writeRecord(bucket, org, WritePrecision.NS, finalData);

        System.out.println("Dati inseriti con successo.");
    }

    private static void queryAndPrintData(InfluxDBClient client, String bucket, String org) {
        String fluxQuery = "from(bucket: \"" + bucket + "\") "
                + "|> range(start: -1d) ";
        QueryApi queryApi = client.getQueryApi();
        List<FluxTable> results = queryApi.query(fluxQuery, org);
        List<Map<String, Object>> recordsList=new java.util.ArrayList<>();
        for (FluxTable result : results) {
            List<FluxRecord> records = result.getRecords();

            for (FluxRecord record : records) {
                Map<String, Object> values = record.getValues();
                recordsList.add(values);
                System.out.println("Values: " + values);
            }
        }
        plotConsoleGraph(recordsList);
    }
    public static void plotConsoleGraph(List<Map<String, Object>> records) {
        // Define graph size
        final int GRAPH_WIDTH = records.size();
        final int GRAPH_HEIGHT = 10;

        // Extract values and find min and max y-values
        double[] values = new double[GRAPH_WIDTH];
        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;

        for (int i = 0; i < GRAPH_WIDTH; i++) {
            Double value = (Double) records.get(i).get("_value");
            values[i] = value;
            minValue = Math.min(minValue, value);
            maxValue = Math.max(maxValue, value);
        }

        // Scale the y-values between 0 and GRAPH_HEIGHT
        for (int i = 0; i < GRAPH_WIDTH; i++) {
            values[i] = (values[i] - minValue) / (maxValue - minValue) * GRAPH_HEIGHT;
        }

        // Plotting the graph
        System.out.println("Plotting graph:");
        for (int y = GRAPH_HEIGHT; y >= 0; y--) {
            for (int x = 0; x < GRAPH_WIDTH; x++) {
                char plotChar = Math.round(values[x]) == y ? '*' : ' ';
                if (y == 0) {
                    plotChar = '-'; // Draw the x-axis
                }
                System.out.print(plotChar);
            }
            System.out.println(); // New line for each level of the y-axis
        }
    }
}
