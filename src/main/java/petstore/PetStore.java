/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The pet store.
 * @author Nico Kuijpers
 */
public class PetStore {
    
    // The pets are stored in a map with key petId and value Pet-object
    private final Map<Integer,Pet> pets;
    
    // Id for the next pet to be added to the store
    private int nextPetId;
    
    // Instance of pet store (singleton pattern)
    private static PetStore instance;

    // Singleton pattern
    private PetStore() {
        // Initially, there are no pets in the pet store
        pets = new HashMap<>();
        nextPetId = 0;
    }
    
    /**
     * Get singleton instance of pet store.
     * @return instance of pet store
     */
    public static PetStore getInstance() {
        if (instance == null) {
            instance = new PetStore();
        }
        return instance;
    } 
    
    /**
     * Get pet with given id.
     * @param petId
     * @return pet with give id.
     */
    public Pet getPet(int petId) {
        return pets.get(petId);
    }
    
    /**
     * Get all pets in the store.
     * @return all pets in the store
     */
    public List<Pet> getAllPets() {
        return new ArrayList<>(pets.values());
    }
    
    /**
     * Get all pets in the store with given owner name.
     * @param ownerName name of the owner
     * @return all pets in the store with given owner name.
     */
    public List<Pet> getAllPetsWithOwner(String ownerName) {
        List petsWithOwner = new ArrayList<>();
        for (Pet p : pets.values()) {
            if (p.getOwnerName().equals(ownerName)) {
                petsWithOwner.add(p);
            }
        }
        return petsWithOwner;
    }
    
    /**
     * Add new pet to the pet store.
     * @param petName name of the new pet
     * @param ownerName name of the owner
     * @return the new pet
     */
    public Pet addPet(String petName, String ownerName) {
        // Define id for the new pet
        int petId = nextPetId;
        nextPetId++;
        
        // Create the new pet
        Pet pet = new Pet(petId, petName, ownerName);
        
        // Put the new pet in the pet store
        pets.put(petId,pet);
        
        // Return the new pet
        return pet;
    }
    
    /**
     * Change owner of pet with given id.
     * @param petId id of pet
     * @param ownerName name of the new owner
     * @return true when owner has been successfully changed, false otherwise
     */
    public boolean changeOwner(int petId, String ownerName) {
        // Get the pet wit given id from the pet store
        Pet pet = pets.get(petId);
        if (pet != null) {
            // Pet exists, change the name of the owner
            pet.setOwnerName(ownerName);
            return true;
        }
        // Pet not found
        return false;
    }
    
    /**
     * Remove pet with given id from the pet store.
     * @param petId id of pet to be removed
     * @return true when pet has been successfully removed, false otherwise
     */
    public boolean removePet(int petId) {
        Pet petRemoved = pets.remove(petId);
        if (petRemoved != null) {
            // Pet successfully removed
            return true;
        }
        // Pet not found
        return false;
    }
}
