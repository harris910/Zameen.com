/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class RealEstate extends User {
    String NameOfRealEstate;
    String NIC;
    

    public RealEstate(String Name, String Email, String PhoneNumber, String Password, String UserName,String _NameOfRealEstate, String nic) {
        super(Name, Email, PhoneNumber, Password, UserName);
        NameOfRealEstate=_NameOfRealEstate;
        NIC=nic;
    }
    
    void Print(){
        
        System.out.println("\t\t\tReal Estate Name : " + NameOfRealEstate );
        System.out.println("\t\t\tNIC : " + NIC );
        super.Print();
    }
}
