
package TextUtil;

import Display.SetupDisplay_LWJGL_3;
import IBO.IBO;
import QuadModel.Model;
import Texture.Textura;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.joml.Vector3f;
import spacecomander.Quad;

public class Characters {  

    IBO ibo;
    Textura tex;
    Model model; 
    Quad character;
    float ch_size;
    Map<Character, Integer>  charMap ; 
    public float spacer = 0.0f; 
    int index = 0;
    SetupDisplay_LWJGL_3   display;
    
   public Characters(IBO ibo , Textura textChart,SetupDisplay_LWJGL_3   display){
       this.ibo = ibo;
       this.display = display;
       this.tex = textChart;
       charMap = new HashMap<Character, Integer>(); 
       generateMap();
       if(display.getWIN_WIDTH() >=600 && display.getWIN_WIDTH() < 650) {
           ch_size = 0.0012f;
       }
       if(display.getWIN_WIDTH() >=650 && display.getWIN_WIDTH() < 670) {
           ch_size = 0.00125f;
       }
       if(display.getWIN_WIDTH() >=670 && display.getWIN_WIDTH() < 800) {
          ch_size = 0.0011f;
       }   
       if(display.getWIN_WIDTH() >=800 && display.getWIN_WIDTH() < 900) {
         ch_size = 0.00085f;
       } 
       if(display.getWIN_WIDTH() >=900 && display.getWIN_WIDTH() <= 1000) {
        ch_size = 0.00085f;
       }        
   }
   public void generateCharacter(float x , float y , float z , char ch ,ArrayList<Quad>    textChars)  {
       character = new Quad();
       character.curentPosition.x = x+spacer;
       character.curentPosition.y = y;
       character.curentPosition.z = z;
       model = new Model(ibo, tex, 0, character.curentPosition, new Vector3f(0,0,0) , new Vector3f(ch_size,ch_size+0.0001f,ch_size));
       int index =  charMap.get(ch);
       if(index == 69) {
          spacer +=(ch_size);
       }
       model.INDEX_T  = index;      
       character.model = model;
       spacer +=(2*ch_size);
       index++; 
       textChars.add(character);
   }    
   public void generateMap(){
    charMap.put("a".charAt(0), 0);
    charMap.put("b".charAt(0), 1);   
    charMap.put("c".charAt(0), 2); 
    charMap.put("d".charAt(0), 3);   
    charMap.put("e".charAt(0), 4);   
    charMap.put("f".charAt(0), 5);  
    charMap.put("g".charAt(0), 6);  
    charMap.put("h".charAt(0), 7);    
    charMap.put("i".charAt(0), 8); 
    charMap.put("j".charAt(0), 9);  
    charMap.put("k".charAt(0), 10); 
    charMap.put("l".charAt(0), 11); 
    charMap.put("m".charAt(0), 12); 
    charMap.put("n".charAt(0), 13);   
    charMap.put("o".charAt(0), 14);
    charMap.put("p".charAt(0), 15);
    charMap.put("q".charAt(0), 16);  
    charMap.put("r".charAt(0), 17); 
    charMap.put("s".charAt(0), 18);   
    charMap.put("t".charAt(0), 19);   
    charMap.put("u".charAt(0), 20);    
    charMap.put("v".charAt(0), 21); 
    charMap.put("w".charAt(0), 22); 
    charMap.put("x".charAt(0), 23); 
    charMap.put("y".charAt(0), 24); 
    charMap.put("z".charAt(0), 25); 
    charMap.put("A".charAt(0), 26); 
    charMap.put("B".charAt(0), 27);    
    charMap.put("C".charAt(0), 28);  
    charMap.put("D".charAt(0), 29);   
    charMap.put("E".charAt(0), 30); 
    charMap.put("F".charAt(0), 31); 
    charMap.put("G".charAt(0), 32); 
    charMap.put("H".charAt(0), 33);
    charMap.put("I".charAt(0), 34);  
    charMap.put("J".charAt(0), 35);    
    charMap.put("K".charAt(0), 36); 
    charMap.put("L".charAt(0), 37);
    charMap.put("M".charAt(0), 38);
    charMap.put("N".charAt(0), 39);
    charMap.put("O".charAt(0), 40);
    charMap.put("P".charAt(0), 41);
    charMap.put("Q".charAt(0), 42);
    charMap.put("R".charAt(0), 43);
    charMap.put("S".charAt(0), 44);   
    charMap.put("T".charAt(0), 45);
    charMap.put("U".charAt(0), 46);
    charMap.put("V".charAt(0), 47);    
    charMap.put("W".charAt(0), 48);
    charMap.put("X".charAt(0), 49);
    charMap.put("Y".charAt(0), 50);
    charMap.put("Z".charAt(0), 51);
    charMap.put("0".charAt(0), 52);    
    charMap.put("1".charAt(0), 53);                
    charMap.put("2".charAt(0), 54);
    charMap.put("3".charAt(0), 55);    
    charMap.put("4".charAt(0), 56);    
    charMap.put("5".charAt(0), 57);    
    charMap.put("6".charAt(0), 58);    
    charMap.put("7".charAt(0), 59);    
    charMap.put("8".charAt(0), 60);    
    charMap.put("9".charAt(0), 61);    
    charMap.put(".".charAt(0), 62);  
    charMap.put("(".charAt(0), 63); 
    charMap.put(")".charAt(0), 64);
    charMap.put("[".charAt(0), 65); 
    charMap.put("]".charAt(0), 66);
    charMap.put("_".charAt(0), 67); 
    charMap.put(":".charAt(0), 68);      
    charMap.put(" ".charAt(0), 69);
    charMap.put("-".charAt(0), 67);
   } 
}
