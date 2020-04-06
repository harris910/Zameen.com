
import java.util.Date;
import java.util.List;
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
public class User {
    String Name;
    String Email;
    String PhoneNumber;
    String Password;
    String UserName;
    List<Payment> UserPayments=null;
    List<Location> UserLocations=null;
    List<PropertyAd> UserPublishedAds=null;
    List<PropertyAd> UserBookmarkAds=null;
    List<Article> UserPublishedArticles=null;
    
    public User()
    {
        
    }
    
    public User(String Name, String Email, String PhoneNumber, String Password, String UserName) {
        this.Name = Name;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.Password = Password;
        this.UserName = UserName;
    }

    
    void Print()
    {
        int i=1;
        
        System.out.println("\t\t\t Personal information");
        System.out.println("");
        
        System.out.println("\t\t\tpersonal name : "+ Name);
        System.out.println("\t\t\temail : "+ Email);
        System.out.println("\t\t\tphone number : "+ PhoneNumber);
        System.out.println("\t\t\tUSerName : "+ UserName);
        
       
        System.out.println("\t\t\t-------------------");
        System.out.println("\t\t\tPayments are: ");
       
        if(UserPayments!=null)
            for(Payment p:UserPayments)
                p.print();
        System.out.println("\t\t\t-------------------"); 
        System.out.println("\t\t\tLocations are: ");
        if(UserLocations!=null)
            for(Location l:UserLocations)
                l.Print();
        System.out.println("\t\t\t-------------------"); 
        System.out.println("\t\t\tProperties are: ");
        if(UserPublishedAds!=null)
            for(PropertyAd pa:UserPublishedAds){
                pa.count=i++;
                pa.Print();
            }
        i=1;
        System.out.println("\t\t\t-------------------"); 
        System.out.println("\t\t\tPublished Articles are: ");
        if(UserPublishedArticles!=null)
            for(Article a:UserPublishedArticles)
                a.Print();
        System.out.println("\t\t\t-------------------"); 
        System.out.println("\t\t\tBookmarks are: ");
        if(UserBookmarkAds!=null)
            for(PropertyAd ba:UserBookmarkAds){
                ba.count=i++;
                ba.Print();
            }
    }
    
     void ViewContactDetails()
    {
        System.out.println("\t\t\tName : " + Name);
        System.out.println("\t\t\tEmail : " + Email);
        System.out.println("\t\t\tPhone Number : " +PhoneNumber);
        System.out.println("\t\t\tUserName : " + UserName);


        //System.out.println(Name+" "+Email+" "+PhoneNumber+" "+UserName);
    }

    
    void featureAds()
    {
                    
        
        int i = 0;
         if(UserPublishedAds!=null)
            for(PropertyAd pa:UserPublishedAds){
                pa.count = i++;
                pa.Print();
            }
         
         //int num=0;
         Scanner cin = new Scanner(System.in);
        System.out.println("\t\tEnter property number to feature Ad");
        int k = cin.nextInt();
        
         
         int n=0;
         
        PropertyAd temp=new PropertyAd();
        for(PropertyAd pa:UserPublishedAds)
        {
            if(n==k)
            {
                temp=pa;
            }
            n=n+1;
        }
        
        dbConnectivity db=dbConnectivity.getInstance();
        System.out.println("\t\t\tEnter your credit card number: ");
        //Scanner cin = new Scanner(System.in);
        String CardNum = cin.nextLine();
        CardNum = cin.nextLine();
        System.out.println("\t\t\tEnter your credit card number pin: ");
        
        String SecurityPin = cin.nextLine();
        

        
        if(db.validatePayment(CardNum, SecurityPin, UserName)==1)
        {
            if(temp!=null)
            {
                db.FeatureAD(temp.PropertyNumber, temp.Address);
                temp.Feature=true;
                System.out.println("The Ad is featured");
            }
        }
        
        
        
        
        Zameen z=Zameen.getInstance();
        
        for(PropertyAd pa:z.ZameenPublishedAds)
        {
            if(temp.equals(pa))
            {
                pa.Feature=true;
            }
        }
        
        for(PropertyAd pa:UserBookmarkAds)
        {
            if(temp.equals(pa))
            {
                pa.Feature=true;
            }
        }
        
        
        
        

    }
    
    void viewBookmark()
    {
         if(UserBookmarkAds!=null)
            for(PropertyAd ba:UserBookmarkAds)
                ba.Print();
    }
     
