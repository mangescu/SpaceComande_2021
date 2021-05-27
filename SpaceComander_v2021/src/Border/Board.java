package Border;

import Maths.Maths;
import Player.Player;
import Sun.Sun;
import Planet.Planet;
import org.joml.Vector3f;
import spacecomander.Quad;
import spacecomander.QuadsManagement;

import java.util.ArrayList;
import java.util.Random;
import spacecomander.QuadData;

public class Board {
    private QuadsManagement quadManagement;
    private Maths maths;
    private Sun rightSun, closestAngleS, S;
    private float scanDistance = 1.5f;
    float minAngle, curentAngle, distance;
    Ecuation ecuation;
    InvertEcuation invertEcuation;
    BorderVectData tempVect;
    public ArrayList<BorderVectData>       borderIntersectionPoints   = new ArrayList<BorderVectData>();
    public ArrayList<Vector3f>             borderVector3fPoints   = new ArrayList<Vector3f>();
    Random random = new Random();
    Vector3f vec3;
    QuadData quadData;

    public Board(QuadsManagement quadManagement, Maths maths,QuadData quadData) {
        this.maths = maths;
        this.quadData =quadData;
        this.quadManagement = quadManagement;
    }
    public void setupSunChildren() {
        for (Sun sun : quadData.sunList) {
            sun.borderAngle = 0;
            sun.ecuationList.clear();
            sun.invertEList.clear();
            sun.setupDefaultNeighbors();
            setupBoard(sun);
        }
        boolean ok = false;
        for(Sun sun : quadData.sunList)    {
            float sun_spacer = random.nextFloat() / 3;
            if (random.nextInt(2) == 0) {
                ok = true;
            } else {
                ok = false;
            }
            for(Planet p : sun.listaPlanete)    {
                if(ok) {
                    p.position.y += sun_spacer;
                } else {
                    p.position.y -= sun_spacer;
                }
                if(p.ring != null){
                     p.ring.curentPosition.x = p.position.x;
                     p.ring.curentPosition.y = p.position.y;
                }
            }
            if(ok) {
                sun.position.y +=sun_spacer;
            } else {
                sun.position.y -=sun_spacer;
            }
        }
        int vector_spacer = 4;
        
        for(Vector3f bVec : borderVector3fPoints)    {
            if(random.nextInt(2) == 0) {
                bVec.x += random.nextFloat() / vector_spacer;
            }else {
                bVec.x -= random.nextFloat() / vector_spacer;
            }
            if(random.nextInt(2) == 0) {
                bVec.y += random.nextFloat()/vector_spacer;
            } else {
                bVec.y -= random.nextFloat()/vector_spacer;
            }
        }
    }
    public void buildBorder(Player player) {
      player.bordersList.clear();
      
      for (int k = 0; k < player.cStarList.size(); k++)   {
           Sun sun = player.cStarList.get(k);
           addColonyBorder(player, sun);
      }
      removeUnusedEdge(player);
    }
    public void addColonyBorder(Player player , Sun sun){   
        
        if(sun.checkSystemEntity(player.index))   {
          sun.border_type = true;          
        } else {
          sun.border_type = false;  
        }
        for (int i = 0; i < sun.intersectionPoints.size(); i++) {
                if (i == sun.intersectionPoints.size() - 1) {
                    if(sun.border_type) {
                        quadManagement.addBorder(sun.intersectionPoints.get(i).borderPVector, sun.intersectionPoints.get(0).borderPVector, player, 0,true);
                    } else {
                        quadManagement.addBorder(sun.intersectionPoints.get(i).borderPVector, sun.intersectionPoints.get(0).borderPVector, player, 0,false);
                    }
                } else {
                    if(sun.border_type) {
                        quadManagement.addBorder(sun.intersectionPoints.get(i).borderPVector, sun.intersectionPoints.get(i+1).borderPVector, player, 0,true);
                    } else {
                        quadManagement.addBorder(sun.intersectionPoints.get(i).borderPVector, sun.intersectionPoints.get(i+1).borderPVector, player, 0,false);
                    }
                }
        }
        quadManagement.addHex(sun, player);
    }
    public void removeUnusedEdge(Player player)   {
        for (int i = 0; i < player.bordersList.size(); i++)   {
            Quad bord_1 = player.bordersList.get(i);
            for (int j = 0; j < player.bordersList.size(); j++)   {
                Quad bord_2 = player.bordersList.get(j);
                if (player.bordersList.get(i) == player.bordersList.get(j)) {
                    continue;
                }
                if (Math.abs(bord_1.curentPosition.x - bord_2.curentPosition.x) < 0.001f && Math.abs(bord_1.curentPosition.y - bord_2.curentPosition.y) < 0.001f) {
                    if(bord_1.isBorder  && bord_2.isBorder) {
                        bord_1.alive = false;
                        bord_2.alive = false;
                    }
                }
            }
        }
        for (int i = 0; i < player.bordersList.size(); i++) {
            if (!player.bordersList.get(i).alive) {
                player.bordersList.remove(i);
                i--;
            }
        }
    }
    public void setupBoard(Sun star) {
        getStarEcuations(star);
    }
    public void getStarEcuations(Sun centerStar) {
        for (int i = 0; i < centerStar.sunNeighborsList.size(); i++) {
            Ecuation ec = calcEcuation(centerStar, centerStar.sunNeighborsList.get(i));
            calcInvertEcuation(ec, centerStar, centerStar.sunNeighborsList.get(i));
        }
        for (int i = 0; i < centerStar.invertEList.size(); i++) {
            if (i == centerStar.invertEList.size() - 1) {
                getInvertEcuationIntersectionPoint(centerStar.invertEList.get(i), centerStar.invertEList.get(0) ,  centerStar);
            } else {
                getInvertEcuationIntersectionPoint(centerStar.invertEList.get(i), centerStar.invertEList.get(i + 1) ,  centerStar);
            }
        }
    }
    public Ecuation calcEcuation(Sun centralStar, Vector3f curentSun)   {
        ecuation = new Ecuation();
        if (centralStar.position.x != curentSun.x && centralStar.position.y != curentSun.y) {
            ecuation.a = ((centralStar.position.y - curentSun.y) / (centralStar.position.x - curentSun.x));
            ecuation.b = centralStar.position.y - (ecuation.a * centralStar.position.x);
            ecuation.c = 1;
            ecuation.A.x = centralStar.position.x + 0.5f;
            ecuation.B.x = centralStar.position.x - 0.5f;
            ecuation.loadPoint();
            centralStar.ecuationList.add(ecuation);
        }
        if (centralStar.position.y == curentSun.y) {
            ecuation.a = 0;
            ecuation.b = centralStar.position.y;
            ecuation.c = 1;
            ecuation.A.x = centralStar.position.x + 1f;
            ecuation.B.x = centralStar.position.x - 1f;
            ecuation.loadPoint();
            centralStar.ecuationList.add(ecuation);
        }
        if (centralStar.position.x == curentSun.x) {
            ecuation.a = 1;
            ecuation.b = centralStar.position.x;
            ecuation.c = 0;
            ecuation.A.y = centralStar.position.y + 1f;
            ecuation.B.y = centralStar.position.y - 1f;
            ecuation.loadPoint();
            centralStar.ecuationList.add(ecuation);
        }
        return ecuation;
    }
    public void calcInvertEcuation(Ecuation ecuation, Sun centralStar, Vector3f curentSun) {
        float x_mid = 0, y_mid = 0;
        x_mid = Math.min(centralStar.position.x, curentSun.x) + Math.abs((centralStar.position.x - curentSun.x) / 2);
        y_mid = Math.min(centralStar.position.y, curentSun.y) + Math.abs((centralStar.position.y - curentSun.y) / 2);
        invertEcuation = new InvertEcuation();
        if (centralStar.position.x != curentSun.x && centralStar.position.y != curentSun.y) {
            invertEcuation.a = -(1 / ecuation.a);
            invertEcuation.b = ((-1 / ecuation.a) * -x_mid) + y_mid;
            invertEcuation.c = 1;
            invertEcuation.A.x = x_mid + 0.25f;
            invertEcuation.B.x = x_mid - 0.25f;
            invertEcuation.loadPoint();
            centralStar.invertEList.add(invertEcuation);
        }
        if (centralStar.position.y == curentSun.y) {
            invertEcuation.a = 1;
            invertEcuation.b = x_mid;
            invertEcuation.c = 0;
            invertEcuation.A.y = y_mid + 0.25f;
            invertEcuation.B.y = y_mid - 0.25f;
            invertEcuation.loadPoint();
            centralStar.invertEList.add(invertEcuation);
        }
        if (centralStar.position.x == curentSun.x) {
            invertEcuation.a = 0;
            invertEcuation.b = y_mid;
            invertEcuation.c = 1;
            invertEcuation.A.x = x_mid + 0.25f;
            invertEcuation.B.x = x_mid - 0.25f;
            invertEcuation.loadPoint();
            centralStar.invertEList.add(invertEcuation);
        }
        invertEcuation.vec = curentSun;
    }

