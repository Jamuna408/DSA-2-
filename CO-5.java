import java.io.*;
import java.util.*;

public class ExternalMergeSort {

    // Number of lines per chunk (change according to available RAM)
    static final int CHUNK_SIZE = 100000;

    // Class for Priority Queue
    static class HeapNode implements Comparable<HeapNode> {
        String line;
        BufferedReader reader;

        HeapNode(String line, BufferedReader reader) {
            this.line = line;
            this.reader = reader;
        }

        @Override
        public int compareTo(HeapNode other) {
            String t1 = this.line.split(",")[0];
            String t2 = other.line.split(",")[0];
            return t1.compareTo(t2);
        }
    }

    // Phase 1: Split file into sorted chunks
    public static List<File> createSortedChunks(String inputFile) throws IOException {

        List<File> chunkFiles = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        List<String> lines = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {

            lines.add(line);

            if (lines.size() >= CHUNK_SIZE) {
                chunkFiles.add(writeChunk(lines));
                lines.clear();
            }
        }

        if (!lines.isEmpty()) {
            chunkFiles.add(writeChunk(lines));
        }

        br.close();

        return chunkFiles;
    }

    // Write one sorted chunk
    private static File writeChunk(List<String> lines) throws IOException {

        Collections.sort(lines, (a, b) ->
                a.split(",")[0].compareTo(b.split(",")[0]));

        File tempFile = File.createTempFile("chunk_", ".txt");

        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

        for (String s : lines) {
            bw.write(s);
            bw.newLine();
        }

        bw.close();

        return tempFile;
    }

    // Phase 2: K-Way Merge
    public static void mergeChunks(List<File> chunkFiles, String outputFile)
            throws IOException {

        PriorityQueue<HeapNode> pq = new PriorityQueue<>();

        List<BufferedReader> readers = new ArrayList<>();

        for (File file : chunkFiles) {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            readers.add(reader);

            String line = reader.readLine();

            if (line != null) {
                pq.add(new HeapNode(line, reader));
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        while (!pq.isEmpty()) {

            HeapNode node = pq.poll();

            writer.write(node.line);
            writer.newLine();

            String next = node.reader.readLine();

            if (next != null) {
                pq.add(new HeapNode(next, node.reader));
            }
        }

        writer.close();

        for (BufferedReader r : readers)
            r.close();

        for (File f : chunkFiles)
            f.delete();
    }

    public static void main(String[] args) {

        String inputFile = "ipl_ad_logs.txt";
        String outputFile = "sorted_ipl_ad_logs.txt";

        try {

            System.out.println("Creating sorted chunks...");

            List<File> chunks = createSortedChunks(inputFile);

            System.out.println("Chunks Created: " + chunks.size());

            System.out.println("Merging chunks...");

            mergeChunks(chunks, outputFile);

            System.out.println("Sorting Completed Successfully!");
            System.out.println("Output File: " + outputFile);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}