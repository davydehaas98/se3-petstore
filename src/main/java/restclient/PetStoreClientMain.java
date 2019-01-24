/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.util.List;
import restshared.PetDTO;

/**
 * Main class for the pet store client.
 * @author Nico Kuijpers
 */
public class PetStoreClientMain {
    
    private static PetStoreClient petStoreClient;
    
    /**
     * Main method for the pet store client.
     * @param args
     */
    public static void main(String[] args) {
        
        // Create the pet store client
        petStoreClient = new PetStoreClient();
        
        // Inspect, add, change, and remove pets
        showAllPetsInStore();
        System.out.println("\n");
        System.out.println("Add Garfield");
        PetDTO garfield = petStoreClient.addPet("Garfield", "Jon");
        System.out.println("Pet " + garfield + " added to the store");
        System.out.println("\n");
        System.out.println("Add Odie");
        PetDTO odie = petStoreClient.addPet("Odie","Jon");
        System.out.println("Pet " + odie + " added to the store");
        System.out.println("\n");
        showAllPetsInStore();
        System.out.println("\n");
        System.out.println("Get the cat Garfield from the store");
        PetDTO cat = petStoreClient.getPet(garfield.getPetId());
        System.out.println("Cat " + cat + " obtained from the store");
        System.out.println("\n");
        System.out.println("Get the dog Odie from the store");
        PetDTO dog = petStoreClient.getPet(odie.getPetId());
        System.out.println("Dog " + dog + " obtained from the store");
        System.out.println("\n");
        showAllPetsInStoreWithOwner("Jon");
        System.out.println("\n");
        System.out.println("Change owner name of both pets from Jon to Jon Arbuckle");
        petStoreClient.changeOwner(garfield.getPetId(), "Jon Arbuckle");
        petStoreClient.changeOwner(odie.getPetId(), "Jon Arbuckle");
        showAllPetsInStoreWithOwner("Jon");
        showAllPetsInStoreWithOwner("Jon Arbuckle");
        System.out.println("\n");
        System.out.println("Remove Garfield from the store");
        petStoreClient.removePet(garfield.getPetId());
        showAllPetsInStore();
        System.out.println("\n");
        System.out.println("Remove Odie from the store");
        petStoreClient.removePet(odie.getPetId());
        showAllPetsInStore();
    }
    
    // Show data of all pets in the store
    private static void showAllPetsInStore() {
        List<PetDTO> allPets = petStoreClient.getAllPets();
        System.out.println("Number of pets in the store: " + allPets.size());
        if (allPets.size() > 0) {
            System.out.println("Pets in the store:");
            for (PetDTO p : allPets) {
                System.out.println("   " + p);
            }
        }
    }
    
    // Show data of all pets in the store with given owner
    private static void showAllPetsInStoreWithOwner(String ownerName) {
        List<PetDTO> allPetsWithOwner = petStoreClient.getAllPetsWithOwner(ownerName);
        if (allPetsWithOwner.size() == 0) {
            System.out.println("There are no pets in the store with owner " + ownerName);
        } else {
            System.out.println("Pets in the store with owner " + ownerName + " :");
            for (PetDTO p : allPetsWithOwner) {
                System.out.println("   " + p);
            }
        }
    }
}
