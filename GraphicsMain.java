import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GraphicsMain extends JPanel implements KeyListener
{
  BufferedImage background = readImage("island.jpg");
  boolean gameState = true;
  Knight hero = new Knight();
  Skeleton mob = new Skeleton();
  double ms = 6;

  public GraphicsMain() {
    addKeyListener(this);
    setFocusable(true);
  }


  public static void main(String[] args)
  { 
    // Initialize the window
    JFrame frame = new JFrame();

    // Set the width and height of the window in pixels
    frame.setSize(1200,800);
    
    //frame

    // Make the program end when the window is closed
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GraphicsMain panel = new GraphicsMain();
    
    frame.add(panel, BorderLayout.CENTER);
    // Show the window when we are done with all of our initialization
    frame.setVisible(true);
    
    panel.mainLoop();
  }

  private static BufferedImage readImage(String infile)
  {
    try
    {
      BufferedImage ret = ImageIO.read(new File(infile));
      return ret;
    }
    catch(Exception e){System.out.println(e.getMessage()); return null;}
  }

  public void mainLoop() {
    while (gameState)  {
      try
      {
        Thread.sleep(20);
      }
      catch(Exception e){e.printStackTrace();}
      if (hero.health <= 0 && hero.state != 5) {
        hero.deathAnimate();
      }
      
      else if (hero.state != 5) {
        hero.moveX(hero.movingX);
        hero.moveY(hero.movingY);
        if (hero.state == 0) {
          hero.idleAnimate();
        }
        if (hero.state == 1) {
          hero.moveAnimate();
        }

        if (hero.state == 2) {
          hero.attackAnimate();
        }

        if (hero.state == 3) {
          hero.hurtAnimate();
        }

        if (hero.health <= 0 && hero.state != 5) {
          hero.deathAnimate();
        }
      }

      if (mob.health <= 0 && mob.state != 5) {
        mob.deathAnimate();
      }

      else if (mob.state != 5  && hero.health > 0) {
        mob.checkAttack(hero.x, hero.y);
        if (hero.state == 2) {
          mob.checkIfAttacked(hero.x, hero.y, hero.attackCurrent, hero.attackFrameCount);
        }

        if (mob.state == 0) {
          mob.moveAnimate();
        }

        if (mob.state == 1) {
          if (hero.x <= mob.xPos+90 && hero.x >= mob.xPos+20 && hero.y <= mob.yPos && hero.y+120 >= mob.yPos && (mob.attackCurrent == 4 || mob.attackCurrent == 8) && mob.attackFrameCount == 0) {
            hero.health--;
            hero.state = 3;
          }
          mob.attackAnimate();
        }
        if (mob.state == 2 && hero.attackCurrent > 7) {
          mob.damagedAnimate();
        }
      }

      repaint();
    }
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
    mob.drawTo(g);
    hero.drawTo(g);
  }


  public void keyPressed(KeyEvent e)
  {
    int code = e.getKeyCode();
    
    if(code == KeyEvent.VK_W)
    {
      hero.movingY = -ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
    }
    if(code == KeyEvent.VK_S)
    {
      hero.movingY = ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
    }
    if(code == KeyEvent.VK_D)
    {
      hero.movingX = ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
    }
    if(code == KeyEvent.VK_A)
    {
      hero.movingX = -ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
    }
    if(code == KeyEvent.VK_F) {
      hero.state = 2;
    }
  }

  public void keyReleased(KeyEvent e)
  {
    int code = e.getKeyCode();
    
    if (code == KeyEvent.VK_W || code == KeyEvent.VK_S)
    {
      hero.movingY = 0;
    }
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
      hero.movingX = 0;
    }
    if (hero.movingX == 0 && hero.movingY == 0 && hero.state != 2) {
      hero.moveFrameCount = 0;
      hero.moveCurrent = 0;
      hero.state = 0;  
    }
  }

  public void keyTyped(KeyEvent e)
  {
  }
}