package controller;

import model.LibraryDatabase;
import model.LibraryItem;

import java.util.ArrayList;
import java.util.List;

// Contains the implementation for linear, binary and recursive searches.
public class SearchEngine {
    private LibraryDatabase database;

    public SearchEngine(LibraryDatabase database) {
        this.database = database;
    }

    public List<LibraryItem> searchByTitle(String title, boolean isCatalogueSorted) {
        List<LibraryItem> catalogue = database.getCatalogue();
        String query = title.toLowerCase().trim();

        if (isCatalogueSorted) {
            List<LibraryItem> results = new ArrayList<>();
            LibraryItem match = binarySearch(catalogue, query);
            if (match != null) {
                results.add(match);
            }
            return results;
        } else {
            return linearSearch(catalogue, query);
        }
    }

    private List<LibraryItem> linearSearch(List<LibraryItem> catalogue, String query) {
        List<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : catalogue) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }

    private LibraryItem binarySearch(List<LibraryItem> catalogue, String targetTitle) {
        int left = 0;
        int right = catalogue.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            LibraryItem midItem = catalogue.get(mid);
            String midTitle = midItem.getTitle().toLowerCase();
            int comparison = midTitle.compareTo(targetTitle);
            if (comparison == 0) {
                return midItem;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}
