package Border;

import org.joml.Vector3f;

public class InvertEcuation {

    Point A,B;
    Vector3f vec;
    public float a,b,c;

    public InvertEcuation(){
        A = new Point(5);
        B = new Point(3);
    }
    public void loadPoint()
    {
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