    void addBookmark(int num)
    {
        Zameen z=Zameen.getInstance();
        
        //int num=1;
        int n=0;
        PropertyAd temp=new PropertyAd();
        for(PropertyAd pa:z.ZameenPublishedAds)
        {
            if(n==num)
            {
                temp=pa;
            }
            n=n+1;
        }
        z.CurrentUser.UserBookmarkAds.add(temp);
        dbConnectivity db=dbConnectivity.getInstance();
        
        db.insertBookmarks(db.getPropertyId(temp.PropertyNumber, temp.Address),z.CurrentUser.UserName);
        
        
    }
    
  ///////////////////////////////////////////////////////////////////////////
    ///////////////////////kunwars code
    
    void printproperty()
    {
        int n=0;
        for(PropertyAd pa:UserPublishedAds)
        {
            
            System.out.println("Property Index: "+n);
            pa.count = n++;
            pa.Print();
        }
            
    }
    
    void editproperty(int c)
    {
        Scanner input=new Scanner(System.in);
        System.out.println("1) Edit Description\n 2) Edit Price\n Enter Choice ");
        int choice=input.nextInt();
        String data;
        
        if(choice==2)
        {
            System.out.println("Enter New Price: ");
            data=input.nextLine();
            data=input.nextLine();
            PropertyAd obj=UserPublishedAds.get(c);
            obj.editPrice(data);
            Zameen z=Zameen.getInstance();
            for(PropertyAd pa: z.ZameenPublishedAds)
            {
                if(pa.Address.equals(obj.Address) && pa.PropertyNumber==obj.PropertyNumber)
                {
                    pa.Price=data;
                }
            }
            
            
           
            for(PropertyAd pa: UserBookmarkAds)
            {
                if(pa.Address.equals(obj.Address) && pa.PropertyNumber==obj.PropertyNumber)
                {
                    pa.Price=data;
                }
            }
            
            
        }
        else
        {
            System.out.println("Enter New Description: ");
            data=input.nextLine();
            data=input.nextLine();
            PropertyAd obj=UserPublishedAds.get(c);
            obj.editDesc(data);
            Zameen z=Zameen.getInstance();
            for(PropertyAd pa: z.ZameenPublishedAds)
            {
                if(pa.Address.equals(obj.Address) && pa.PropertyNumber==obj.PropertyNumber)
                {
                    pa.Description=data;
                }
            }
            
            for(PropertyAd pa: UserBookmarkAds)
            {
                if(pa.Address.equals(obj.Address) && pa.PropertyNumber==obj.PropertyNumber)
                {
                    pa.Description=data;
                }
            }

        }
        
    }
    
    void deleteprop(int c)
    {
        PropertyAd obj=UserPublishedAds.get(c);
        
        Zameen z=Zameen.getInstance();
        int n=0;
            for(PropertyAd pa: z.ZameenPublishedAds)
            {
                if(pa.Address.equals(obj.Address) && pa.PropertyNumber==obj.PropertyNumber)
                {
                    z.ZameenPublishedAds.remove(n);
                    break;
                }
                n++;
            }
        n=0;
            for(PropertyAd pa: UserPublishedAds)
            {
                if(pa.Address.equals(obj.Address) && pa.PropertyNumber==obj.PropertyNumber)
                {
                    UserPublishedAds.remove(n);
                    break;
                }
                n++;
            }
        n=0;   
            for(PropertyAd pa: UserBookmarkAds)
            {
                if(pa.Address.equals(obj.Address) && pa.PropertyNumber==obj.PropertyNumber)
                {
                    UserBookmarkAds.remove(n);
                    break;
                }
                n++;
            }
            
        obj.delete(); 
        
    }
    
    PropertyAd addProperty(int c)
    {
        Scanner input=new Scanner(System.in);
        PropertyFactory pf=new PropertyFactory();
        if(c==1)
        {
            PropertyAd obj=pf.getProperty("Plot");
            UserPublishedAds.add(obj);
            obj.setter();
            obj.UserName=this.UserName;
            
            return obj;
            
        }
        else if(c==2)
        {
            PropertyAd obj=pf.getProperty("House");
            UserPublishedAds.add(obj);
            obj.setter();
            obj.UserName=this.UserName;
            return obj;
        }
        else if(c==3)
        {
            PropertyAd obj=pf.getProperty("Apartment");
            UserPublishedAds.add(obj);
            obj.setter();
            obj.UserName=this.UserName;
            return obj;   
        }
        else if(c==4)
        {
            PropertyAd obj=pf.getProperty("Plaza");
            UserPublishedAds.add(obj);
            obj.setter();
            obj.UserName=this.UserName;
            return obj;
        }
        return null;
    }
    
}
