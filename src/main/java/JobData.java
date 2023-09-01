import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    private static void loadData() {

        if (isDataLoaded) {
            return;
        }

        allJobs = new ArrayList<>();

        try (Reader in = new FileReader(DATA_FILE);
             CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in)) {

            List<CSVRecord> records = parser.getRecords();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[0]);

            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Job data loading failed");
            e.printStackTrace();
        }
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        loadData();
        return allJobs;
    }

    public static ArrayList<String> findAll(String field) {
        loadData();
        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {
        loadData();
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(column).toLowerCase();

            if (aValue.contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    public static ArrayList<HashMap<String, String>> findByValue(String value) {
        loadData();
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            for (String entry : row.values()) {
                if (entry.toLowerCase().contains(value.toLowerCase())) {
                    jobs.add(row);
                    break;
                }
            }
        }

        return jobs;
    }
}
