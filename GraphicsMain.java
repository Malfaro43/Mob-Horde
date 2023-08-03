import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import java.awt.Font;

import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Random;

public class GraphicsMain extends JPanel implements KeyListener
{
  BufferedImage background = readImage("island.jpg");
  boolean gameState = true;
  Knight hero = new Knight();
  ArrayList<Skeleton> skeletons = new ArrayList<Skeleton>();
  double ms = 6;
  int numMob = 5;
  int mobHealth = 10;
  int mobDead = 0;
  int level = 0;
  Font font;

  public GraphicsMain() {
    font = new Font("Arial", Font.BOLD, 20);
    for (int i = 0; i<numMob; i++) {
      skeletons.add(new Skeleton(mobHealth));
    }
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
        hero.moveX(hero.movingRight + hero.movingLeft);
        hero.moveY(hero.movingUp + hero.movingDown);
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
      for (Skeleton mob : skeletons) {
        if (mob.health <= 0 && mob.state != 5) {
          mob.deathAnimate();
          if (mob.state == 5) {
            hero.health++;
            mobDead++;
          }
        }

        else if (mob.state != 5  && hero.health > 0) {
          mob.checkIfFlip(hero.x, hero.y);
          mob.checkAttack(hero.x, hero.y);
          if (hero.state == 2) {
            mob.checkIfAttacked(hero.x, hero.y, hero.attackCurrent, hero.attackFrameCount, hero.flipped);
          }
          if (mob.state == 0) {
            mob.roam();
            mob.moveAnimate();
          }

          if (mob.state == 1) {
            if (!mob.flipped) {
              if (hero.x <= mob.xPos+90 && hero.x >= mob.xPos-50 && hero.y <= mob.yPos && hero.y+120 >= mob.yPos && (mob.attackCurrent == 4 || mob.attackCurrent == 8) && mob.attackFrameCount == 0) {
                hero.health--;
                hero.state = 3;
              }
            }
            else if (hero.x >= mob.xPos-150 && hero.x <= mob.xPos && hero.y <= mob.yPos && hero.y+120 >= mob.yPos && (mob.attackCurrent == 4 || mob.attackCurrent == 8) && mob.attackFrameCount == 0) {
              hero.health--;
              hero.state = 3;
            }
            mob.attackAnimate();
          }
          if (mob.state == 2 && hero.attackCurrent > 7) {
            mob.damagedAnimate();
          }
        }
      }
      if (mobDead == numMob) {
        levelUp();
      }
      repaint();
    }
  }

  public void levelUp() {
    Random rand = new Random();
    if (rand.nextInt(2) == 1) {
      mobHealth = mobHealth + 5;
    }
    else {
      numMob = numMob + 2;
    }
    mobDead = 0;
    skeletons.clear();
    for (int i = 0; i<numMob; i++) {
      skeletons.add(new Skeleton(mobHealth));
    }
    level++;
    hero.x = 520;
    hero.y = 20;
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
    for (Skeleton mob : skeletons) {
      mob.drawTo(g);
    }
    hero.drawTo(g);
    g.setFont(font);
    g.drawString("LEVEL: " + Integer.toString(level), 930, 25);
  }


  public void keyPressed(KeyEvent e)
  {
    int code = e.getKeyCode();
    
    if(code == KeyEvent.VK_W)
    {
      hero.movingUp = -ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
      hero.attackCurrent = 0;
      hero.attackFrameCount = 0;
    }
    if(code == KeyEvent.VK_S)
    {
      hero.movingDown = ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
      hero.attackCurrent = 0;
      hero.attackFrameCount = 0;
    }
    if(code == KeyEvent.VK_D)
    {
      hero.movingRight = ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
      hero.attackCurrent = 0;
      hero.attackFrameCount = 0;
      hero.flipped = false;
    }
    if(code == KeyEvent.VK_A)
    {
      hero.movingLeft = -ms;
      hero.state = 1;
      hero.idleFrameCount = 0;
      hero.idleCurrent = 0;
      hero.attackCurrent = 0;
      hero.attackFrameCount = 0;
      hero.flipped = true;
    }
    if(code == KeyEvent.VK_F) {
      hero.state = 2;
    }
    if(code == KeyEvent.VK_G && hero.potion > 0) {
      hero.health++;
      hero.potion--;
    }
  }

  public void keyReleased(KeyEvent e)
  {
    int code = e.getKeyCode();
    
    if (code == KeyEvent.VK_W)
    {
      hero.movingUp = 0;
    }
    if (code == KeyEvent.VK_S)
    {
      hero.movingDown = 0;
    }    
    if (code == KeyEvent.VK_A) {
      hero.movingLeft = 0;
    }
    if (code == KeyEvent.VK_D) {
      hero.movingRight = 0;
    }
    if (hero.movingDown == 0 && hero.movingUp == 0 && hero.movingRight == 0 && hero.movingLeft == 0 && hero.state != 2) {
      hero.moveFrameCount = 0;
      hero.moveCurrent = 0;
      hero.state = 0;  
    }
  }

  public void keyTyped(KeyEvent e)
  {
  }
}