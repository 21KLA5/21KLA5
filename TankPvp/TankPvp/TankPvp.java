
//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

//image imports
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

class TankPvp{
  static int gameRestart = 0;
  
// static variables
  static JFrame gameWindow;
  static GraphicsPanel canvas;
  static final int WIDTH = 1500;
  static final int HEIGHT = 900;
  static int counter = 0;
  static int count = 0;
  static int tankOneX = 400;
  static int tankOneY = 450;
  static int tankTwoX = 600;
  static int tankTwoY = 450;
  static Rectangle tankbox1 = new Rectangle(tankOneX, tankOneY, 80, 80);
  static Rectangle tankbox2 = new Rectangle(tankTwoX, tankTwoY, 80, 80);
  static Rectangle r1 = new Rectangle(100, 0, 10, 100);
  static Rectangle r2 = new Rectangle(100, 100, 100, 10);
  static Rectangle r3 = new Rectangle(730, 400, 10, 200);
  static Rectangle r4 = new Rectangle(730, 400, 200, 10);
  static Rectangle r5 = new Rectangle(1100, 0, 10, 400);
  static Rectangle r6 = new Rectangle(200, 600, 10, 500);
  static Rectangle r7 = new Rectangle(540, 400, 10, 500);
  static Rectangle r8 = new Rectangle(0, 400, 300, 10);
  static Rectangle r9 = new Rectangle(730, 600, 400, 10);
  
  static Rectangle b1 = new Rectangle(0,0, 1500, 10);
  static Rectangle b2 = new Rectangle(0,0,10,900);
  static Rectangle b3 = new Rectangle(0,851, 1500, 10);
  static Rectangle b4 = new Rectangle(1475, 0, 10, 900);
        
  static int collision = 0; 
  static int collisionOne = 0;
  static int sandMovement = 10;
  static int sandMovementOne = 10;
  static int tankShot = 0;
  static int tankOneScore = 0;
  static int tankTwoScore = 0;
  static int gameOver = 0;
  
  
  static BufferedImage battlefield;
  static int battlefieldX = 0;
  static int battlefieldY = 0;
  static int battlefieldW = 1500;
  static int battlefieldH = 900;
  
  static BufferedImage sand;
  static int sandX = 0;
  static int sandY = 0;
  static int sandW = 1500;
  static int sandH = 900;
  
  //bullet 1
  static int numBullets = 40;    
  static int[] bulletX = new int[numBullets];
  static int[] bulletY = new int[numBullets];
  static boolean[] bulletVisible = new boolean[numBullets];
  static int bulletW = 6;
  static int bulletH = 10;
  static int bulletSpeed = 10;
  static int currentBullet = 0;
  
  //bullet 2
  static int numBullets2 = 40;    
  static int[] bulletX2 = new int[numBullets2];
  static int[] bulletY2 = new int[numBullets2];
  static boolean[] bulletVisible2 = new boolean[numBullets2];
  static int bulletW2 = 6;
  static int bulletH2 = 10;
  static int bulletSpeed2 = -10;
  static int currentBullet2 = 0;
  
  // mouse listeners
  static MyMouseListener mouseListener = new MyMouseListener();
  static MyMouseMotionListener mouseMotionListener = new MyMouseMotionListener(); 
  
  //key listener
  static MyKeyListener keyListener = new MyKeyListener(); 
  
  static int moveTank1X = 0;
  static int moveTank1Y = 0;
  
  static int moveTank2X = 0;
  static int moveTank2Y = 0;
  