    public void getInvertEcuationIntersectionPoint(InvertEcuation ec_1, InvertEcuation ec_2 , Sun centerStar) {
        float X = 0;
        float Y = 0;
        if (ec_1.c == 1 && ec_2.c == 1 && ec_1.a != 0 && ec_2.a != 0) {
            X = -(ec_1.b - ec_2.b) / (ec_1.a - ec_2.a);
            Y = ec_1.a * X + ec_1.b;
            centerStar.intersectionPoints.add(getIFExist(X,Y));
        } else {
            if (ec_1.c == 0 && ec_2.c != 0) {
                X = ec_1.b;
                Y = (ec_2.a * X) + ec_2.b;
                centerStar.intersectionPoints.add(getIFExist(X,Y));
            }
            if (ec_2.c == 0 && ec_1.c != 0) {
                X = ec_2.b;
                Y = (ec_1.a * X) + ec_1.b;
                centerStar.intersectionPoints.add(getIFExist(X,Y));
            }
            if (ec_1.a == 0 && ec_2.a != 0) {
                Y = ec_1.b;
                X = (Y - ec_2.b) / ec_2.a;
                centerStar.intersectionPoints.add(getIFExist(X,Y));
            }
            if (ec_2.a == 0 && ec_1.a != 0) {
                Y = ec_2.b;
                X = (Y - ec_1.b) / ec_1.a;
                centerStar.intersectionPoints.add(getIFExist(X,Y));
            }
        }

    }
    public BorderVectData getIFExist(float X , float Y)    {
        BorderVectData  tempBVect = null;
        for(BorderVectData bVec : borderIntersectionPoints) {
            if(Math.abs(X - bVec.borderPVector.x) < 0.05f  &&  (Math.abs(Y - bVec.borderPVector.y) < 0.05f)) {
                tempBVect  = bVec;
           }
        }
        if(tempBVect == null) {
            vec3 = new Vector3f(X,Y, 0);
            tempBVect = new BorderVectData(vec3, 0.0005f, 1);
            borderVector3fPoints.add(vec3);
            borderIntersectionPoints.add(tempBVect);
        }
        return tempBVect;
    }

}
