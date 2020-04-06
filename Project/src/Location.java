/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Location {
    String Country;
    String City;
    String StreetNo;
    String Block;
    String Society;

    public Location(String Country, String City, String StreetNo, String Block, String Society) {
        this.Country = Country;
        this.City = City;
        this.StreetNo = StreetNo;
        this.Block = Block;
        this.Society = Society;
    }
    
    void Print(){
        
        System.out.println("\t\t\tCountry :"+Country);
        System.out.println("\t\t\tCity :"+City);
        System.out.println("\t\t\tStreetNo :"+StreetNo);
        System.out.println("\t\t\tSociety :"+Society);
        System.out.println("\t\t\tBlock :"+Block);
    }
}
