/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*; 
/**
 *
 * @author user
 */
public class Payment {

    String CardNumber;
    String SecurityPin;
    Date ExpiryDate;
    String UserName;
    
    public Payment(String CardNumber, String SecurityPin, Date ExpiryDate) {
        this.CardNumber = CardNumber;
        this.SecurityPin = SecurityPin;
        this.ExpiryDate = ExpiryDate;
        this.UserName=UserName;
    }
    
    void print()
    {
       System.out.println("\t\t\tcardnumber : "+CardNumber);
       
       System.out.println("\t\t\tSecurity pin : "+SecurityPin);
       
       System.out.println("\t\t\texpiry date : "+ExpiryDate);
        
    }
}
