package controller;

import model.LibraryDatabase;
import model.LibraryItem;

import java.util.ArrayList;
import java.util.List;

// Houses the logic for selection, insertion, merge or quick sort
public class SortingEngine {
    public void sortCatalogueByTitle(List<LibraryItem> catalogue, String algorithmChoice) {
        if (catalogue.isEmpty() || catalogue.size() < 2) {
            return;
        }
        if (algorithmChoice.equalsIgnoreCase("MERGE")) {
            mergeSort(catalogue, 0, catalogue.size() - 1);
        } else if (algorithmChoice.equalsIgnoreCase("INSERTION")) {
            insertionSort(catalogue);
        } else {
            System.out.println("Error: Invalid algorithm choice.");
        }

    }

    public void mergeSort(List<LibraryItem> catalogue, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(catalogue, left, mid);
            mergeSort(catalogue, mid + 1, right);

            merge(catalogue, left, mid, right);
        }
    }

    private void merge (List<LibraryItem> catalogue, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right + mid;

        List<LibraryItem> leftList = new ArrayList<LibraryItem>(leftSize);
        List<LibraryItem> rightList = new ArrayList<LibraryItem>(rightSize);

        for (int i = 0; i < leftSize; i++) {
            leftList.add(catalogue.get(left + i));
        }
        for (int j = 0; j < rightSize; j++) {
            rightList.add(catalogue.get(mid + j + 1));
        }

        int i = 0; int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            String leftTitle = leftList.get(i).getTitle().toLowerCase();
            String rightTitle = rightList.get(j).getTitle().toLowerCase();

            if (leftTitle.compareTo(rightTitle) <= 0) {
                catalogue.set(k, leftList.get(i));
                i++;
            } else {
                catalogue.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            catalogue.set(k, leftList.get(i));
            i++;
            k++;
        }
        while (j < rightSize) {
            catalogue.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    public void insertionSort(List<LibraryItem> catalogue) {
        for (int i = 0; i < catalogue.size(); i++) {
            LibraryItem currentItem = catalogue.get(i);
            String currentTitle = currentItem.getTitle().toLowerCase();

            int j = i + 1;
            while (j >= 0 && catalogue.get(j).getTitle().toLowerCase().compareTo(currentTitle) > 0) {
                catalogue.set(j + 1, catalogue.get(j));
                j--;
            }

            catalogue.set(j + 1, currentItem);
        }
    }
}
