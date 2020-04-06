
import java.math.BigInteger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author user
 */
public class Project {

    
    public static void main(String[] args) {
       dbConnectivity db = dbConnectivity.getInstance();
       Zameen web=Zameen.getInstance();
       web.menue();
    }
 
    
}
