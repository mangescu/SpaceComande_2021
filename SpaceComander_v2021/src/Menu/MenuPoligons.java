
package Menu;

import Camera.Camera;
import IBO.IBO;
import QuadModel.Model;
import Texture.Textura;
import java.util.ArrayList;
import org.joml.Vector3f;
import spacecomander.Quad;

public class MenuPoligons {
    
    IBO ibo;
    Model model;
    ArrayList<Quad>  menuPoliList;
    Quad  poligon;

    public  MenuPoligons(IBO ibo , ArrayList<Quad>  menuPoliList) {
        this.ibo = ibo;
        this.menuPoliList = menuPoliList;
    }  
    public  Quad createPoligon(Textura poliTex , Camera cam, float x , float y   , float width , float height , int index, float  rotateZ)   {
        poligon = new Quad();
        poligon.curentPosition.x = cam.getPosition().x + x;
        poligon.curentPosition.y = cam.getPosition().y + y;
        poligon.curentPosition.z = cam.getPosition().z - 0.25f;
        poligon.scale.x = width;
        poligon.scale.y = height;
        model = new Model(ibo, poliTex, 0, poligon.curentPosition, new Vector3f(0,0,rotateZ) , poligon.scale);
        model.INDEX_T  = index;
        poligon.model = model;
        menuPoliList.add(poligon);
        
        return poligon;
    }   
}
