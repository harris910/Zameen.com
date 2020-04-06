
import java.util.ArrayList;
import java.util.Collections;
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
public class Zameen {
    dbConnectivity db = dbConnectivity.getInstance();
    
    List<User> ZameenUsers;
    List<PropertyAd> ZameenPublishedAds;
    List<Trend> ZameenSearchTrends;
    Blog ZameenBlog;
    User CurrentUser;
    
    private static Zameen zameen=new Zameen();
    
    private Zameen(){
        ZameenUsers=db.getUser();
        ZameenPublishedAds=db.getPublishedAds();
        ZameenSearchTrends=db.getTrends();
        ZameenBlog=new Blog("ZameenBlog","Keeps you updated about the Property Market");
        User CurrentUser=new User();
    }
    
    public static Zameen getInstance() 
    { 
        return zameen; 
    }
    
    void Register(int type)
    {
        Scanner input=new Scanner(System.in);
        String Name;
        String Email;
        String PhoneNumber;
        String Password;
        String UserName;
        
        System.out.println("Enter your name: ");
        Name=input.nextLine();
        System.out.println("Enter your Email: ");
        Email=input.nextLine();
        System.out.println("Enter your phoneno: ");        
        PhoneNumber=input.nextLine();
        System.out.println("Enter your password: ");        
        Password=input.nextLine();
        System.out.println("Enter your Username: ");        
        UserName=input.nextLine();
        
        for(User u:ZameenUsers)
        {
            if(u.UserName.equals(UserName))
            {
                System.out.println("This UserName is already taken. Please select another UserName");
                Register(type);
            }
                
        }
        
        System.out.println(UserName + " have been registered successfully");
        db.insertUser(Name, Email, PhoneNumber, Password, UserName);
        
        
        
        if(type==2)
        {
            String NameOfRealEstate;
            String NIC;
            System.out.println("Enter your Real State Name: ");        
            NameOfRealEstate=input.nextLine();
            
            System.out.println("Enter your : ");        
            NIC=input.nextLine();
            
            db.insertAgent(UserName, NameOfRealEstate, NIC);
            CurrentUser=new RealEstate(Name, Email, PhoneNumber, Password, UserName,NameOfRealEstate, NIC);
            
        }
        
        else
        {
            CurrentUser=new User(Name, Email, PhoneNumber, Password, UserName);
        }
        
        System.out.println(UserName + " have been logged in Successfully.");
            //User obj
            
            CurrentUser.UserPayments=db.getPayment(UserName);
            CurrentUser.UserLocations=db.getLocation(UserName);
            CurrentUser.UserPublishedAds=db.getPublishedAds(UserName);
            CurrentUser.UserPublishedArticles=db.getArticles(UserName);
            CurrentUser.UserBookmarkAds=db.getBookmarkAds(UserName);
        
    }
    

    
    void Login()
    {
        Scanner input=new Scanner(System.in);
        
        System.out.print("Enter your username: ");
        String UserName=input.nextLine();//"kunwar12";
        System.out.println("Enter your Password: ");
        String Password=input.nextLine();//"ahmed234";
        
        int temp=ValidateUser(UserName, Password);
        
        if(temp==1){    
            System.out.println(UserName + " have been logged in Successfully.");
            //User obj
            CurrentUser=db.getUser(UserName, Password);
            CurrentUser.UserPayments=db.getPayment(UserName);
            CurrentUser.UserLocations=db.getLocation(UserName);
            CurrentUser.UserPublishedAds=db.getPublishedAds(UserName);
            CurrentUser.UserPublishedArticles=db.getArticles(UserName);
            CurrentUser.UserBookmarkAds=db.getBookmarkAds(UserName);
        }
        else if(temp==0)
        {
            System.out.println("The user password doesnot match the UserName");
            zameen.Login();
        }
    }
    
    int ValidateUser(String UserName,String Password){
        return db.validateUser(UserName, Password);
    }
    
