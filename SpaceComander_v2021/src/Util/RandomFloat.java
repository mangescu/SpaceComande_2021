
package Util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


public class RandomFloat {
    
   public ArrayList<Float>  floats = new ArrayList<Float>();
   DecimalFormat df = new DecimalFormat("#.###");           
   Random rand = new Random();  
    float ranFloat;
    float numar;
    int   count;
    int randInt;
    public static  float starSpacer = 0.0f;   
    public static float  planetsSpacer =0.04f;
    public static float startP = 0.0f;
    
     public float  genShipRandFloat(float min , int divide) {
         float rFloat = -1;
         while(rFloat  < min) {
             rFloat =  rand.nextFloat();
         }  
         if(rand.nextInt(2) == 0) {
             rFloat = rFloat * (-1);
         }   
         rFloat = rFloat / divide;
     return  rFloat;
     }    
  public float generateRandomDistance(boolean randomMode , int switchNumber) {
        
        boolean bool = true;

              while(bool == true)
              {    
                 bool = false;
                 if(randomMode) {
                 count  =  rand.nextInt(10);
                 } else {
                  count = switchNumber;
                 }
                 numar = 0;
                 numar=starSpacer;        
   
                 switch(count) {
                     
                     case 0 :
                          numar += startP + (planetsSpacer * 0);
                          break;
                     case 1 :
                          numar += startP + (planetsSpacer * 1);
                          break;
                     case 2 :
                          numar += startP + (planetsSpacer * 2);
                          break;
                     case 3 :     
                          numar += startP + (planetsSpacer * 3);
                          break;
                     case 4 :
                          numar += startP + (planetsSpacer * 4);
                          break;
                     case 5 :
                          numar += startP + (planetsSpacer * 5) ;
                          break;
                     case 6 :
                          numar += startP + (planetsSpacer * 6);
                          break;
                     case 7 :
                          numar += startP + (planetsSpacer * 7);
                          break;
                     case 8 :
                          numar += startP + (planetsSpacer * 8);
                          break; 
                     case 9 :
                          numar += startP + (planetsSpacer * 9);
                          break;           
                 }    
                 for(int i=0 ; i<floats.size() ; i++) {
                     if(floats.get(i) == numar) {
                        bool = true;   
                     }
                 }
              }
              floats.add(numar);
              float data = Float.valueOf(df.format(numar));    
    return   data;
    } 
 public float  genFloatNumber(int Int ,float min , float max) {
               float rFloat = 1f;
               while(rFloat > max || rFloat < min) {
                  rFloat =  rand.nextFloat();              
               }
                rFloat += Int;
               if(rand.nextInt(2) == 0) {
                 rFloat = rFloat * (-1);
               } 
                             
      return rFloat;
} 
     public float  getFloatRange(float min , float max) {
            float numar = -1;     
            float intF =0;
            while(numar <= min || numar >= max) {
                 numar = rand.nextFloat();
            }
        return numar+intF;
    }
    
    public float  genConstrainedIntNumber(int min , int max ) {
               int   rInt =-1;
               float rFloat = 1f;
               while(rInt  < min )  {     
                      rInt = rand.nextInt(max);
               }
               rFloat =  rand.nextFloat();
               float ranFloat = rInt + rFloat;
               ranFloat = ranFloat * getRandomSign();
                                     
      return ranFloat;
    }     
    public int  getRandomSign(){
        int sign =0;
            if(rand.nextInt(2) == 0) {
                 sign = -1;
            } else {
               sign = 1;
            } 
        return sign;
    }
    
}