  static int stepTank1 = 5;
  static int stepTank2 = 5;
  
  
//------------------------------------------------------------------------------    
  public static void main(String[] args){
    
    gameWindow = new JFrame("Game Window");
    gameWindow.setSize(WIDTH,HEIGHT);
    gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    canvas = new GraphicsPanel();
    canvas.addMouseListener(mouseListener);
    canvas.addMouseMotionListener(mouseMotionListener);
    gameWindow.add(canvas); 
    canvas.addKeyListener(keyListener);
    gameWindow.add(canvas); 
    
    try {                
      battlefield = ImageIO.read(new File("battlefield.png"));
      sand = ImageIO.read(new File("sand.jpg"));
    } catch (IOException ex){}    
    
    // generate bullets
    Arrays.fill(bulletX,0);
    Arrays.fill(bulletY,0);
    Arrays.fill(bulletVisible,false);
    
    Arrays.fill(bulletX2,0);
    Arrays.fill(bulletY2,0);
    Arrays.fill(bulletVisible2,false);

    gameWindow.setVisible(true);
    runGameLoop();
    
  } // main method end
  
//------------------------------------------------------------------------------   
  public static void runGameLoop(){
    while (true) {
      gameWindow.repaint();
      try  {Thread.sleep(20);} catch(Exception e){}
      
      
      //border for tank 1
      if(b1.intersects(tankbox1)){
        tankOneY = tankOneY + 7 ;
      }
      if(b2.intersects(tankbox1)){
        tankOneX = tankOneX + 7 ;
      }
      if(b3.intersects(tankbox1)){
        tankOneY = tankOneY - 7 ;
      }
      if(b4.intersects(tankbox1)){
        tankOneX = tankOneX - 7 ;
      }
      
      //border for tank 2
      
      if(b1.intersects(tankbox2)){
        tankTwoY = tankTwoY + 7 ;
      }
      if(b2.intersects(tankbox2)){
        tankTwoX = tankTwoX + 7 ;
      }
      if(b3.intersects(tankbox2)){
        tankTwoY = tankTwoY - 7 ;
      }
      if(b4.intersects(tankbox2)){
        tankTwoX = tankTwoX - 7 ;
      }
      
      // move the bullets
      for (int i=0; i<numBullets; i++){
        if (bulletVisible[i]){
          bulletX[i] = bulletX[i] + bulletSpeed;
          if (bulletX[i]<0)
            bulletVisible[i] = false;
        }
        
      }      
      
      for (int i=0; i<numBullets2; i++){
        if (bulletVisible2[i]){
          bulletX2[i] = bulletX2[i] + bulletSpeed2;
          if (bulletX2[i]<0)
            bulletVisible2[i] = false;
        }
      }
      
      if (checkCollisionWall(tankbox1)){
        stepTank1 = 1;
      } else {
        stepTank1 = 7;
      }
      
      if (checkCollisionWall(tankbox2)){
        stepTank2 = 1;
      } else {
        stepTank2 = 7;
      }
      
      tankOneX = tankOneX + moveTank1X;
      tankOneY = tankOneY + moveTank1Y;
      tankbox1.setLocation(tankOneX,tankOneY);
      
      tankTwoX = tankTwoX + moveTank2X;
      tankTwoY = tankTwoY + moveTank2Y;
      tankbox2.setLocation(tankTwoX,tankTwoY); 
      
      
    }

 
    
    
    
    
    
  } // runGameLoop method end
  
  
  
//method for collision tank1
  public static boolean checkCollisionWall(Rectangle collisionObject){
    if (r1.intersects(collisionObject)){
      return true;
    } else if (r2.intersects(collisionObject)){
      return true;
    } else if (r3.intersects(collisionObject)){
      return true;
    } else if (r4.intersects(collisionObject)){
      return true;
    } else if (r5.intersects(collisionObject)){
      return true;
    } else if (r6.intersects(collisionObject)){
      return true;
    } else if (r7.intersects(collisionObject)){
      return true;
    } else if (r8.intersects(collisionObject)){
      return true;
    } else if (r9.intersects(collisionObject)){
      return true;
    }return false;  
  }
  
 
    
    
//-----------------------------------------------------------------------------  
  static class GraphicsPanel extends JPanel{
    public GraphicsPanel(){
      setFocusable(true);
      requestFocusInWindow();
    }
    public void paintComponent(Graphics g){ 
      super.paintComponent(g); //required
      g.setColor(Color.black);
      
      if(counter == 0){ //Main screen title page
        g.drawImage(battlefield,battlefieldX,battlefieldY,this);
        
        //how to play button 
        g.setColor(Color.black);
        g.fillRect(975, 380, 300, 100);
        
        //play button 
        g.setColor(Color.white);
        g.fillRect(200, 380, 300, 100);
        
        //tank 1
        g.setColor(Color.white);
        g.fillRect(120, 120, 100, 100);
        
        g.setColor(Color.gray);
        g.fillRect(100, 130, 20, 90);
        
        g.setColor(Color.gray);
        g.fillRect(220, 130, 20, 90);
        
        g.setColor(Color.gray);
        g.fillRect(160, 50, 20, 90);
        
        g.setColor(Color.darkGray);
        for (int y=130; y<220; y += 10)
          g.drawLine(100,y,120,y); 
        
        g.setColor(Color.darkGray);
        for (int y=130; y<220; y += 10)
          g.drawLine(220,y,240,y); 
        
        //tank 2
        g.setColor(Color.black);
        g.fillRect(1260, 120, 100, 100);
        
        g.setColor(Color.gray);
        g.fillRect(1240, 130, 20, 90);
        
        g.setColor(Color.gray);
        g.fillRect(1360, 130, 20, 90);
        
        g.setColor(Color.gray);
        g.fillRect(1300, 50, 20, 90);
        
        g.setColor(Color.darkGray);
        for (int y=130; y<220; y += 10)
          g.drawLine(1240,y,1260,y); 
        
        for (int y=130; y<220; y += 10)
          g.drawLine(1360,y,1380,y); 
        
        
        
        //title words
        g.setColor(Color.white);
        int largeSize = 108;
        Font largeFont = new Font("Times New Roman", Font.BOLD, largeSize);
        g.setFont(largeFont); 
        g.drawString("Tank", 350, 250);
        
        g.setColor(Color.black);
        g.setFont(largeFont); 
        g.drawString("PVP", 900, 250);
        
        g.setColor(Color.white);
        int smallSize = 48;
        Font smallFont = new Font("Times New Roman", Font.BOLD, smallSize);
        g.setFont(smallFont);
        g.drawString("How to Play", 1000, 450);
        
        g.setColor(Color.black);
        g.setFont(smallFont);
        g.drawString("Start Game", 225, 450);
        
        
        
      }
      
      //how to play page 
      if(counter == 1){ 
        g.setColor(Color.black);
        g.fillRect(0, 0, 1500, 900);
        
        //title
        g.setColor(Color.white);
        int wordSize = 72;
        Font wordFont = new Font("Times New Roman", Font.BOLD, wordSize);
        g.setFont(wordFont);
        g.drawString("How to Play", 550, 220);
        
        //instructions and controls
        g.setColor(Color.white);
        int smallSize = 40;
        Font smallFont = new Font("Times New Roman", Font.BOLD, smallSize);
        g.setFont(smallFont);
        g.drawString("Player 1 Controls:", 100, 450);
        g.drawString("Player 2 Controls:", 100, 550);
        
        g.setColor(Color.white);
        int wSize = 30;
        Font wFont = new Font("Times New Roman", Font.BOLD, wSize);
        g.setFont(wFont);
        g.drawString("The main objective of this game is to destroy the opposing player's tank!", 100, 300);
        g.drawString("Try your best to avoid the walls of quicksand as they will slow you down if stuck inside", 100, 330);
        g.drawString("To do so use the controls below to move and shoot your opponent:", 100, 360);
        g.drawString(" \"W\" = Forward, \"A\" = Left, \"S\" = Down, \"D\" = Right, \"Space\" = Shoot ", 410, 450);
        g.drawString(" \"5\" = Forward, \"1\" = Left, \"2\" = Down, \"3\" = Right, \"ENTER\" = Shoot ", 410, 550);
        
        
        //back button
        g.setColor(Color.white);
        g.fillRect(1200, 600, 200, 100);
        
        g.setColor(Color.black);
        smallSize = 56;
        smallFont = new Font("Times New Roman", Font.BOLD, smallSize);
        g.setFont(smallFont);
        g.drawString("Back", 1230, 670);
      }
      
      //After your click play
      if (counter == 2){
        
        g.drawImage(sand,sandX,sandY,this);
        
        //maze
        
        g.setColor(Color.gray);
        g.fillRect(100, 0, 10, 100);
        g.fillRect(100, 100, 100, 10);
        g.fillRect(730, 400, 10, 200);
        g.fillRect(730, 400, 200, 10);
        g.fillRect(730, 600, 400, 10);
        g.fillRect(1100, 0, 10, 400);
        g.fillRect(200, 600, 10, 500);
        g.fillRect(540, 400, 10, 500);
        g.fillRect(0, 400, 300, 10);
        
        //borders
        g.setColor(Color.gray);
        g.fillRect(0,0, 1500, 10);
        g.fillRect(0,0,10,900);
        g.fillRect(0,851, 1500, 10);
        g.fillRect(1475, 0, 10, 900);
        
        
        //tank 1
        g.setColor(Color.black);
        g.fillRect(tankOneX, tankOneY, 80, 80);
        
        g.setColor(Color.gray);
        g.fillOval(tankOneX+20, tankOneY+20, 40, 40);
        
        //tank 2
        g.setColor(Color.green);
        g.fillRect(tankTwoX, tankTwoY, 80, 80);
        
        g.setColor(Color.gray);
        g.fillOval(tankTwoX+20, tankTwoY+20, 40, 40);
        
        g.setColor(Color.red);
        for (int i=0; i<numBullets; i++){
          if (bulletVisible[i])
            g.fillOval(bulletX[i],bulletY[i],bulletW,bulletH);
        }
        
        g.setColor(Color.red);
        for (int i=0; i<numBullets2; i++){
          if (bulletVisible2[i])
            g.fillOval(bulletX2[i],bulletY2[i],bulletW2,bulletH2);
        }
        
        //scoreboard
        if (tankShot == 1){
          int wSize = 30;
          Font wFont = new Font("Times New Roman", Font.BOLD, wSize);
          g.setFont(wFont);
          g.drawString("Tank 1 =" + tankOneScore + "Tank 2 =" + tankTwoScore, 200, 300); 
          g.drawString("Please click enter to continue", 200, 330);
        }
        //game over
        if ((tankOneScore == 5) || (tankTwoScore == 5)){
          g.setColor(Color.black);
          g.fillRect(0, 0, 1500, 900);
          
          //button
          g.setColor(Color.white);
          g.fillRect(600, 380, 275, 100);
          
          g.setColor(Color.black);
          int smallSize = 60;
          Font smallFont = new Font("Times New Roman", Font.BOLD, smallSize);
          g.setFont(smallFont);
          g.drawString("Restart", 640, 450);
          
          g.setColor(Color.white);
          int wordSize = 72;
          Font wordFont = new Font("Times New Roman", Font.BOLD, wordSize);
          g.setFont(wordFont);
          g.drawString("Game Over", 550, 200);
          
          gameOver = 1;
          
          
        }
        
      }
      
      
      
      
      
      // do all your drawings here      
      
    } // paintComponent method end
  } // GraphicsPanel class end
  
//------------------------------------------------------------------------------ 
  static class MyMouseListener implements MouseListener{
    public void mouseClicked(MouseEvent e){
      System.out.println("Mouse clicked at X:"+e.getX() + " Y:"+e.getY());
    }
    public void mousePressed(MouseEvent e){   
      int mouseX = e.getX();
      int mouseY = e.getY();
      if (counter == 0){
        if ((mouseX >= 975) && (mouseX <= 1275)){
          if ((mouseY >= 380) && (mouseY <= 480)){
            counter = 1;
          }
        }
        
        if ((mouseX >= 200) && (mouseX <= 500)){
          if ((mouseY >= 380) && (mouseY <= 480)){
            counter = 2;
          }
        }
      }
      
      if (counter == 1){
        if ((mouseX >= 1200) && (mouseX <= 1400)){
          if ((mouseY >= 600) && (mouseY <= 700)){
            counter = 0;
          }
        }
      }
      
      if ((tankOneScore == 5) || (tankTwoScore == 5)){
        if ((mouseX >= 600 ) && (mouseX <= 875 )){
          if ((mouseY >= 380 ) && (mouseY <= 480 )){
            //restart game variables
            gameRestart = 0;
            counter = 0;
            count = 0;
            tankOneX = 400;
            tankOneY = 450;
            tankTwoX = 600;
            tankTwoY = 450;
            tankbox1 = new Rectangle(tankOneX, tankOneY, 80, 80);
            tankbox2 = new Rectangle(tankTwoX, tankTwoY, 80, 80);
            r1 = new Rectangle(100, 0, 10, 100);
            r2 = new Rectangle(100, 100, 100, 10);
            r3 = new Rectangle(730, 400, 10, 200);
            r4 = new Rectangle(730, 400, 200, 10);
            r5 = new Rectangle(1100, 0, 10, 400);
            r6 = new Rectangle(200, 600, 10, 500);
            r7 = new Rectangle(540, 400, 10, 500);
            r8 = new Rectangle(0, 400, 300, 10);
            r9 = new Rectangle(730, 600, 400, 10);
            collision = 0; 
            collisionOne = 0;
            sandMovement = 10;
            sandMovementOne = 10;
            tankShot = 0;
            tankOneScore = 0;
            tankTwoScore = 0;
            gameOver = 0;            
            BufferedImage battlefield;
            battlefieldX = 0;
            battlefieldY = 0;
            battlefieldW = 1500;
            battlefieldH = 900;           
            BufferedImage sand;
            sandX = 0;
            sandY = 0;
            sandW = 1500;
            sandH = 900;            
            numBullets = 40;    
            bulletX = new int[numBullets];
            bulletY = new int[numBullets];
            bulletVisible = new boolean[numBullets];
            bulletW = 6;
            bulletH = 10;
            bulletSpeed = -10;
            currentBullet = 0;
            
          }
        }
      }
    }
    
    
    public void mouseReleased(MouseEvent e){ 
      
    }
    public void mouseEntered(MouseEvent e){
      System.out.println("Mouse entered at X:"+e.getX() + " Y:"+e.getY());
    }
    public void mouseExited(MouseEvent e){
      System.out.println("Mouse exited at X:"+e.getX() + " Y:"+e.getY());
    }
  } // MyMouseListener class end
  
//------------------------------------------------------------------------------     
  static class MyMouseMotionListener implements MouseMotionListener{
    public void mouseMoved(MouseEvent e){
    }
    public void mouseDragged(MouseEvent e){  
      
    }        
  } // MyMouseMotionListener class end
  
//keyboard
  static class MyKeyListener implements KeyListener{   
    // method to process key pressed events (when a key goes down, i.e. immediately)
    public void keyPressed(KeyEvent e){            
      
      int key = e.getKeyCode();
      
      if (tankShot == 0){
        if (key == KeyEvent.VK_LEFT){
          moveTank1X = -stepTank1;
        } 
        
        else if (key == KeyEvent.VK_RIGHT){
          moveTank1X = stepTank1;
        } 
        
        else if (key == KeyEvent.VK_UP){
          moveTank1Y = -stepTank1;
        } 
        
        else if (key == KeyEvent.VK_DOWN){
          moveTank1Y = stepTank1;
        }
        
        if (key == KeyEvent.VK_A){
          moveTank2X = -stepTank2;
        } 
        
        else if (key == KeyEvent.VK_D){
          moveTank2X = stepTank2;
        } 
        
        if (key == KeyEvent.VK_W){
          moveTank2Y = -stepTank2;
        } 
        
        else if (key == KeyEvent.VK_S){
          moveTank2Y = stepTank2;
        }
      }
      if (key == 10){
        tankShot = 0;
      }
      
      sandMovement = 10;
    }
    
