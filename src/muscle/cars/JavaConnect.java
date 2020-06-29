/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muscle.cars;

/**
 *
 * @author Adnan Alam
 */

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class JavaConnect 
{
    
    public static Connection connectDb()
    {
        
        try
        {
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/muscle_cars", "root", "");
            return conn;
            
        } // end try
        catch(Exception exception)
        {
            
            JOptionPane.showMessageDialog(null, exception);
            return null;
            
        } // end catch Exception
        
    } // end method connectDb
    
} // end class JavaConnect
