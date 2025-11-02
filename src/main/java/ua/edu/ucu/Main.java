package ua.edu.ucu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final String filePath = System.getProperty("user.dir") + "/students.csv";
    private static final Database[] implementations = {
            new DatabaseV1(),
            new DatabaseV2(),
            new DatabaseV3()
        };

    private static final int RATIO_A = 10;
    private static final int RATIO_B = 10;
    private static final int RATIO_C = 1;
    private static final int[] databaseSizes = {100, 1000, 10000, 100000};
    
    private static final int BENCHMARK_DURATION = 10;
    private static final Random random = new Random();

    private static List<String> emailToSearch;
    private static List<String> namesToSearch;
    private static List<String> surnamesToSearch;

    public static void main(String[] args) {
        operationsBenchmark();
        sortBenchmark();
    }

    private static void operationsBenchmark() {
        emailToSearch = new ArrayList<>(1000);
        namesToSearch = new ArrayList<>(1000);
        surnamesToSearch = new ArrayList<>(1000);

        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            int count = 0;

            while ((line = br.readLine()) != null && count < 1000) {
                count++;

                String[] data = line.split(",");

                emailToSearch.add(data[2]);
                namesToSearch.add(data[0]);
                surnamesToSearch.add(data[1]);
            }

        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Implementation; DB_Size; Ops_Count; Memory_Used_MB");

        for (int size : databaseSizes) {
            for (Database db : implementations) {
                long memoryUsed = measureMemoryUsage(() -> db.readFromCSV(filePath, size));
                double memoryUsedMB = (double) memoryUsed / (1024 * 1024);

                long opsCount = runBenchmark(db);

                System.out.printf("%s; %d; %d; %.2f%n",
                        db.getClass(), size, opsCount, memoryUsedMB);
            }
        }
    }

    private static long runBenchmark(Database db) {

        long operationCount = 0;
        long endTime = System.currentTimeMillis() + (BENCHMARK_DURATION * 1000);

        int sumRatios = RATIO_A + RATIO_B + RATIO_C;
        
        while (System.currentTimeMillis() < endTime) {
            if (operationCount % sumRatios < RATIO_A) {
                String name = namesToSearch.get(random.nextInt(namesToSearch.size()));
                String surname = surnamesToSearch.get(random.nextInt(surnamesToSearch.size()));
                db.operation1(name, surname);
            } else if (operationCount % sumRatios < RATIO_A + RATIO_B) {
                db.operation2();
            } else {
                String email = emailToSearch.get(random.nextInt(emailToSearch.size()));
                db.operation3(email, "NEW-00");
            }
            operationCount++;
        }
        return operationCount;
    }

    private static long measureMemoryUsage(Runnable loader) {
        System.gc();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        Runtime rt = Runtime.getRuntime();
        long memoryBefore = rt.totalMemory() - rt.freeMemory();
        
        loader.run();
        
        long memoryAfter = rt.totalMemory() - rt.freeMemory();
        return memoryAfter - memoryBefore;
    }

    private static void sortBenchmark() {
        DatabaseV1 db = new DatabaseV1();
        db.readFromCSV(filePath);

        final int ITERATIONS = 10;
        long totalBuiltIn = 0;
        long totalCustom = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            System.gc();
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            long startTime = System.nanoTime();
            db.sortBuiltIn();
            long endTime = System.nanoTime();
            totalBuiltIn += (endTime - startTime);
            
            System.gc();
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            startTime = System.nanoTime();
            db.sortCustom();
            endTime = System.nanoTime();
            totalCustom += (endTime - startTime);
        }

        System.out.println("Built-in sort (avg): " + (totalBuiltIn / ITERATIONS) / 1_000_000f + " ms");
        System.out.println("Custom sort (avg): " + (totalCustom / ITERATIONS) / 1_000_000f + " ms");
    }
}