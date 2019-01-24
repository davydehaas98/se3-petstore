/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petstore;

import restshared.PetDTO;

/**
 * Pet in the pet store.
 * @author Nico Kuijpers
 */
public class Pet {
    
    // Identification of pet
    private int petId;
    
    // Name of pet
    private String petName;
    
    // Owner of pet
    private String ownerName;
    
    /**
     * Constructor.
     * @param petId  id of the pet
     * @param petName name of the pet
     * @param ownerName name of the owner
     */
    public Pet(int petId, String petName, String ownerName) {
        this.petId = petId;
        this.petName = petName;
        this.ownerName = ownerName;
    }

    /**
     * Get id of this pet.
     * @return
     */
    public int getPetId() {
        return petId;
    }

    /**
     * Set id of this pet.
     * @param petId new id for this pet
     */
    public void setPetId(int petId) {
        this.petId = petId;
    }

    /**
     * Get the name of this pet.
     * @return name of this pet.
     */
    public String getPetName() {
        return petName;
    }

    /**
     * Set the name of this pet.
     * @param petName new name for this pet
     */
    public void setPetName(String petName) {
        this.petName = petName;
    }

    /**
     * Get the name of the owner of this pet.
     * @return name of the owner of this pet
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Set the name of the owner for this pet.
     * @param ownerName new name of the owner
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    /**
     * Create a Data Transfer Object (DTO) for this pet.
     * @return DTO for this pet
     */
    public PetDTO createDTO () {
        return new PetDTO(petId,petName,ownerName);
    }
}
