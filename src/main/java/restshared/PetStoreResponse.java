/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restshared;

import java.util.List;

/**
 * Response from the REST service.
 * @author Nico Kuijpers
 */
public class PetStoreResponse {
    
    // Indicates whether REST call was successful
    private boolean success;
    
    // List of pets
    private List<PetDTO> pets;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }
}