    void DisplayHomepage()
    {
        CurrentUser.Print();
    }
    
    void DisplayAds()
    {
        List<PropertyAd> tempfeatured=new ArrayList<PropertyAd>();
        List<PropertyAd> tempNonfeatured=new ArrayList<PropertyAd>();
        for(PropertyAd p:ZameenPublishedAds)
        {
            if(p.getFeature()==true)
                tempfeatured.add(p);
            else
                tempNonfeatured.add(p);
        }
        ZameenPublishedAds=tempfeatured;
        for(PropertyAd p:tempNonfeatured)
        {
            ZameenPublishedAds.add(p);
        }
        
        int i=0;
        for(PropertyAd p:ZameenPublishedAds)
        {
            p.count=i++;
            p.Print();
        }
    }
    
    void ViewBlog(){
        ZameenBlog.Print();
    }
    
    void SearchAndDisplay(String SearchedKeyword)
    {
        //String SearchedKeyword="Garden Town";
        boolean flag=false;
        
        for(Trend t:ZameenSearchTrends)
        {
            if(t.getSearchedKeyword().equalsIgnoreCase(SearchedKeyword))
            {
                t.TimesSearched=t.getTimesSearched()+1;
                db.updateTrend(t.TimesSearched, SearchedKeyword); 
                flag=true;
            }
            
        }
        if(flag==false){
            
            Trend newTrend=new Trend(SearchedKeyword.toLowerCase(),1);
            ZameenSearchTrends.add(newTrend);
            db.insertTrend(SearchedKeyword.toLowerCase(), 1);
        }
        
        List<PropertyAd> searchedAds=new ArrayList<PropertyAd>();
        searchedAds=db.getSearchedAds(SearchedKeyword);
        
        List<PropertyAd> tempfeatured=new ArrayList<PropertyAd>();
        List<PropertyAd> tempNonfeatured=new ArrayList<PropertyAd>();
        for(PropertyAd p:searchedAds)
        {
            if(p.getFeature()==true)
                tempfeatured.add(p);
            else
                tempNonfeatured.add(p);
        }
        searchedAds=tempfeatured;
        for(PropertyAd p:tempNonfeatured)
        {
            searchedAds.add(p);
        }
        
        int i=0;
        for(PropertyAd p:searchedAds)
        {   
            p.count=i++;
            p.Print();
        }
    }
    
    void DisplayTrends(){
        List<Trend> TopRated=new ArrayList<Trend>();
        TopRated=db.getTopTrends();
        System.out.println("The trending areas are: ");
        for(Trend t:TopRated)
            t.Print();
    }
    
    void ApplyFilters(int num)
    {
        //int num=1;
        List<PropertyAd> filteredAds=new ArrayList<PropertyAd>();
        List<PropertyAd> tempfeatured=new ArrayList<PropertyAd>();
        List<PropertyAd> tempNonfeatured=new ArrayList<PropertyAd>();
        
        if(num==1)
            filteredAds=db.getFilteredLoc1();
        else if(num==2)
            filteredAds=db.getFilteredLoc2();
        else if(num==3)
            filteredAds=db.getFilteredLoc3();
        
        for(PropertyAd p:filteredAds)
        {
            if(p.getFeature()==true)
                tempfeatured.add(p);
            else
                tempNonfeatured.add(p);
        }
        filteredAds=tempfeatured;
        for(PropertyAd p:tempNonfeatured)
        {
            filteredAds.add(p);
        }
        
        int i=0;
        for(PropertyAd pa:filteredAds)
        {    
            pa.count=i++;
            pa.Print();
        }
    }
    
