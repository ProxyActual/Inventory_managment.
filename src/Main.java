package src;

import src.data.csv_data;
import java.util.HashMap;

public class Main {
    static csv_data data = new csv_data();
    public static void main(String[] args) {
        System.out.println("Loading Data");
        data.load("data.csv");

        for(int i = 0; i < 10000000; i++){
            System.out.println("Adding Data " + i);
            HashMap<String, Object> value = new HashMap<>();
            value.put("Date", java.time.LocalDate.now().plusDays((int)(Math.random() * 365) - 182).toString());
            value.put("Transaction", ((Math.random()-.5) * 20000));
            value.put("Description", "Test");
            data.addData(null, value);
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            data.save("data.csv");
            // Add any cleanup code here if needed
        }));
    }
}
