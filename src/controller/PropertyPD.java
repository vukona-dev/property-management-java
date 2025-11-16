/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Vukona-Maritz
 */

import data.PropertyDA;
import model.Property;
import exceptions.DuplicateException;
import exceptions.NotFoundException;
import exceptions.DataStorageException;

import java.util.ArrayList;

public class PropertyPD {
    private PropertyDA propertyDA;

    public PropertyPD() {
        propertyDA = new PropertyDA();
    }

    // Add a new property
    public void addProperty(Property p) throws DuplicateException, DataStorageException {
        propertyDA.addRecord(p);
    }

    // Search property by reference number
    public Property searchProperty(String refNo) throws NotFoundException {
        Property p = propertyDA.search(refNo);
        if (p == null) {
            throw new NotFoundException("Property with reference " + refNo + " not found.");
        }
        return p;
    }

    // Update property
    public void updateProperty(Property p) throws NotFoundException, DataStorageException {
        propertyDA.update(p);
    }

    // Delete property
    public void deleteProperty(String refNo) throws NotFoundException, DataStorageException {
        propertyDA.delete(refNo);
    }

    // Count TownHouse properties
    public int countTownHouse() {
        return propertyDA.countTownHouse();
    }

    // Get all properties
    public ArrayList<Property> getAllProperties() {
        return propertyDA.getAll();
    }
}
