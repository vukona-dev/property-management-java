/**
 *
 * @author Vukona-Maritz
 */

package data;

import model.Property;
import exceptions.DuplicateException;
import exceptions.NotFoundException;
import exceptions.DataStorageException;

import java.io.*;
import java.util.ArrayList;

public class PropertyDA {
    private ArrayList<Property> propertyList;
    private final String filename = "property.dat";

    public PropertyDA() {
        propertyList = new ArrayList<>();
        load();
    }

    // Load from file
    private void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            propertyList = (ArrayList<Property>) ois.readObject();
        } catch (FileNotFoundException e) {
            propertyList = new ArrayList<>(); // file doesn't exist yet
        } catch (IOException | ClassNotFoundException e) {
            throw new DataStorageException("Error loading property data: " + e.getMessage());
        }
    }

    // Save to file
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(propertyList);
        } catch (IOException e) {
            throw new DataStorageException("Error saving property data: " + e.getMessage());
        }
    }

    // Add new record
    public void addRecord(Property p) {
        if (search(p.getPRefNo()) != null) {
            throw new DuplicateException("Property with reference " + p.getPRefNo() + " already exists.");
        }
        propertyList.add(p);
        save();
    }

    // Search by reference number
    public Property search(String refNo) {
        for (Property p : propertyList) {
            if (p.getPRefNo().equalsIgnoreCase(refNo)) {
                return p;
            }
        }
        return null;
    }

    // Update existing record
    public void update(Property updated) {
        Property existing = search(updated.getPRefNo());
        if (existing == null) {
            throw new NotFoundException("Property with reference " + updated.getPRefNo() + " not found.");
        }
        propertyList.remove(existing);
        propertyList.add(updated);
        save();
    }

    // Delete record
    public void delete(String refNo) {
        Property p = search(refNo);
        if (p == null) {
            throw new NotFoundException("Property with reference " + refNo + " not found.");
        }
        propertyList.remove(p);
        save();
    }

    // Count TownHouse properties
    public int countTownHouse() {
        int count = 0;
        for (Property p : propertyList) {
            if (p.getPropType().equalsIgnoreCase("TownHouse")) {
                count++;
            }
        }
        return count;
    }

    // Get all properties
    public ArrayList<Property> getAll() {
        return propertyList;
    }
}
