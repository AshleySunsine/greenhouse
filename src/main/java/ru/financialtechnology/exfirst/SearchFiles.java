package ru.financialtechnology.exfirst;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

public class SearchFiles implements FileVisitor<Path> {
    private Map<String, String> paths = new TreeMap<>();
    private Predicate<Path> pred;

    public SearchFiles(Predicate<Path> condition) {
        pred = condition;
    }

    public Map<String, String> getPaths() {
        return paths;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (pred.test(file)) {
            paths.put(file.toFile().getAbsolutePath(), file.toFile().getAbsolutePath());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}
