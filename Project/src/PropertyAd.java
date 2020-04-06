
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
public class PropertyAd {
    Integer PropertyNumber;
    String Address;
    Integer Area;
    String Type;//Residencial or commercial
    String Price;
    String Kind;//Rent or sell
    String Description;
    Integer Bookmarks;//Keep the count of bookmarks
    Boolean Feature;
    String UserName;
    
    int count;
    
    public PropertyAd()
    {
        
    }

    public Boolean getFeature() {
        return Feature;
    }

    
    public PropertyAd(Integer PropertyNumber, String Address, Integer Area, String Type, String Price, String Kind, String Description, Integer Bookmarks, Boolean Feature,String UserName) {
        this.PropertyNumber = PropertyNumber;
        this.Address = Address;
        this.Area = Area;
        this.Type = Type;
        this.Price = Price;
        this.Kind = Kind;
        this.Description = Description;
        this.Bookmarks = Bookmarks;
        this.Feature = Feature;
        this.UserName=UserName;
    }
    
    void Print(){
        System.out.println("\n\n");
        System.out.println("\t\t\tProperty count is: "+ count);
        System.out.println("\t\t\tProperty Belongs to: "+ UserName);
        System.out.println("\t\t\tProperty number is: "+ PropertyNumber);
        System.out.println("\t\t\tProperty address is: "+ Address);
        System.out.println("\t\t\tProperty Area is "+ Area + " Kanal");
        if("1".equals(Type)){
            System.out.println("\t\t\tProperty is: Residential");
        }
        else if("2".equals(Type)){
            System.out.println("\t\t\tProperty is: Commercial");
        }
        System.out.println("\t\t\tProperty Price is: "+ Price);
        if("sale".equals(Kind)){
            System.out.println("\t\t\tProperty is: for sale");
        }
        else if("rent".equals(Kind)){
            System.out.println("\t\t\tProperty is: for rent");
        }
        System.out.println("\t\t\tProperty Description: "+ Description);
        if(Feature == true){
            System.out.println("\t\t\tProperty is Featured");           
        }
        else if(Feature == false){
            System.out.println("\t\t\tProperty is not Featured");
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////kunwars code
    void propertyup()
    {
        dbConnectivity db = dbConnectivity.getInstance();
        db.insertProperty(PropertyNumber, Address, Area, Type, Price, Kind, Description, Bookmarks, Feature, UserName);//owner.Username instead of userName
    }
    
    void editPrice(String Price)
    {
      dbConnectivity db = dbConnectivity.getInstance();
      this.Price=Price;
      db.editPrice(PropertyNumber, Address, Price);
    }
    
    void editDesc(String Desc)
    {
        dbConnectivity db = dbConnectivity.getInstance();
        this.Description=Desc;
        db.editDesc(PropertyNumber, Address, Desc);
    }
    
    
    void setter()
    {
        Scanner input=new Scanner(System.in);
       
        System.out.println("Enter Property No: ");
        PropertyNumber=input.nextInt();
        Address = input.nextLine();
        System.out.println("Enter Address:");
        Address = input.nextLine();
        System.out.println("Enter Area in Kanals");
        Area = input.nextInt();
        Type = input.nextLine();
        System.out.println("Enter Type(Residencial / Commercial)");
        Type = input.nextLine();
        System.out.println("Enter Price");
        Price = input.nextLine();
        System.out.println("Enter Kind(Sale / Rent)");
        Kind = input.nextLine();
        System.out.println("Enter Description");
        Description = input.nextLine();
       
        this.Bookmarks=0;
        this.Feature=false;
       
    }
    
    
    void delete()
    {
        dbConnectivity db =dbConnectivity.getInstance();
        db.deleteProp(PropertyNumber, Address);
    }
    
}
