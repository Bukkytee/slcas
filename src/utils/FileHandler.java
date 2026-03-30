package utils;

import model.LibraryItem;

import java.io.*;
import java.util.ArrayList;

// Manages reading from and writing to local text or JSON files for data persistence
public class FileHandler {
    private static final String FILE_NAME = "library_catalogue.dat";

    public static void saveCatalogue(ArrayList<LibraryItem> catalogue) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(catalogue);
            System.out.println("Saved library catalogue to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<LibraryItem> loadCatalogue() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("Library catalogue file does not exist");
            return new ArrayList<>();
        }

        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            ArrayList<LibraryItem> loadedCatalogue = (ArrayList<LibraryItem>) objectInputStream.readObject();
            System.out.println("Library catalogue loaded from " + FILE_NAME);
            return loadedCatalogue;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading database: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
