package restclient;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import restshared.PetDTO;
import restshared.PetStoreResponse;


/**
 * Client-side of REST service for pet store.
 * @author Nico Kuijpers
 */
public class PetStoreClient {
    
    private final String url = "http://localhost:8090/petstore";
    
    private final Gson gson = new Gson();
    
    private final int NOTDEFINED = -1;
    
    public PetStoreClient() {
        // Nothing
    }

    /**
     * Get pet with given petId.
     * @param petId petId
     * @return the pet with given petId
     */
    public PetDTO getPet(int petId) {
        String queryGet = "/pet/" + petId;
        PetStoreResponse response = executeQueryGet(queryGet);
        return response.getPets().get(0);
    }
    
    /**
     * Get all pets in the pet store.
     * @return all pets
     */
    public List<PetDTO> getAllPets() {
        String queryGet = "/pet/all";
        PetStoreResponse response = executeQueryGet(queryGet);
        return response.getPets();
    }
    
    /**
     * Get all pets with owner corresponding to given owner name.
     * @param ownerName name of the owner
     * @return all pets from given owner
     */
    public List<PetDTO> getAllPetsWithOwner(String ownerName) {
        // Note that spaces are not allowed in a query
        // Replace each space with %20
        String ownerNameWithSpacesReplaced = ownerName.replace(" ","%20");
        String queryGet = "/pet/findByOwner/" + ownerNameWithSpacesReplaced;
        PetStoreResponse response = executeQueryGet(queryGet);
        return response.getPets();
    }
    
    /**
     * Add a new pet to the store.
     * @param petName name of the new pet
     * @param ownerName name of the owner
     * @return the new pet
     */
    public PetDTO addPet(String petName, String ownerName) {
        PetDTO petRequest = new PetDTO(NOTDEFINED,petName,ownerName);
        String queryPost = "/pet";
        PetStoreResponse response = executeQueryPost(petRequest,queryPost);
        return response.getPets().get(0);
    }

    /**
     * Change the name of the owner of a pet.
     * @param petId id of the pet
     * @param ownerName new owner name
     * @return success if name of owner has been changed, otherwise false
     */
    public boolean changeOwner(int petId, String ownerName) {
        PetDTO petRequest = new PetDTO(petId,"",ownerName);
        String queryPut = "/pet/changeOwner";
        PetStoreResponse response = executeQueryPut(petRequest,queryPut);
        return response.isSuccess();
    }

     /**
     * Remove pet with given pet id.
     * @param petId pet id
     * @return true if pet has been removed, otherwise false
     */
    public boolean removePet(int petId) {
        String queryDelete = "/pet/" + petId;
        PetStoreResponse response = executeQueryDelete(queryDelete);
        return response.isSuccess();
    }
    
    private PetStoreResponse executeQueryGet(String queryGet) {

        // Build the query for the REST service
        final String query = url + queryGet;
        System.out.println("[Query Get] : " + query);

        // Execute the HTTP GET request
        HttpGet httpGet = new HttpGet(query);
        return executeHttpUriRequest(httpGet);
    }

    private PetStoreResponse executeQueryPost(PetDTO petRequest, String queryPost) {

        // Build the query for the REST service
        final String query = url + queryPost;
        System.out.println("[Query Post] : " + query);

        // Execute the HTTP POST request
        HttpPost httpPost = new HttpPost(query);
        httpPost.addHeader("content-type", "application/json");
        StringEntity params;
        try {
            params = new StringEntity(gson.toJson(petRequest));
            httpPost.setEntity(params);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PetStoreClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeHttpUriRequest(httpPost);
    }

    private PetStoreResponse executeQueryPut(PetDTO petRequest, String queryPut) {

        // Build the query for the REST service
        final String query = url + queryPut;
        System.out.println("[Query Put] : " + query);

        // Execute the HTTP PUT request
        HttpPut httpPut = new HttpPut(query);
        httpPut.addHeader("content-type", "application/json");
        StringEntity params;
        try {
            params = new StringEntity(gson.toJson(petRequest));
            httpPut.setEntity(params);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PetStoreClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeHttpUriRequest(httpPut);
    }

    private PetStoreResponse executeQueryDelete(String queryDelete) {

        // Build the query for the REST service
        final String query = url + queryDelete;
        System.out.println("[Query Delete] : " + query);

        // Execute the HTTP DELETE request
        HttpDelete httpDelete = new HttpDelete(query);
        return executeHttpUriRequest(httpDelete);
    }

    private PetStoreResponse executeHttpUriRequest(HttpUriRequest httpUriRequest) {

        // Execute the HttpUriRequest
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(httpUriRequest)) {
            System.out.println("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println("[Entity] : " + entityString);
            PetStoreResponse petStoreResponse = gson.fromJson(entityString, PetStoreResponse.class);
            return petStoreResponse;
        } catch (IOException e) {
            System.out.println("IOException : " + e.toString());
            PetStoreResponse petStoreResponse = new PetStoreResponse();
            petStoreResponse.setSuccess(false);
            return petStoreResponse;
        } catch (JsonSyntaxException e) {
            System.out.println("JsonSyntaxException : " + e.toString());
            PetStoreResponse petStoreResponse = new PetStoreResponse();
            petStoreResponse.setSuccess(false);
            return petStoreResponse;
        }
    }
}