    void ViewContactDetails(int num)
    {
        //int num=0;
        int n=0;
        //zameen.DisplayAds();
        
        PropertyAd temp=new PropertyAd();
        for(PropertyAd pa:ZameenPublishedAds)
        {
            if(n==num)
            {
                temp=pa;
            }
            n=n+1;
        }
        
        for(User u:ZameenUsers)
        {
            
            if(u.UserName.equals(temp.UserName))
                u.ViewContactDetails();
        }
          
    }
    void ShareProperty(){
        System.out.println("Press 1: To Share Property on Facebook");
        System.out.println("Press 2: To Share Property on Whatsapp");
        Scanner cin = new Scanner(System.in);
        int a = cin.nextInt();
        if(a == 1){
            System.out.println("Property Shared on Facebook");
        }
        else if(a == 2){
            System.out.println("Property Shared on WhatsApp");
        }
        
    }
    //////////////////////////////////////////////////////////////////////////////
    /////kunwars code
    
        void createAd()
    {
        Scanner input=new Scanner(System.in);
        System.out.println("1) Plot\n 2) House\n 3) Appartment\n 4) Plaza");
        int choice=input.nextInt();
        PropertyAd obj=CurrentUser.addProperty(choice);
        if(ZameenPublishedAds!=null)
        ZameenPublishedAds.add(obj);
        
        else
        {
           ZameenPublishedAds=new ArrayList<PropertyAd>(); 
           ZameenPublishedAds.add(obj);
           
        }
        obj.propertyup();
        
    }
    
    void EditProperty()
    {
        System.out.println("Edit Property");
        System.out.print("\n");
        CurrentUser.printproperty();
        Scanner input=new Scanner(System.in);
        System.out.println("Enter index of Property You want to Edit");
        int choice=input.nextInt();
        CurrentUser.editproperty(choice);
    }
    
