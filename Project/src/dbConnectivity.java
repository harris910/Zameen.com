/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author user
 */
public class dbConnectivity {           //singleton
    
    private static dbConnectivity obj=new dbConnectivity();
    
    Connection con;
    Statement stmt;
    
    public static dbConnectivity getInstance() 
    { 
        return obj; 
    }
    
    private dbConnectivity() //cons
    {
        try
        {
            String s = "jdbc:sqlserver://DESKTOP-LG4M17M:1433;databaseName=ooad";
            con=DriverManager.getConnection(s,"helloworld","1234");

            stmt = con.createStatement(); 
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    void displayUser()
    {
        
        try
        {
            ResultSet rs=stmt.executeQuery("select * from [User]");
             while(rs.next())
             {
                System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+"  "+rs.getString(5));
             }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    //this will create the user(register)
   void insertUser(String name, String email, String phoneNo, String password, String userName)
   {
        try
        {
            String query = "insert into [User] values(?,?,?,?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, name);
             preparedStmt.setString(2, email);
             preparedStmt.setString(3, phoneNo);
             preparedStmt.setString(4, password);
             preparedStmt.setString(5,userName);

             preparedStmt.executeUpdate();
         }
      
        catch(SQLException e)
        {
            if(e.getErrorCode()==2627)
            {
                System.out.println("UserName Already Taken......Please Select another UserName ");
                
            }
            else
                System.out.println(e);
        }
   }
   
   void insertAgent(String UserName,String NameOfRealEstate, String Nic)
   {
               try
        {
            String query = "insert into [RealEstateAgent] values(?,?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, NameOfRealEstate);
             preparedStmt.setString(2, Nic);
             preparedStmt.setString(3, UserName);

             preparedStmt.executeUpdate();
         }
      
        catch(SQLException e)
        {
           
                System.out.println(e);
        }
   }
   
   //updates for User////////////////////////////////////////////////////////////////////////
   void updateUserName(String name, String userName)
   {
        try
        {
            String query = "update [User] set  Name = ? where UserName = ?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, name);
             preparedStmt.setString(2,userName);
             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
   void updateUserPno(String pno, String userName)
   {
        try
        {
            String query = "update [User] set  PhoneNumber = ? where UserName = ?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, pno);
             
             preparedStmt.setString(2,userName);
             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
   
   void updatePassword(String password, String userName)
   {
        try
        {
            String query = "update [User] set  Password = ? where UserName = ?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, password);
             preparedStmt.setString(2,userName);
             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   /////////////////////////////////////////////////////////
   ///Delete User/////////////////////////////////////////
   void deleteUser(String userName)
   {
        try
        {
            String query = "delete from [User] where UserName = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
             
             preparedStmt.setString(1, userName);

             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   ////////////////////////////////////////////////////////
   ////Validate User///////////////////////////////////////
   
   int validateUser(String userName,String password)
   {
        try
        {
            String query="select Password from [User] where UserName = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();

            String pass=null;

            while(rs.next())
            {
                pass=rs.getString(1);
            }
            
            if(pass.equals(password))
                return 1;//password matches userName
            else
                return 0;//password doesnot match UserName
            
        }
        catch(Exception e)
        {
            System.out.println("The UserName doesnot exit in database");
            Zameen z=Zameen.getInstance();
            z.Login();
        }
        return 2;//userName doesnot exit
       
   }
   
   
   
   ////////////////////////////////////////////////////////
   //this will get the user
   User getUser(String userName,String password)
   {
       User obj=null;
        try
        {
            String query="select * from getUser where UserName = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();
            String Name=null;
            String Email=null;
            String Pno=null;
            String pass=null;
            String UserName=null;
            String NameOfRealEstate=null;
            String NIC=null;
            
            while(rs.next())
            {
                Name=rs.getString(1);
                Email=rs.getString(2);
                Pno=rs.getString(3);
                pass=rs.getString(4);
                UserName=rs.getString(5);    
                obj = new User(Name,Email,Pno,pass,UserName);
            }
           
            
            query="select * from getAgent where UserName = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                Name=rs.getString(4);
                Email=rs.getString(3);
                Pno=rs.getString(6);
                pass=rs.getString(5);
                UserName=rs.getString(7);
                NameOfRealEstate=rs.getString(1);
                NIC=rs.getString(2); 
                obj = new RealEstate(Name,Email,Pno,pass,UserName,NameOfRealEstate,NIC);
            }
            
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        return obj;
   }
   //////////////////////////////////////////////////////////
   ////get the list of registered users
   List<User> getUser()
   {
       List<User> obj=new ArrayList<User>();
        try
        {
            String query="select * from getUser";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            String Name=null;
            String Email=null;
            String Pno=null;
            String pass=null;
            String UserName=null;
            while(rs.next())
            {
                Name=rs.getString(1);
                Email=rs.getString(2);
                Pno=rs.getString(3);
                pass=rs.getString(4);
                UserName=rs.getString(5);
                User temp = new User(Name,Email,Pno,pass,UserName);
                obj.add(temp);
            }
            
            query="select * from getAgent";
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            Name=null;
            Email=null;
            Pno=null;
            pass=null;
            UserName=null;
            String NameOfRealEstate=null;
            String NIC=null;
            while(rs.next())
            {
                Name=rs.getString(4);
                Email=rs.getString(3);
                Pno=rs.getString(6);
                pass=rs.getString(5);
                UserName=rs.getString(7);
                NameOfRealEstate=rs.getString(1);
                NIC=rs.getString(2);
                User temp = new RealEstate(Name,Email,Pno,pass,UserName,NameOfRealEstate,NIC);
                obj.add(temp);
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        return obj;
   }

   //////////////////////////////////////////////////////////
   //this will get the payments of the user
   List<Payment> getPayment(String userName)
   {
       List<Payment>payments=new ArrayList<Payment>();
       try
       {
            String query="select * from [Payment] where UserName = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();
            
            String cardNumber=null;
            String securityPin=null;
            Date expiryDate=null;
            
            while(rs.next())
            {
                cardNumber=rs.getString(2);
                securityPin=rs.getString(3);
                expiryDate=new Date(); 
                Payment obj=new Payment(cardNumber,securityPin,expiryDate);
                payments.add(obj);
                
            }
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return payments;
   }
   //this will get the list of the location of the user
   List<Location> getLocation(String userName){
       
       List<Location>locations=new ArrayList<Location>();
       try
       {
            String query="select * from [Location] where UserName = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();
            
            String country=null;
            String city=null;
            String streetNo=null;
            String block=null;
            String society=null;
            
            
            while(rs.next())
            {
                country=rs.getString(2);
                city=rs.getString(3);
                streetNo=rs.getString(4);
                block=rs.getString(5);
                society=rs.getString(6);
                
                Location obj=new Location(country,city,streetNo,block,society);
                locations.add(obj);
                
            }
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return locations;  
   }
   //this will get the published ads of the user
   List<PropertyAd> getPublishedAds(String userName){//this is returning the list of user published ads which include diff types
       
       List<PropertyAd>publishedAds=new ArrayList<PropertyAd>();
       try
       {

            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            int noOfFloors;//for plaza
            int noOfBedrooms;//for house
            int noOfWashrooms;//for house
            boolean furnished;//for house
            int floor;//for apartment
            
            
            String query="select * from [propdet] where UserName = ? order by Feature Desc";          //get the user propertyAds
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(2);
                address=rs.getString(3);
                area=rs.getInt(4);
                type=rs.getString(5);
                price=rs.getString(6);
                kind=rs.getString(7);
                description=rs.getString(8);
                bookmark=rs.getInt(9);
                feature=rs.getBoolean(10);
                
                PropertyAd obj=new PropertyAd(propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [Appdet] where UserName = ? order by Feature Desc";          //get the user appartments
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                furnished=rs.getBoolean(12);
                noOfBedrooms=rs.getInt(13);
                noOfWashrooms=rs.getInt(14);
                
                PropertyAd obj= new Apartment(floor,noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [housedet] where UserName = ? order by Feature Desc";          //get the user houses
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                furnished=rs.getBoolean(11);
                noOfBedrooms=rs.getInt(12);
                noOfWashrooms=rs.getInt(13);
                
                PropertyAd obj= new House(noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [plazadet] where UserName = ? order by Feature Desc";          //get the user plazas
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                noOfFloors=rs.getInt(11);
                PropertyAd obj= new Plaza(noOfFloors,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return publishedAds;  
   }
   /////get the all the published ads on zameen.com
   List<PropertyAd> getPublishedAds(){//this is returning the list of zameen.com published ads which include diff types
       
       List<PropertyAd>publishedAds=new ArrayList<PropertyAd>();
       try
       {

            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            int noOfFloors;//for plaza
            int noOfBedrooms;//for house
            int noOfWashrooms;//for house
            boolean furnished;//for house
            int floor;//for apartment
            String userName=null;
            
            String query="select * from [propdet] order by Feature Desc";          //get the propertyAds
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(2);
                address=rs.getString(3);
                area=rs.getInt(4);
                type=rs.getString(5);
                price=rs.getString(6);
                kind=rs.getString(7);
                description=rs.getString(8);
                bookmark=rs.getInt(9);
                feature=rs.getBoolean(10);
                userName=rs.getString(11);
                
                PropertyAd obj=new PropertyAd(propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [Appdet] order by Feature Desc";          //get the zameen.com appartments
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                furnished=rs.getBoolean(12);
                noOfBedrooms=rs.getInt(13);
                noOfWashrooms=rs.getInt(14);
                userName=rs.getString(15);
                
                PropertyAd obj= new Apartment(floor,noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [housedet] order by Feature Desc";          //get the zameen.com houses
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                furnished=rs.getBoolean(11);
                noOfBedrooms=rs.getInt(12);
                noOfWashrooms=rs.getInt(13);
                userName=rs.getString(14);
                
                PropertyAd obj= new House(noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [plazadet] order by Feature Desc";          //get the zameen.com plazas
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                noOfFloors=rs.getInt(11);
                userName=rs.getString(12);
                
                PropertyAd obj= new Plaza(noOfFloors,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return publishedAds;  
   }
   ///////////////////////////////////////////////////////////////////////
   
   
   
   //this will get the list of bookmark Users
   List<PropertyAd> getBookmarkAds(String userName){//this is returning the list of user bookmark ads which include diff types
       
       List<PropertyAd>bookmarkAds=new ArrayList<PropertyAd>();
       try
       {

            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            int noOfFloors;//for plaza
            int noOfBedrooms;//for house
            int noOfWashrooms;//for house
            boolean furnished;//for house
            int floor;//for apartment
            String UName=null;
            
            String query="select * from [propdet] where [propdet].Id IN (select PropertyAdID from Bookmarks where UserName = ?)";          //get the user propertyAds
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(2);
                address=rs.getString(3);
                area=rs.getInt(4);
                type=rs.getString(5);
                price=rs.getString(6);
                kind=rs.getString(7);
                description=rs.getString(8);
                bookmark=rs.getInt(9);
                feature=rs.getBoolean(10);
                UName=rs.getString(11);
                
                PropertyAd obj=new PropertyAd(propertyNo,address,area,type,price,kind,description,bookmark,feature,UName);
                bookmarkAds.add(obj);  
            }
            
            query="select * from [Appdet] where [Appdet].Id IN (select PropertyAdID from Bookmarks where UserName = ?)";          //get the user appartments
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                furnished=rs.getBoolean(12);
                noOfBedrooms=rs.getInt(13);
                noOfWashrooms=rs.getInt(14);
                UName=rs.getString(15);
                
                PropertyAd obj= new Apartment(floor,noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,UName);
                bookmarkAds.add(obj);  
            }
            
            query="select * from [housedet] where [housedet].Id IN (select PropertyAdID from Bookmarks where UserName = ?)";          //get the user houses
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                furnished=rs.getBoolean(11);
                noOfBedrooms=rs.getInt(12);
                noOfWashrooms=rs.getInt(13);
                UName=rs.getString(14);
                
                PropertyAd obj= new House(noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,UName);
                bookmarkAds.add(obj);  
            }
            
            query="select * from [plazadet] where [plazadet].Id IN (select PropertyAdID from Bookmarks where UserName = ?)";          //get the user plazas
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                noOfFloors=rs.getInt(11);
                UName=rs.getString(12);
                PropertyAd obj= new Plaza(noOfFloors,propertyNo,address,area,type,price,kind,description,bookmark,feature,UName);
                bookmarkAds.add(obj);  
            }
            
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return bookmarkAds;  
   }
   
   
   
   List<PropertyAd> getAppartments(String userName){//returns the appartments according to username
       
       List<PropertyAd>publishedApartments=new ArrayList<PropertyAd>();
       try
       {
            String query="select * from [Appdet] where UserName = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();
            //c.Address,c.Area,c.Bookmark,c.Description,c.Feature,c.Kind,c.Price,c.PropertyNumber,c.Type,b.Floor,a.Furnished,a.NoOfBedrooms,a.NoOfWashrooms,c.UserName
            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            boolean Furnished;
            int floor;
            int NoOfBedrooms;
            int NoOfWashrooms;
            
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                Furnished=rs.getBoolean(12);
                NoOfBedrooms=rs.getInt(13);
                NoOfWashrooms=rs.getInt(14);
                String UName=rs.getString(15);
                //Integer Floor, Integer NoOfBedrooms, Integer NoOfWashrooms, Boolean Furnished, Integer PropertyNumber, String Address, Integer Area, String Type, String Price, String Kind, String Description, Integer Bookmarks, Boolean Feature
                if(address!=null)
                {
                    PropertyAd obj= new Apartment(floor,NoOfBedrooms,NoOfWashrooms,Furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,UName);
                    publishedApartments.add(obj);
                }
                
                
            }
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return publishedApartments;  
   }
   
   
   ////////this will get the articles of specific user
   List<Article> getArticles(String userName){
       
       List<Article>articles=new ArrayList<Article>();
       try
       {
            String query="select * from [Article] where UserName = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();
            
            String name=null;
            String mainBody=null;
            Date datePublish=null;
            
            
            while(rs.next())
            {
                name=rs.getString(2);
                mainBody=rs.getString(3);
                datePublish=(java.sql.Date)rs.getDate(4);
            
                Article obj=new Article(name,mainBody,datePublish,userName);
                articles.add(obj);
                
            }
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return articles;  
   }
   
   ////////////////////////////////////////////////////////////////
   
   /////this will get all the article for blog
   List<Article> getArticles(){
       
       List<Article>articles=new ArrayList<Article>();
       try
       {
            String query="select * from [Article]";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            
            String name=null;
            String mainBody=null;
            Date datePublish=null;
            String userName=null;
            
            while(rs.next())
            {
                name=rs.getString(2);
                mainBody=rs.getString(3);
                datePublish=(java.sql.Date)rs.getDate(4);
                userName=rs.getString(5);
                Article obj=new Article(name,mainBody,datePublish,userName);
                articles.add(obj);
                
            }
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return articles;  
   }
  ///////////////////////////////////////////////////////////////////////////////
   
  ///////get the searched ads list
   List<PropertyAd> getSearchedAds(String SearchedKeyword){//this is returning the list of searched zameen.com published ads which include diff types
       
       List<PropertyAd>publishedAds=new ArrayList<PropertyAd>();
       try
       {
            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            int noOfFloors;//for plaza
            int noOfBedrooms;//for house
            int noOfWashrooms;//for house
            boolean furnished;//for house
            int floor;//for apartment
            String userName=null;
            
            String query="select * from [propdet] where [Address] Like '%'+?+'%'";  //get searched the propertyAds
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,SearchedKeyword);
            ResultSet  rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(2);
                address=rs.getString(3);
                area=rs.getInt(4);
                type=rs.getString(5);
                price=rs.getString(6);
                kind=rs.getString(7);
                description=rs.getString(8);
                bookmark=rs.getInt(9);
                feature=rs.getBoolean(10);
                userName=rs.getString(11);
                
                PropertyAd obj=new PropertyAd(propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [Appdet] where [Address] Like '%'+?+'%'";          //get the searched zameen.com appartments
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,SearchedKeyword);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                furnished=rs.getBoolean(12);
                noOfBedrooms=rs.getInt(13);
                noOfWashrooms=rs.getInt(14);
                userName=rs.getString(15);
                
                PropertyAd obj= new Apartment(floor,noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [housedet] where [Address] Like '%'+?+'%'";          //get the searched zameen.com houses
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,SearchedKeyword);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                furnished=rs.getBoolean(11);
                noOfBedrooms=rs.getInt(12);
                noOfWashrooms=rs.getInt(13);
                userName=rs.getString(14);
                
                PropertyAd obj= new House(noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
            query="select * from [plazadet] where [Address] Like '%'+?+'%'";          //get the searched zameen.com plazas
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,SearchedKeyword);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                noOfFloors=rs.getInt(11);
                userName=rs.getString(12);
                PropertyAd obj= new Plaza(noOfFloors,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                publishedAds.add(obj);  
            }
            
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return publishedAds;  
   }
  ////////////////////////////////////////////////////////////////////////////////////
   
   
   //////get trends from db
    List<Trend> getTrends(){
       
       List<Trend>Trends=new ArrayList<Trend>();
       try
       {
            String query="select * from [Trend] ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            
            String searchedKeyword=null;
            int searchedCount;
  
            while(rs.next())
            {
                searchedKeyword=rs.getString(2);
                searchedCount=rs.getInt(3);

                Trend obj=new Trend(searchedKeyword,searchedCount);
                Trends.add(obj);
                
            }
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return Trends;  
   }
   //////////////////////////////////////////////////////
   //////Update trend
   void updateTrend(int count, String searchedKeyword)
   {
        try
        {
            String query = "update [Trend] set  TimesSearched = ? where SearchedKeyword = ?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setInt(1, count);
             preparedStmt.setString(2,searchedKeyword);
             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   ///////////////////////////////////////////////////////
  
   





////////////////////////////////////////////////
////Create trend 
    void insertTrend(String searchedKeyword, int count)
   {
        try
        {
            String query = "insert into [Trend] values(?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, searchedKeyword);
             preparedStmt.setInt(2, count);
             preparedStmt.executeUpdate();
         }
      
        catch(SQLException e)
        {
            System.out.println(e);
        }
   }
  //////////////////////////////////////////////////////////////////
    
    ///////////////////top 5 trendings
    List<Trend> getTopTrends(){
       
       List<Trend>Trends=new ArrayList<Trend>();
       try
       {
            String query="select top 5 * from Trend order by TimesSearched desc ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            
            String searchedKeyword=null;
            int searchedCount;
  
            while(rs.next())
            {
                searchedKeyword=rs.getString(2);
                searchedCount=rs.getInt(3);

                Trend obj=new Trend(searchedKeyword,searchedCount);
                Trends.add(obj);   
            }
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return Trends;  
   }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    ///get ads for filters
    List<PropertyAd> getFilteredLoc1(){//this is returning the list of searched zameen.com published ads which include diff types
       
       List<PropertyAd>filteredAds=new ArrayList<PropertyAd>();
       try
       {
            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            int noOfFloors;//for plaza
            int noOfBedrooms;//for house
            int noOfWashrooms;//for house
            boolean furnished;//for house
            int floor;//for apartment
            String userName=null;
            
            String query="select * from [propdet] where [Address] Like '%wapda town%'";  //get searched the propertyAds
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(2);
                address=rs.getString(3);
                area=rs.getInt(4);
                type=rs.getString(5);
                price=rs.getString(6);
                kind=rs.getString(7);
                description=rs.getString(8);
                bookmark=rs.getInt(9);
                feature=rs.getBoolean(10);
                userName=rs.getString(11);
                
                PropertyAd obj=new PropertyAd(propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [Appdet] where [Address] Like '%wapda town%'";          //get the searched zameen.com appartments
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                furnished=rs.getBoolean(12);
                noOfBedrooms=rs.getInt(13);
                noOfWashrooms=rs.getInt(14);
                userName=rs.getString(15);
                
                PropertyAd obj= new Apartment(floor,noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [housedet] where [Address] Like '%wapda town%'";          //get the searched zameen.com houses
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                furnished=rs.getBoolean(11);
                noOfBedrooms=rs.getInt(12);
                noOfWashrooms=rs.getInt(13);
                userName=rs.getString(14);
                
                PropertyAd obj= new House(noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [plazadet] where [Address] Like '%wapda town%'";          //get the searched zameen.com plazas
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                noOfFloors=rs.getInt(11);
                userName=rs.getString(12);
                PropertyAd obj= new Plaza(noOfFloors,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return filteredAds;  
   }
    
    
    
        List<PropertyAd> getFilteredLoc2(){//this is returning the list of searched zameen.com published ads which include diff types
       
       List<PropertyAd>filteredAds=new ArrayList<PropertyAd>();
       try
       {
            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            int noOfFloors;//for plaza
            int noOfBedrooms;//for house
            int noOfWashrooms;//for house
            boolean furnished;//for house
            int floor;//for apartment
            String userName=null;
            
            String query="select * from [propdet] where [Address] Like '%garden town%'";  //get searched the propertyAds
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(2);
                address=rs.getString(3);
                area=rs.getInt(4);
                type=rs.getString(5);
                price=rs.getString(6);
                kind=rs.getString(7);
                description=rs.getString(8);
                bookmark=rs.getInt(9);
                feature=rs.getBoolean(10);
                userName=rs.getString(11);
                
                PropertyAd obj=new PropertyAd(propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [Appdet] where [Address] Like '%garden town%'";          //get the searched zameen.com appartments
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                furnished=rs.getBoolean(12);
                noOfBedrooms=rs.getInt(13);
                noOfWashrooms=rs.getInt(14);
                userName=rs.getString(15);
                
                PropertyAd obj= new Apartment(floor,noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [housedet] where [Address] Like '%garden town%'";          //get the searched zameen.com houses
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                furnished=rs.getBoolean(11);
                noOfBedrooms=rs.getInt(12);
                noOfWashrooms=rs.getInt(13);
                userName=rs.getString(14);
                
                PropertyAd obj= new House(noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [plazadet] where [Address] Like '%garden town%'";          //get the searched zameen.com plazas
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                noOfFloors=rs.getInt(11);
                userName=rs.getString(12);
                PropertyAd obj= new Plaza(noOfFloors,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return filteredAds;  
   }
        
        
    
    List<PropertyAd> getFilteredLoc3(){//this is returning the list of searched zameen.com published ads which include diff types
       
       List<PropertyAd>filteredAds=new ArrayList<PropertyAd>();
       try
       {
            int propertyNo;
            String address=null;
            int area;
            String type=null;
            String price=null;
            String kind=null;
            String description=null;
            int bookmark;
            boolean feature;
            int noOfFloors;//for plaza
            int noOfBedrooms;//for house
            int noOfWashrooms;//for house
            boolean furnished;//for house
            int floor;//for apartment
            String userName=null;
            
            String query="select * from [propdet] where [Address] Like '%bahria town%'";  //get searched the propertyAds
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet  rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(2);
                address=rs.getString(3);
                area=rs.getInt(4);
                type=rs.getString(5);
                price=rs.getString(6);
                kind=rs.getString(7);
                description=rs.getString(8);
                bookmark=rs.getInt(9);
                feature=rs.getBoolean(10);
                userName=rs.getString(11);
                
                PropertyAd obj=new PropertyAd(propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [Appdet] where [Address] Like '%bahria town%'";          //get the searched zameen.com appartments
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                floor=rs.getInt(11);
                furnished=rs.getBoolean(12);
                noOfBedrooms=rs.getInt(13);
                noOfWashrooms=rs.getInt(14);
                userName=rs.getString(15);
                
                PropertyAd obj= new Apartment(floor,noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [housedet] where [Address] Like '%bahria town%'";          //get the searched zameen.com houses
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                furnished=rs.getBoolean(11);
                noOfBedrooms=rs.getInt(12);
                noOfWashrooms=rs.getInt(13);
                userName=rs.getString(14);
                
                PropertyAd obj= new House(noOfBedrooms,noOfWashrooms,furnished,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
            query="select * from [plazadet] where [Address] Like '%bahria town%'";          //get the searched zameen.com plazas
            preparedStmt = con.prepareStatement(query);
            rs=preparedStmt.executeQuery();
            
            while(rs.next())
            {
                propertyNo=rs.getInt(9);
                address=rs.getString(2);
                area=rs.getInt(3);
                type=rs.getString(10);
                price=rs.getString(8);
                kind=rs.getString(7);
                description=rs.getString(5);
                bookmark=rs.getInt(4);
                feature=rs.getBoolean(6);
                noOfFloors=rs.getInt(11);
                userName=rs.getString(12);
                PropertyAd obj= new Plaza(noOfFloors,propertyNo,address,area,type,price,kind,description,bookmark,feature,userName);
                filteredAds.add(obj);  
            }
            
       }
       catch(Exception e)
        {
            System.out.println(e);
        }
       return filteredAds;  
   }
    //////////////////////////////////////////////////////////////////////////////
    //////create articles
    
   void insertArticle(String name,String mainBody,Date dateOfPublish,String userName)
   {
       java.util.Date temp = dateOfPublish;
       java.sql.Date publishDate = new java.sql.Date(temp.getTime());
        try
        {
            String query = "insert into [Article] values(?,?,?,?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, name);
             preparedStmt.setString(2, mainBody);
             preparedStmt.setDate(3, publishDate);
             preparedStmt.setString(4, userName);
             preparedStmt.setInt(5, 1);
             preparedStmt.executeUpdate();
         }
      
        catch(SQLException e)
        {
            System.out.println(e);
        }
   }
   /////////////////////////////////////////////////////////////////
   ///////Validate Payment
   int validatePayment(String cardNumber,String securityPin,String userName)
   {
        try
        {
            String query="select * from [Payment] where UserName = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            ResultSet  rs=preparedStmt.executeQuery();

            String cardNum=null;
            String securityNum=null;
            
            //java.util.Date temp = dateOfPublish;
            //java.sql.Date publishDate = new java.sql.Date(temp.getTime());
            while(rs.next())
            {
                cardNum=rs.getString(2);
                securityNum=rs.getString(3);
                
                if(cardNum.equals(cardNumber) && securityPin.equals(securityNum) )
                {
                    System.out.println("The Payment is verified");
                    return 1;
                }
                
            }
           
               
            System.out.println("The Payment details mismatch");
            return 0;
        
            
        }
        catch(Exception e)
        {
            System.out.println("The Payment details doesnot exist");
            return 0;
        }
   }
   //////////////////////////////////////////////////////////////////////////////////////
   
   //////////////Update PropertyAd
   void FeatureAD(int propNum , String address)
   {
        try
        {
            String query = "update [PropertyAd] set  Feature = 1 where PropertyNumber=? and Address=?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setInt(1, propNum);
             preparedStmt.setString(2,address);
             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
   
   ////////////////////////////////////////////////////////////////////////
   /////add bookmark
   void insertBookmarks(int PropertyId,String userName)
   {
        try
        {
            String query = "insert into Bookmarks values(?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setInt(1,PropertyId);
             preparedStmt.setString(2, userName);
             preparedStmt.executeUpdate();
         }
        catch(SQLException e)
        {
            System.out.println(e);
        }
   }
   
   ////////////////////////////////////////////////////////////////////////////
   int getPropertyId(int propertyNumber, String address)
   {
       int id=0;
        try
            {

                 String query="select * from [propdet] where PropertyNumber=? and Address=? ";          //get the user propertyAds
                 PreparedStatement preparedStmt = con.prepareStatement(query);
                 preparedStmt.setInt(1, propertyNumber);
                 preparedStmt.setString(2, address);
                 ResultSet  rs=preparedStmt.executeQuery();

                 while(rs.next())
                 {
                     id=rs.getInt(1);  
                 }

                 query="select * from [Appdet] where PropertyNumber=? and Address=? ";          //get the user appartments
                 preparedStmt = con.prepareStatement(query);
                 preparedStmt.setInt(1, propertyNumber);
                 preparedStmt.setString(2, address);
                 rs=preparedStmt.executeQuery();

                 while(rs.next())
                 {
                     id=rs.getInt(1);  
                 }

                 query="select * from [housedet] where PropertyNumber=? and Address=? ";          //get the user houses
                 preparedStmt = con.prepareStatement(query);
                 preparedStmt.setInt(1, propertyNumber);
                 preparedStmt.setString(2, address);
                 rs=preparedStmt.executeQuery();

                 while(rs.next())
                 {
                     id=rs.getInt(1);  
                 }

                 query="select * from [plazadet] where PropertyNumber=? and Address=? ";          //get the user plazas
                 preparedStmt = con.prepareStatement(query);
                 preparedStmt.setInt(1, propertyNumber);
                 preparedStmt.setString(2, address);
                 rs=preparedStmt.executeQuery();

                 while(rs.next())
                 {
                     id=rs.getInt(1);  
                 }
            }
            catch(Exception e)
             {
                 System.out.println(e);
             }
            return id;  
    }
   
   
   
   
   
   /////////////////////////////////////////////////////////////////////////////////////////
   /////////////////////////kunwars code///////////////////////////////////////////////////
   void insertProperty(Integer PropertyNumber, String Address, Integer Area, String Type, String Price, String Kind, String Description, Integer Bookmarks, Boolean Feature,String user)
   {
        try
        {
            String query = "insert into PropertyAd values(?,?,?,?,?,?,?,?,?,? )";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setInt(1, PropertyNumber);
             preparedStmt.setString(2,Address);
             preparedStmt.setInt(3,Area);
             preparedStmt.setString(4,Type);
             preparedStmt.setString(5,Price);
             preparedStmt.setString(6,Kind);
             preparedStmt.setString(7,Description);
             preparedStmt.setInt(8,Bookmarks);
             preparedStmt.setBoolean(9,Feature);
             preparedStmt.setString(10,user);
             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }

   int getlastestProp()
   {
       int s=0;
       try
        {
             ResultSet rs=stmt.executeQuery("select max(Id) from PropertyAd");
             while(rs.next())
             {
                 
                s=rs.getInt(1);
             }
             
             
             
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
       return s;
   }
 
   
   void insertHouse(Integer NoOfBedrooms, Integer NoOfWashrooms, Boolean Furnished, Integer PropertyAdId)
   {
        try
        {
            String query = "insert into House values(?,?,?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setInt(1, NoOfBedrooms);
             preparedStmt.setInt(2,NoOfWashrooms);
             preparedStmt.setBoolean(3,Furnished);
             preparedStmt.setInt(4,PropertyAdId);
             
             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
      
    int getLatestHouse()
    {
        int s=0;
       try
        {
             ResultSet rs=stmt.executeQuery("select max(Id) from House");
             while(rs.next())
             {
                 
                s=rs.getInt(1);
             }
             
             
             
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
       return s;
    }
   
    
    void insertApp(Integer Floor, Integer HouseId)
   {
        try
        {
            String query = "insert into Apartment values(?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setInt(1, Floor);
             preparedStmt.setInt(2, HouseId);

             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
    
   void insertPlaza(Integer NoOfFloors, Integer PropertyAdId)
   {
        try
        {
            String query = "insert into Plaza values(?,?)";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setInt(1, NoOfFloors);
             preparedStmt.setInt(2, PropertyAdId);

             preparedStmt.executeUpdate();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
   } 
   
   void editPrice(Integer PropertyNumber,String Address,String Price)
   {
        try
        {
            String query = "update PropertyAd set Price = ? where PropertyNumber=? and Address=?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, Price);
             preparedStmt.setInt(2, PropertyNumber);
             preparedStmt.setString(3, Address);

             preparedStmt.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
   
   void editDesc(Integer PropertyNumber,String Address,String Description)
   {
        try
        {
            String query = "update PropertyAd set Description = ? where PropertyNumber=? and Address=?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             preparedStmt.setString(1, Description);
             preparedStmt.setInt(2, PropertyNumber);
             preparedStmt.setString(3, Address);

             preparedStmt.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
   void deleteProp(Integer PropertyNumber,String Address)
   {
        try
        {
            String query = "delete from PropertyAd where PropertyNumber=? and Address=?";
             PreparedStatement preparedStmt = con.prepareStatement(query);
             
             preparedStmt.setInt(1, PropertyNumber);
             preparedStmt.setString(2, Address);

             preparedStmt.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
   
}
