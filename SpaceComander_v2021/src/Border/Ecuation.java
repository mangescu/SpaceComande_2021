package Border;

public class Ecuation {
    Point A,B;
    public float a,b,c;
    
    public Ecuation()   {
      A = new Point(5);
      B = new Point(3);
    }
    public void loadPoint()    {
        if(c != 0 && a != 0) {
            A.y = a* A.x + b;
            B.y = a* B.x + b;
        }
        if(c == 0) {
            A.x =  b;
            B.x =  b;
        }
        if(a == 0) {
            A.y = b;
            B.y = b;
        }
    }
}
