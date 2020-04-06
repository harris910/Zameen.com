
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Apartment extends House {
    Integer Floor;
    
    public Apartment(){
        
    }
    
    public Apartment(Integer Floor, Integer NoOfBedrooms, Integer NoOfWashrooms, Boolean Furnished, Integer PropertyNumber, String Address, Integer Area, String Type, String Price, String Kind, String Description, Integer Bookmarks, Boolean Feature,String UserName) {
        super(NoOfBedrooms, NoOfWashrooms, Furnished, PropertyNumber, Address, Area, Type, Price, Kind, Description, Bookmarks, Feature,UserName);
        this.Floor = Floor;
    }
    
    void Print(){
        super.Print();
        System.out.println("\t\t\tApartment has " + Floor + " Floors");
    }
    /////////////////////////////////////////////////////////////////////////////////////
    ///////kunwars code
    void setter()
    {
        super.setter();
        Scanner input=new Scanner(System.in);
        System.out.println("Enter Floor No: ");
        this.Floor = input.nextInt();
        
    }
    void propertyup()
    {
        super.propertyup();
        dbConnectivity db = dbConnectivity.getInstance();
        int s=db.getLatestHouse();
        db.insertApp(Floor, s);
        
    }

    
}
