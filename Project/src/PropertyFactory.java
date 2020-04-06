/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class PropertyFactory {
    
    PropertyAd getProperty(String type)
    {
        if(type.equalsIgnoreCase("PLOT"))
        {
            return new PropertyAd();
        }
        else if(type.equalsIgnoreCase("HOUSE"))
        {
            return new House();
        }
        else if(type.equalsIgnoreCase("APARTMENT"))
        {
            return new Apartment();
        }
        else if(type.equalsIgnoreCase("PLAZA"))
        {
            return new Plaza();
        }
        return null;
    }
    
}
