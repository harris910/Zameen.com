
import java.util.List;
import java.util.Date;
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
public class Blog {

    String Name;
    String Description;
    List<Article> ArticlesOnBlog;
    
    dbConnectivity db = dbConnectivity.getInstance();
    
    public Blog(String Name, String Description) {
        this.Name = Name;
        this.Description = Description;
        ArticlesOnBlog=db.getArticles();
    }
    
    void Print(){
        System.out.println("\t\t\tTitle: "+ Name);
        System.out.println("\t\t\tDescription: " + Description);
        
        for(Article a:ArticlesOnBlog)
            a.Print(); 
    }
    
    void editBlog(){
        Scanner cin = new Scanner(System.in);
        System.out.println("\t\t\tenter Article name ");
        String nameOfArticle=cin.nextLine();
        System.out.println("\t\t\tenter new main body");
        String mainBody= cin.nextLine();
        
        Zameen z=Zameen.getInstance();
        dbConnectivity db=dbConnectivity.getInstance();
        
        Article a=new Article(nameOfArticle,mainBody,new Date(),z.CurrentUser.UserName);        
        ArticlesOnBlog.add(a);
        z.CurrentUser.UserPublishedArticles.add(a);
        db.insertArticle(nameOfArticle, mainBody, new Date(), z.CurrentUser.UserName);
    }
}
