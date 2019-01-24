/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restserver;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import petstore.Pet;
import petstore.PetStore;
import restshared.PetDTO;

/**
 * REST service for pet store.
 * Note that the service is stateless. 
 * The state of the pet store is maintained within the pet store.
 * The singleton pattern is used for the pet store to ensure that 
 * only one instance of the pet store is being created.
 * @author Nico Kuijpers
 */
@Path("/petstore")
public class PetStoreRESTService {

    public PetStoreRESTService() {
        // Nothing
    }

    /**
     * Find pet by ID.
     * @param petIdAsString pet id (as string)
     * @return the pet with given id
     */
    @GET
    @Path("/pet/{petid}")
    @Produces("application/json")
    public Response getPet(@PathParam("petid") String petIdAsString) {
        
        System.out.println("[Server getPet]");
        
        // Find pet
        int petId = Integer.parseInt(petIdAsString);
        Pet petFromStore = PetStore.getInstance().getPet(petId);
        
        // Check whether pet exists
        if (petFromStore == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }
        
        // Define response
        return Response.status(200).entity(RestResponseHelper.getSinglePetResponse(petFromStore)).build();
    }
    
    /**
     * Get all pets in the pet store.
     * @return all pets
     */
    @GET
    @Path("/pet/all")
    @Produces("application/json")
    public Response getAllPets() {
        
        System.out.println("[Server getAllPets]");
        
        // Get all pets from the store
        List<Pet> allPetsFromStore = PetStore.getInstance().getAllPets();

        // Define response
        return Response.status(200).entity(RestResponseHelper.getAllPetsResponse(RestResponseHelper.getPetDTOList(allPetsFromStore))).build();
    }
    
    /**
     * Get all pets with owner corresponding to given owner name.
     * @param ownerName name of the owner
     * @return all pets from given owner
     */
    @GET
    @Path("/pet/findByOwner/{owner}")
    @Produces("application/json")
    public Response getAllPetsWithOwner(@PathParam("owner") String ownerName) {
        
        System.out.println("[Server getAllPetsWithOwner]");
        
        // Get all pets from the store with given owner name
        List<Pet> allPetsFromStoreWithOwner = PetStore.getInstance().getAllPetsWithOwner(ownerName);

        // Define response
        return Response.status(200).entity(RestResponseHelper.getAllPetsResponse(RestResponseHelper.getPetDTOList(allPetsFromStoreWithOwner))).build();
    }

    /**
     * Add a new pet to the store.
     * @param petRequest data of the new pet
     * @return the new pet
     */
    @POST
    @Path("/pet")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPet(PetDTO petRequest) {
        
        System.out.println("[Server addPet]");
        
        // Check request
        if (petRequest == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }
        
        // Add pet to store
        String petName = petRequest.getPetName();
        String ownerName = petRequest.getOwnerName();
        Pet newPet = PetStore.getInstance().addPet(petName,ownerName);
        
        // Define response
        return Response.status(200).entity(RestResponseHelper.getSinglePetResponse(newPet)).build();
    }
    
    /**
     * Change the name of the owner of a pet.
     * @param petRequest contains the id of the pet and the new owner name
     * @return success if name of owner has been changed, otherwise bad request
     */
    @PUT
    @Path("/pet/changeOwner")
    @Consumes("application/json")
    @Produces("application/json")
    public Response changeOwner(PetDTO petRequest) {
        
        System.out.println("[Server changeOwner]");
        
        // Check request
        if (petRequest == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }
        
        // Change owner of the pet
        int petId = petRequest.getPetId();
        String ownerName = petRequest.getOwnerName();
        boolean success = PetStore.getInstance().changeOwner(petId,ownerName);
        
        // Define response
        return Response.status(200).entity(RestResponseHelper.getSuccessResponse(success)).build();
    }
    
    /**
     * Remove pet with given pet id.
     * @param petIdAsString pet id (as string)
     * @return success if pet has been removed, otherwise bad request
     */
    @DELETE
    @Path("/pet/{petid}")
    @Produces("application/json")
    public Response removePet(@PathParam("petid") String petIdAsString) {
        
        System.out.println("[Server removePet]");
        
        // Find pet to be removed
        int petId = Integer.parseInt(petIdAsString);
        Pet petFromStore = PetStore.getInstance().getPet(petId);
        
        // Check whether pet exists
        if (petFromStore == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }
        
        // Remove pet from the store
        boolean success = PetStore.getInstance().removePet(petId);
        
        // Return response
        return Response.status(200).entity(RestResponseHelper.getSuccessResponse(success)).build();
    }
}