    void deleteProp()
    {
        CurrentUser.printproperty();
        Scanner input=new Scanner(System.in);
        System.out.println("\t\t\tEnter index of Property You want to delete");
        int choice=input.nextInt();
        CurrentUser.deleteprop(choice);
    }
    
    
    int menulogin(int nn){
         Scanner cin =new Scanner(System.in);
         int n=-1;          
            while( n != 17){
                System.out.println("\tPress 1: view all property.");
                System.out.println("\tPress 2: add filter");
                System.out.println("\tPress 3: Search property");
                System.out.println("\tPress 4: view Blog.");
                System.out.println("\tPress 5: view Trends.");
                System.out.println("\tPress 6: Add Property.");
                System.out.println("\tPress 7: Feature the property");
                System.out.println("\tPress 8: Add Bookmarks");
                System.out.println("\tPress 9: View Bookmarks");
                System.out.println("\tPress 10: Edit Property");
                System.out.println("\tPress 11: Remove Property");
                System.out.println("\tPress 12: Edit Blog");
                System.out.println("\tPress 13: Share Property");
                System.out.println("\tPress 14: view user property.");
                System.out.println("\tPress 15: view contact details.");
                System.out.println("\tPress 16: Logout");
                System.out.println("\tPress 17: To Exit menu");
               
                n = cin.nextInt();
                nn=n;
                if(n == 1){
                 //view property
                    this.DisplayAds();
                }
                else if(n == 2){
                     System.out.println("\t\tPress 1: filter for wapdatown.");
                     System.out.println("\t\tPress 2: filter for garden town.");                                          
                     System.out.println("\t\tPress 3: filter for Bahria town.");
                     int q=cin.nextInt();
                     if(q==1){
                         ApplyFilters(1);
                     }else if(q==2){
                         ApplyFilters(2);
                     }else if(q==3){
                         ApplyFilters(3);
                     }else{
                         System.out.println("\twrong input");
                     }
                   
                }
                else if(n==3){
                    System.out.println("\t\tEnter the location to search");
                   
                    String ptr = cin.nextLine();
                     ptr = cin.nextLine();
                    this.SearchAndDisplay(ptr);
               
                }
                else if(n == 4){
                    this.ViewBlog();
                }
                else if(n == 5){
                    this.DisplayTrends();
                }
                else if(n == 6){
                    this.createAd();
                }
                else if(n == 7){
                    //this.DisplayAds();
                    //this.CurrentUser.printproperty();
                    
                    this.CurrentUser.featureAds();

                }
                else if(n == 8){
                   this.DisplayAds();
                    System.out.println("\t\tSelect a specific number to add Bookmark");
                    int k=cin.nextInt();
                    this.CurrentUser.addBookmark(k); 
                }
                else if(n == 9){
                       System.out.println("\t\tBookmarks are: ");
                        this.CurrentUser.viewBookmark();
                }
                else if(n == 10){
                   this.EditProperty();
                }
                else if(n == 11){
                    this.deleteProp();
                }
                else if(n == 12){
                        this.ZameenBlog.editBlog();
                }
                else if(n == 13){
                    this.ShareProperty();
                }
                else if(n == 14){
                     this.CurrentUser.Print();
                }
                else if(n == 15){
                    this.DisplayAds();
                    System.out.println("Select a specific number to get info of that property");
                    int k=cin.nextInt();
                    this.ViewContactDetails(k);

                }
                else if(n == 16){
                    this.CurrentUser = null;
                    System.out.println("logout successfully");
                    return nn;
                   

                }
            }
        
        return nn;
    }
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    
void menue(){
        Scanner cin =new Scanner(System.in);
int nn=0;
int m=0;
boolean check=true;

while(check ==true){
        System.out.println("Press 1: Use as guest.");
        System.out.println("Press 2: login");
        System.out.println("Press 3: Register");

     
        int choice=0;
        
        choice = cin.nextInt();
        if(choice == 1){
            int n=-1;          
            while( n != 8){
                System.out.println("\tPress 1: view property.");
                System.out.println("\tPress 2: add filter");
                System.out.println("\tPress 3: Search property");
                System.out.println("\tPress 4: view Blog.");
                System.out.println("\tPress 5: view Trends.");
                System.out.println("\tPress 6: Share property.");
                System.out.println("\tPress 7: view Contact Details.");
                System.out.println("\tPress 8: To Exit menu.");
                n = cin.nextInt();
                if(n == 1){
                 //view property
                    this.DisplayAds();
                }
                else if(n == 2){
                     System.out.println("\t\tPress 1: filter for wapdatown.");
                     System.out.println("\t\tPress 2: filter for garden town.");                                          
                     System.out.println("\t\tPress 3: filter for Bahria town.");
                     int q=cin.nextInt();
                     if(q==1){
                         ApplyFilters(1);
                     }else if(q==2){
                         ApplyFilters(2);
                     }else if(q==3){
                         ApplyFilters(3);
                     }else{
                         System.out.println("\twrong input");
                     }
                   
                }
                else if(n==3){
                    System.out.println("\t\tEnter the location to search");
                   
                    String ptr = cin.nextLine();
                     ptr = cin.nextLine();
                    this.SearchAndDisplay(ptr);
               
                }
                else if(n == 4){
                    this.ViewBlog();
                }
                else if(n == 5){
                    this.DisplayTrends();
                }
                else if(n == 6){
                    this.ShareProperty();
                }
                else if(n == 7){
                    //this.DisplayAds();
                    System.out.println("Select a specific number to get info of that property");
                    int k=cin.nextInt();
                    this.ViewContactDetails(k);

                }
            }
           
        }
        else if(choice == 2){
            this.Login();
             m=menulogin(nn);
            
        }else if(choice == 3){
            
            System.out.println("\t\tEither you want to be a Simple user or Real Estate Agent ");
            System.out.println("\t\tpress 1: Simple User");
            System.out.println("\t\tpress 2: Estate Agent");
            
            int p = cin.nextInt();
            this.Register(p);
            m= menulogin(nn);
            
        }

    if(m ==17){
    break;
    }

}
    
    
    

}
}