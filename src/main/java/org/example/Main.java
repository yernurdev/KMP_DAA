package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.algorithm.KMPMatcher;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        run("small");
        run("medium");
        run("large");
    }

    private static void run(String size) throws Exception {
        String inputPath = "data/input/input_" + size + ".json";
        String outputPath = "data/output/output_" + size + ".json";

        JsonObject input = new Gson().fromJson(new FileReader(inputPath), JsonObject.class);
        String text = input.get("text").getAsString();
        String pattern = input.get("pattern").getAsString();

        long start = System.nanoTime();
        KMPMatcher kmp = new KMPMatcher(pattern);
        List<Integer> positions = kmp.search(text);
        long end = System.nanoTime();

        JsonObject result = new JsonObject();
        result.addProperty("size", size);
        result.addProperty("pattern", pattern);
        result.addProperty("matches", positions.size());
        result.addProperty("execution_ns", end - start);
        result.add("positions", new Gson().toJsonTree(positions));

        Files.createDirectories(Paths.get("data/output"));
        FileWriter fw = new FileWriter(outputPath);
        fw.write(new Gson().toJson(result));
        fw.close();

        writeCsvRow(size, pattern, positions.size(), end - start);

        System.out.println("Finished: " + size + " | matches=" + positions.size() + " | time=" + (end - start));
    }

    private static void writeCsvRow(String size, String pattern, int matches, long timeNs) throws Exception {
        String csvPath = "data/output/metrics.csv";
        boolean exists = Files.exists(Paths.get(csvPath));

        FileWriter fw = new FileWriter(csvPath, true);

        if (!exists) {
            fw.write("size,pattern,matches,execution_ns\n");
        }

        fw.write(size + "," + pattern + "," + matches + "," + timeNs + "\n");
        fw.close();
    }
}
