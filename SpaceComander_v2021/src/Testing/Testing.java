
package Testing;

import java.util.ArrayList;

class Race {
    int race;
    
    public Race(int race){
       this.race = race;
    }
}

class Dog {
   Race race ;
   public Dog(Race race){
     this.race = race;
   }
}
class  Animal {
    Dog dog;
    public Animal(Dog dog){
        this.dog = dog;
    }

}
class Entity {
   int number;
   public Entity(int number){
     this.number = number;
   }
   //ArrayList<Animal>   list  = new ArrayList<Animal>() ;
}


public class Testing {
    
//______FPS___________________________________________________________//
    private double start = System.nanoTime() / 1000000000;
    private int valoare = 0;
    private double end;
//____________________________________________________________________//     
    
 Entity obj[] = new Entity[100000];
    
    int sum;
    
    public static void main(String[] args){
    
        Testing  test = new Testing();
        test.test_1();
    }
    public void test_1(){
    
   
        Integer[]   list = new Integer[100000]; 
        ArrayList<Entity>   listEntity = new ArrayList<Entity>();
        
       
        
        for(int i=0 ; i< 100000 ; i++){
            obj[i] = new Entity(i);
            listEntity.add(new Entity(i));
            list[i] = i;
;        }
        
     boolean done = true;
     while(done) {
        FPS();
        
           sum = 0;
           
 //  /*    
        for(int i=0 ; i< listEntity.size() ; i++){
             sum  += listEntity.get(i).number;
        }
//   */
  
  
    /*    
          for(int i=0 ; i< list.length ; i++){
               sum  += list[i];
          }   
   */         
       
  
    /*
           for(int i=0 ; i< obj.length ; i++){
               sum  += obj[i].number;
          }   
   */
  
     }
    }
    
    
    
    
    public void FPS(){        

        end = System.nanoTime() / 1000000000;
        if (end - start > 1) {
            start = end;
                System.out.println(String.valueOf(valoare/2));
              valoare = 0;
        }
        valoare++;
    }     
    
}