    // method to process key released events (when a key goes up)
    public void keyReleased(KeyEvent e){ 
      int key = e.getKeyCode();
      if (key == KeyEvent.VK_ESCAPE){
        gameWindow.dispose();
      }
      if (tankShot == 0){
        if (key == KeyEvent.VK_LEFT){
          moveTank1X = 0;
        } 
        
        else if (key == KeyEvent.VK_RIGHT){
          moveTank1X = 0;
        } 
        
        else if (key == KeyEvent.VK_UP){
          moveTank1Y = 0;
        } 
        
        else if (key == KeyEvent.VK_DOWN){
          moveTank1Y = 0;
        }
        if (key == KeyEvent.VK_A){
          moveTank2X = 0;
        } 
        
        else if (key == KeyEvent.VK_D){
          moveTank2X = 0;
        } 
        
        if (key == KeyEvent.VK_W){
          moveTank2Y = 0;
        } 
        
        else if (key == KeyEvent.VK_S){
          moveTank2Y = 0;
        }
      }
    }   
    // method to process key typed events (only typeable/printable keys)
    public void keyTyped(KeyEvent e){
      
      char keyChar = e.getKeyChar();
      
      if (keyChar == KeyEvent.VK_ENTER){
        // assign the coordinates of the top middle point of the ship to the current bullet
        bulletX[currentBullet] = tankOneX + 70;
        bulletY[currentBullet] = tankOneY + 40;
        bulletVisible[currentBullet] = true;
        currentBullet = (currentBullet + 1)%numBullets;
      }
      
      if (keyChar == KeyEvent.VK_SPACE){
        // assign the coordinates of the top middle point of the ship to the current bullet
        bulletX2[currentBullet2] = tankTwoX;
        bulletY2[currentBullet2] = tankTwoY + 40;
        bulletVisible2[currentBullet2] = true;
        currentBullet2 = (currentBullet2 + 1)%numBullets2;
      }
      
      
      
      
    }
    
    
    
  } // MyKeyListener class end   
  
} // TankPvp class end



























