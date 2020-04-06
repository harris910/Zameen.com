/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
import java.util.Scanner;
public class Plaza extends PropertyAd {
    Integer NoOfFloors;

    public Plaza(){
        
    }
    
    public Plaza(Integer NoOfFloors, Integer PropertyNumber, String Address, Integer Area, String Type, String Price, String Kind, String Description, Integer Bookmarks, Boolean Feature,String UserName) {
        super(PropertyNumber, Address, Area, Type, Price, Kind, Description, Bookmarks, Feature,UserName);
        this.NoOfFloors = NoOfFloors;
    }
    
    void Print(){
        super.Print();
        System.out.println("\t\t\tPlaza has " + NoOfFloors + " Floors");
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    ////////kunwars code
        void setter()
    {
        super.setter();
        Scanner input=new Scanner(System.in);
        System.out.println("Enter no of Floors: ");
        this.NoOfFloors=input.nextInt();
    }
    
    
    
    void propertyup()
    {
        super.propertyup();
        dbConnectivity db = dbConnectivity.getInstance();
        int s=db.getlastestProp();
        db.insertPlaza(NoOfFloors, s);
        
    }

    
    
    
}
