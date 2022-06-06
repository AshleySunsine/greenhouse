package ru.financialtechnology.exfirst;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Root folder is null. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        Search search = new Search();
        Path start = Paths.get(args[0]);
        Map<String, String> tree = search.search(start, p -> p.toFile()
                .getName()
                .endsWith(".txt"));
        search.save(tree, "./out.txt");


    }

    private Map<String, String> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private void save(Map<String, String> tree, String file) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            tree.entrySet().forEach(f -> out.println(f.getKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

