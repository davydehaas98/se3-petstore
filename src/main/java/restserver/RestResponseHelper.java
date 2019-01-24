package restserver;

import com.google.gson.Gson;
import petstore.Pet;
import restshared.PetDTO;
import restshared.PetStoreResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to convert domain model to rest response string
 */
public class RestResponseHelper {

    private static final Gson gson = new Gson();

    public static String getErrorResponseString()
    {
        PetStoreResponse response = new PetStoreResponse();
        response.setSuccess(false);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getSinglePetResponse(Pet petFromStore)
    {
        PetStoreResponse response = new PetStoreResponse();
        response.setSuccess(true);
        List<PetDTO> pets = new ArrayList<>();
        PetDTO pet = petFromStore.createDTO();
        pets.add(pet);
        response.setPets(pets);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getSuccessResponse(boolean success)
    {
        PetStoreResponse response = new PetStoreResponse();
        response.setSuccess(success);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getAllPetsResponse(List<PetDTO> allPets)
    {
        PetStoreResponse response = new PetStoreResponse();
        response.setSuccess(true);
        response.setPets(allPets);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static List<PetDTO> getPetDTOList(List<Pet> allPetsFromStore)
    {
        List<PetDTO> allPets = new ArrayList<>();
        for (Pet p : allPetsFromStore) {
            PetDTO pet = p.createDTO();
            allPets.add(pet);
        }
        return allPets;
    }
}
