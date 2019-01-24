/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restshared;

/**
 * Data Transfer Object (DTO) for Pet.
 * This class is used to communicate pet-data between client and server.
 * @author Nico Kuijpers
 */
public class PetDTO {
    // Identification of pet in pet store
    private int petId;
    
    // Name of pet
    private String petName;
    
    // Owner of pet
    private String ownerName;
    
    /**
     * No-argument constructor.
     * Note that a no-argument constructor is
     * required for data transfer objects when a
     * constructor with arguments is also declared.
     */
    public PetDTO() {
        // Nothing
    }
    
    /**
     * Constructor.
     * @param petId     Identification of pet
     * @param petName   Name of pet
     * @param ownerName Owner of pet
     */
    public PetDTO(int petId, String petName, String ownerName) {
        this.petId = petId;
        this.petName = petName;
        this.ownerName = ownerName;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    @Override
    public String toString() {
        return "id: " + petId + " name: " + petName + " owner: " + ownerName;
    }
}
