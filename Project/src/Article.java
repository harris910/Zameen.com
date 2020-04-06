
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Article {
    String Name;
    String MainBody;
    Date DateOfPublish;
    String UserName;
    
    public Article(String Name, String MainBody, Date DateOfPublish,String UserName) {
        this.Name = Name;
        this.MainBody = MainBody;
        this.DateOfPublish = DateOfPublish;
        this.UserName=UserName;
    }
    
    void Print()
    {
        System.out.println("\t\t\tArticle Name: "+ Name);
        System.out.println("\t\t\tMain Body: " + MainBody);
        System.out.println("\t\t\tDate: " + DateOfPublish);
        System.out.println("\t\t\tAuthor: " + UserName);
        System.out.println("\n\n");
        
    }
    
}
