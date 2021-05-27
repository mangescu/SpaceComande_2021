
package audio;

import Camera.Camera;
import java.io.IOException;
import spacecomander.Quad;
import spacecomander.SpaceComander;

public class SoundMainClass {
    int explosionSource_index = 3;
    int phaserSource_index    = 2;
    int borgPhaserSource_index = 3;
    int rapidFiredisrupterSourceA_index = 7;
    int standardDisrupterSourceA_index = 3;
    int shieldImpactSource_index = 3;
    
    public Source fundalSource;
    Source torpedoSource;
    Source[]  explosionSourceList = new Source[explosionSource_index];
    Source[]  phaserSourceList    = new Source[phaserSource_index]; 
    Source[]  borgPhaserSourceList    = new Source[borgPhaserSource_index];  
    Source[]  rapidFiredisrupterSourceAList    = new Source[rapidFiredisrupterSourceA_index];  
    Source[]  standardDisrupterSourceAList    = new Source[standardDisrupterSourceA_index];
    Source[]  shieldImpactSourceList    = new Source[shieldImpactSource_index];
    
    public int   explosionSound , shieldImpactSound  , borgPhaserSound , borgLaseCuterSound , forceFieldSound , phaserSound , torpedoSound  ,disruptorSound , fundal ;
            
    Camera camera;
    
    float  soundDistance = 1.5f;
    
    public SoundMainClass(Camera camera) throws IOException {
        this.camera =camera;
        AudioMaster.init();
        AudioMaster.setListenerData(0,0,0);   
        
      //  fundal = AudioMaster.loadSound("res/audio/Fantascape.wav");  
        phaserSound = AudioMaster.loadSound("res/audio/phaser.wav"); 
        explosionSound = AudioMaster.loadSound("res/audio/explosion.wav");
        borgPhaserSound = AudioMaster.loadSound("res/audio/borgPhaser.wav");
        borgLaseCuterSound = AudioMaster.loadSound("res/audio/borgLaserCuter.wav");
        torpedoSound = AudioMaster.loadSound("res/audio/torpedo.wav");
        disruptorSound = AudioMaster.loadSound("res/audio/disruptor.wav");
        shieldImpactSound = AudioMaster.loadSound("res/audio/forcefieldTouch.wav");        
        
        fundalSource = new Source();
        fundalSource.setLooping(true);
        fundalSource.setVolume(0.25f/SpaceComander.volume);
        
        torpedoSource = new Source();
        torpedoSource.setVolume(0.3f/SpaceComander.volume);
        
        for(int i=0 ; i< explosionSource_index ; i++)    {
          explosionSourceList[i] = new Source();
          explosionSourceList[i].setVolume(0.25f/SpaceComander.volume);
        }
        for(int i=0 ; i< phaserSource_index ; i++)    {
          phaserSourceList[i] = new Source();
          phaserSourceList[i].setVolume(0.25f/SpaceComander.volume);
        }       
        for(int i=0 ; i< borgPhaserSource_index ; i++)    {
          borgPhaserSourceList[i] = new Source();
          borgPhaserSourceList[i].setVolume(0.35f/SpaceComander.volume);
        }   
        for(int i=0 ; i< rapidFiredisrupterSourceA_index ; i++)    {
          rapidFiredisrupterSourceAList[i] = new Source();
          rapidFiredisrupterSourceAList[i].setVolume(0.25f/SpaceComander.volume);
        } 
        for(int i=0 ; i< standardDisrupterSourceA_index ; i++)    {
          standardDisrupterSourceAList[i] = new Source();
          standardDisrupterSourceAList[i].setVolume(0.25f/SpaceComander.volume);
        }         
        for(int i=0 ; i< shieldImpactSource_index ; i++)    {
          shieldImpactSourceList[i] = new Source();
          shieldImpactSourceList[i].setVolume(0.3f/SpaceComander.volume);
        }  
        
      //  playFundalMusic();
    }
    public void playTorpedoSound(){
        if(camera.getPosition().z <= soundDistance) {
            torpedoSource.play(torpedoSound);
        }
    }
    public void   playExplosionSource()  {
        if(camera.getPosition().z <= soundDistance) {
            for(int i=0 ; i< explosionSource_index ; i++)  {
                if(!explosionSourceList[i].isPlaying()) {
                    explosionSourceList[i].play(explosionSound);
                    i = explosionSource_index;
                }
            }
        }
    }
    public void playPhaserSource(Quad ship)
    {
        if(camera.getPosition().z <= soundDistance) {
            for(int i=0 ; i< phaserSource_index ; i++)   {
                if(!phaserSourceList[i].isPlaying())  {
                     if(ship.player.index != 5)   {
                         phaserSourceList[i].play(phaserSound);
                         i = phaserSource_index;
                     }
                }
            } 
            for(int i=0 ; i< borgPhaserSource_index ; i++)  {
                if(!borgPhaserSourceList[i].isPlaying())  {
                    if(ship.player.index == 5)   {
                        if(ship.isMotherShip || ship.isCarrierShip) {
                            borgPhaserSourceList[i].play(borgPhaserSound);
                            i = borgPhaserSource_index;
                        } else {
                                  borgPhaserSourceList[i].play(borgLaseCuterSound);
                                  i = borgPhaserSource_index;
                               }
                    }
                }
            }
        }
    }
    public void playStandarDisrupterFire(){
        if(camera.getPosition().z <= soundDistance) {
            for(int i=0 ; i< standardDisrupterSourceA_index ; i++){
                if(!standardDisrupterSourceAList[i].isPlaying())  {
                    standardDisrupterSourceAList[i].play(disruptorSound);
                    i = standardDisrupterSourceA_index;
                }
            }
        }
    }
    public void playRapidFireDisruptorSound(){
        if(camera.getPosition().z <= soundDistance) {
            for(int i=0 ; i< rapidFiredisrupterSourceA_index ; i++){
                if(!rapidFiredisrupterSourceAList[i].isPlaying())    {
                    rapidFiredisrupterSourceAList[i].play(disruptorSound);
                    i = rapidFiredisrupterSourceA_index;
                }
            }
        }
    }
    public void playShieldImpactSource(){
        if(camera.getPosition().z <= soundDistance) {
            for(int i=0 ; i< shieldImpactSource_index ; i++){
                if(!shieldImpactSourceList[i].isPlaying())  {
                    shieldImpactSourceList[i].play(shieldImpactSound);
                    i = shieldImpactSource_index;
                }
            }
        }
    }    
    public void playFundalMusic(){
        fundalSource.play(fundal);
    }
    public void delSources(){
        fundalSource.delete();
        torpedoSource.delete();
        for(int i=0 ; i< explosionSource_index ; i++)    {
          explosionSourceList[i].delete();
        }
        for(int i=0 ; i< phaserSource_index ; i++)    {
          phaserSourceList[i].delete();
        }       
        for(int i=0 ; i< borgPhaserSource_index ; i++)    {
           borgPhaserSourceList[i].delete();
        }   
        for(int i=0 ; i< rapidFiredisrupterSourceA_index ; i++)    {
          rapidFiredisrupterSourceAList[i].delete();
        } 
        for(int i=0 ; i< standardDisrupterSourceA_index ; i++)    {
          standardDisrupterSourceAList[i].delete();
        }         
        for(int i=0 ; i< shieldImpactSource_index ; i++)    {
          shieldImpactSourceList[i].delete();
        }      
    }
    public void cleanUP(){   
       delSources();
       AudioMaster.cleanUP();
    }
}
