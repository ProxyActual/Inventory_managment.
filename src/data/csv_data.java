package src.data;

import java.util.ArrayList;
import java.util.HashMap;

public class csv_data {
    private HashMap<String, HashMap<String, Object>> data_;

    public csv_data() {
        data_ = new HashMap<>();
    }

    public void addData(String key, HashMap<String, Object> value) {
        if(data_.containsKey(key)){
            key = key + "_" + data_.size();
        }
        data_.put(key, value);
        if(value.get("ID") == null){
            value.put("ID", key);
        }
    }

    public csv_data getDataWhere(java.util.function.Predicate<HashMap<String, Object>> filter) {
        csv_data value = new csv_data();
        for (String k : data_.keySet()) {
            HashMap<String, Object> v = data_.get(k);
            if (v != null && filter.test(v)) {
                value.addData(k, v);
            }
        }
        return value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : data_.keySet()) {
            sb.append(key).append(": ").append(data_.get(key)).append("\n");
        }
        return sb.toString();
    }

    private ArrayList<String> getHeader() {
        ArrayList<String> header = new ArrayList<>();
        for (String key : data_.keySet()) {
            HashMap<String, Object> v = data_.get(key);
            if (v != null) {
                for (String k : v.keySet()) {
                    if (!header.contains(k)) {
                        header.add(k);
                    }
                }
            }
        }
        return header;
    }

    private void updateAllData(){
        ArrayList<String> header = getHeader();
        for(String key : data_.keySet()){
            HashMap<String, Object> v = data_.get(key);
            for(String k : header){
                if(v.get(k) == null){
                    v.put(k, "");
                }
            }
        }
    }

    public boolean save(String path) {
        // Check if the file exists and delete it if it does
        java.io.File file = new java.io.File(path);
        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Failed to delete existing file: " + path);
                return false;
            }
        }
        System.out.println("Saving "+ data_.size()+ " elements to " + path);
        try (java.io.FileWriter writer = new java.io.FileWriter(path)) {
            // Ensure all rows have the same headers
            updateAllData();
            // Write the header row
            ArrayList<String> headers = getHeader();
            writer.write(String.join(",", headers) + "\n");

            // Write the data rows
            for (String key : data_.keySet()) {
                HashMap<String, Object> row = data_.get(key);
                ArrayList<String> rowData = new ArrayList<>();
                for (String header : headers) {
                    Object value = row.get(header);
                    rowData.add(value != null ? value.toString() : "");
                }
                writer.write(String.join(",", rowData) + "\n");
            }

            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void load(String fileName){
        // Check if the file exists
        java.io.File file = new java.io.File(fileName);
        if (!file.exists()) {
            System.err.println("File not found: " + fileName);
            return;
        }

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(fileName))) {
            String line;
            ArrayList<String> headers = new ArrayList<>();

            // Read the header row
            if ((line = reader.readLine()) != null) {
            String[] headerArray = line.split(",");
            for (String header : headerArray) {
                headers.add(header.trim());
            }
            }

            // Read the data rows
            while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            HashMap<String, Object> row = new HashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                String value = i < values.length ? values[i].trim() : "";
                row.put(headers.get(i), value);
            }
            addData(row.get("ID") != null ? row.get("ID").toString() : "", row);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
