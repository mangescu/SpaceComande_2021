
package GameSaveLoad;

import Sun.Sun;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import spacecomander.Quad;
import spacecomander.QuadData;

public class Save {
    
    QuadData qdata;
    
    FileWriter writer  = null;
    BufferedWriter out = null;
    
    public Save(QuadData qdata){
      this.qdata = qdata;
    }
            
    public void saveGame() throws IOException {

          FileUtils.deleteQuietly(new File("save.txt"));
        
           writer = new FileWriter("save.txt", true);            
           out = new BufferedWriter(writer); 
           
          for(Sun sun:qdata.sunList){
            write_Line("sun ");
            write_Line(String.valueOf(sun.position.x)+" ");
            write_Line(String.valueOf(sun.position.y)+" ");
            write_Line(String.valueOf(sun.position.z)+" ");
            write_Line(String.valueOf(sun.model.scale.x)+" ");
            
            out.newLine();
           }
           
               
              
                
        out.close();    
    }
    
    public void write_Line(String appendToLine) throws IOException{
      out.write(" "+appendToLine);
    }
    
}
