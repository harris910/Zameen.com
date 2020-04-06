
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
public class House extends PropertyAd {
    Integer NoOfBedrooms;
    Integer NoOfWashrooms;
    Boolean Furnished;
    
    public House(){
        
    }

    public House(Integer NoOfBedrooms, Integer NoOfWashrooms, Boolean Furnished, Integer PropertyNumber, String Address, Integer Area, String Type, String Price, String Kind, String Description, Integer Bookmarks, Boolean Feature,String UserName) {
        super(PropertyNumber, Address, Area, Type, Price, Kind, Description, Bookmarks, Feature,UserName);
        this.NoOfBedrooms = NoOfBedrooms;
        this.NoOfWashrooms = NoOfWashrooms;
        this.Furnished = Furnished;
    }
    
    void Print()
    {
        super.Print();
        System.out.println("\t\t\tHouse has " + NoOfBedrooms + " Bedrooms");
        System.out.println("\t\t\tHouse has " + NoOfWashrooms + " Washrooms");
        if(Furnished == true){
            System.out.println("\t\t\tHouse is furnished");
        }
        else{
            System.out.println("\t\t\tHouse is not furnished");
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //////kunwars code

    
    
    void propertyup()
    {
        super.propertyup();
        dbConnectivity db =dbConnectivity.getInstance();
        int s=db.getlastestProp();
        db.insertHouse(NoOfBedrooms, NoOfWashrooms, Furnished, s);
        
    }
	
    void setter()
    {
        super.setter();
        Scanner input=new Scanner(System.in);
        
        
        System.out.println("Enter No of Bedrooms:");
        NoOfBedrooms = input.nextInt();
        System.out.println("Enter No of Washrooms");
        NoOfWashrooms = input.nextInt();
        System.out.println("Is Furnished? ");
        Furnished = input.nextBoolean();
           
       
    }

}
