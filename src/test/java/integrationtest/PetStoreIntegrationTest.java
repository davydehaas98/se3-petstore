/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import restclient.PetStoreClient;
import static org.junit.Assert.*;
import restserver.PetStoreRESTService;
import restshared.PetDTO;

/**
 * Integration test for the pet store.
 * In this integration test, the pet store server is started and 
 * after one second all methods of the pet store client are invoked.
 * The actual results of the method calls are compared to expected results and
 * the state of the pet store is checked against the expected state.
 * @author Nico Kuijpers
 */
public class PetStoreIntegrationTest {
    
    private static PetStoreClient petStoreClient;
    
    public PetStoreIntegrationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        // Start the Pet Store Server
        ServletContextHandler context = new
                    ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server(8090);
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                PetStoreRESTService.class.getCanonicalName());
        
        // Start the jetty server in a separate thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jettyServer.start();
                    jettyServer.join();
                } catch (Exception ex) {
                    Logger.getLogger(PetStoreIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    jettyServer.destroy();
                }
            }

        });
        thread.start();

        // Create the pet store client
        petStoreClient = new PetStoreClient();
    }
    
    @AfterClass
    public static void tearDownClass() {
        // Nothing, the jetty server will stop when the test is finished
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPetStore() throws InterruptedException {
        
        // Wait one second for the server to start
        Thread.sleep(1000);
        
        // Beginning of the integration test
        System.out.println("BEGIN integration test for the pet store");
        
        // Add Garfield to the pet store
        PetDTO garfield = petStoreClient.addPet("Garfield", "Jon");
        assertEquals("Wrong pet name for Garfield","Garfield",garfield.getPetName());
        assertEquals("Wrong owner name for Garfield","Jon",garfield.getOwnerName());
        
        // Add Odie to the pet store
        PetDTO odie = petStoreClient.addPet("Odie","Jon");
        assertEquals("Wrong pet name for Odie","Odie",odie.getPetName());
        assertEquals("Wrong owner name for Odie","Jon",odie.getOwnerName());
        
        // There should be two pets in the pet store now
        List<PetDTO> allPets = petStoreClient.getAllPets();
        assertEquals("Wrong number of pets in the store",2,allPets.size());
        
        // Get the cat Garfield from the pet store using id obtained from the server
        PetDTO cat = petStoreClient.getPet(garfield.getPetId());
        assertEquals("Wrong pet name for the cat",cat.getPetName(),"Garfield");
        assertEquals("Wrong owner name for the cat",cat.getOwnerName(),"Jon");
        
        // Get the dog Odie from the pet store using id obtained from the server
        PetDTO dog = petStoreClient.getPet(odie.getPetId());
        assertEquals("Wrong pet name for the dog",dog.getPetName(),"Odie");
        assertEquals("Wrong owner name for the dog",dog.getOwnerName(),"Jon");
        
        // There should be two pets in the pet store with owner Jon
        List<PetDTO> allPetsWithOwnerJon = petStoreClient.getAllPetsWithOwner("Jon");
        assertEquals("Wrong number of pets with owner Jon",2,allPetsWithOwnerJon.size());
        
        // Change the name of the owner for Garfield from Jon to Jon Arbuckle
        boolean result = petStoreClient.changeOwner(garfield.getPetId(),"Jon Arbuckle");
        assertTrue("Change of owner name not succesful for Garfield",result);
        
        // Change the name of the owner for Odie from Jon to Jon Arbuckle
        result = petStoreClient.changeOwner(odie.getPetId(),"Jon Arbuckle");
        assertTrue("Change of owner name not succesful for Odie",result);
        
        // There should be zero pets in the pet store with owner Jon
        allPetsWithOwnerJon = petStoreClient.getAllPetsWithOwner("Jon");
        assertEquals("Wrong number of pets with owner Jon after name change",
                0,allPetsWithOwnerJon.size());
        
        // There should be two pets in the pet store with owner Jon Arbuckle
        List<PetDTO> allPetsWithOwnerJonArbuckle = petStoreClient.getAllPetsWithOwner("Jon Arbuckle");
        assertEquals("Wrong number of pets with owner Jon Arbuckle after name change",
                2,allPetsWithOwnerJonArbuckle.size());
        
        // Remove Garfield from the pet store
        result = petStoreClient.removePet(garfield.getPetId());
        assertTrue("Removing Garfield from the pet store not succesful",result);
        
        // There should be 1 pet in the pet store now
        allPets = petStoreClient.getAllPets();
        assertEquals("Wrong number of pets in the store after removing Garfield",
                1,allPets.size());
        
        // Remove Odie from the pet store
        result = petStoreClient.removePet(odie.getPetId());
        assertTrue("Removing Odie from the pet store not succesful",result);
        
        // There should be zero pets in the pet store now
        allPets = petStoreClient.getAllPets();
        assertEquals("Wrong number of pets in the store after removing Garfield and Odie",
                0,allPets.size());
        
        // End of the integration test
        System.out.println("END integration test for the pet store");
    }
}
