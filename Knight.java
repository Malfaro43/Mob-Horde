/* This class represents a square we want to move around the screen.
   We will use this in conjunction with multiple examples, including
   BetterKeyboardExample and EvenBetterKeyboardExample.
 */
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;


public class Knight {
    int idleFrameCount = 0;
    int attackFrameCount = 0;
    int moveFrameCount = 0;
    int hurtFrameCount = 0;
    int deathFrameCount = 0;

    double xSpeed = 0;
    double ySpeed = 0;
    int health = 10;

    BufferedImage spriteSheet = readImage("Nightborne\\NightBorne.png");
    Image heart = readImage("heart.png").getScaledInstance(27, 24, Image.SCALE_DEFAULT);
    Image[] idleImages = new Image[9];
    Image[] moveImages = new Image[6];
    Image[] attackImages = new Image[10];
    Image[] hurtImages = new Image[5];
    Image[] deathImages = new Image[23];

    int attackCurrent = 0;
    int idleCurrent = 0;
    int moveCurrent = 0;
    int hurtCurrent = 0;
    int deathCurrent = 0;

    int x = 400;
    int y = 500;

    int state = 0;

    double movingX = 0;
    double movingY = 0;
  
  public Knight() {
    for (int i = 0; i < 9; i++) {
        idleImages[i] = spriteSheet.getSubimage(0+i*80, 0, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 6; i++) {
        moveImages[i] = spriteSheet.getSubimage(0+i*80, 80, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 10; i++) {
        attackImages[i] = spriteSheet.getSubimage(0+i*80, 160, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 5; i++) {
        hurtImages[i] = spriteSheet.getSubimage(0+i*80, 240, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 23; i++) {
        deathImages[i] = spriteSheet.getSubimage(0+i*80, 320, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
  }
  
  public void drawTo(Graphics g)
  {
    if (health <= 0 && state != 5) {
      g.drawImage(deathImages[deathCurrent], x, y, null);
    }
    else {
      for (int i = 0; i<health; i++) {
        g.drawImage(heart, 10+i*27, 10, null);
      }
      if (state == 0) {
        g.drawImage(idleImages[idleCurrent], x, y, null);
      }
      else if (state == 1) {
        g.drawImage(moveImages[moveCurrent], x, y, null);
      }
      else if (state == 2) {
        g.drawImage(attackImages[attackCurrent], x, y, null);
      }
      else if (state == 3) {
        g.drawImage(hurtImages[hurtCurrent], x, y, null);
      }
    }
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

  public void idleAnimate() {
    idleFrameCount++;
    if (idleFrameCount == 2) {
        idleCurrent++;
        if (idleCurrent>8) {
            idleCurrent = 0;
        }
        idleFrameCount = 0;
    }
  }

  public void moveAnimate() {
    moveFrameCount++;
    if (moveFrameCount == 3) {
        moveCurrent++;
        if (moveCurrent>5) {
            moveCurrent = 0;
        }
        moveFrameCount = 0;
    }
  }
  
  public void attackAnimate() {
    movingX = 0;
    movingY = 0;
    attackFrameCount++;
    if (attackFrameCount == 4) {
        attackCurrent++;
        if (attackCurrent>9) {
            attackCurrent = 0;
            state = 0;
        }
        attackFrameCount = 0;
    }
  }

  public void hurtAnimate() {
    hurtFrameCount++;
    if (hurtFrameCount == 3) {
        hurtCurrent++;
        if (hurtCurrent>4) {
            hurtCurrent = 0;
            state = 0;
        }
        hurtFrameCount = 0;
    }
  }
  
  public void deathAnimate() {
    deathFrameCount++;
    if (deathFrameCount == 5) {
        deathCurrent++;
        if (deathCurrent>22) {
            deathCurrent = 0;
            state = 5;
        }
        deathFrameCount = 0;
    }
  }
  
  public void move()
  {
    moveY(ySpeed);
    moveX(xSpeed);
  }

  public void moveY(double amount)
  {
    if ((x>-200 && x<230 && y+amount>205 && y+amount<350) || (x>180 && x<240 && y+amount<260 && y+amount>90) || (x>240 && x<430 && y+amount<260 && y+amount>45) || (x>440 && x<480 && y+amount<300 && y+amount>80) || (x>570 && x<610 && y+amount<570 && y+amount>80) || (x>610 && x<860 && y+amount<260 && y+amount>45) || (x>800 && y+amount<100) || (!(y+amount>-50 && y+amount<600))){
      
    }
    else {y+=amount;}
  }

  public void moveX(double amount) {
    if ((x+amount>-200 && x+amount<=230 && y>205 && y<350) || (x+amount>180 && x+amount<260 && y<260 && y>90) || (x+amount>240 && x+amount<430 && y<260 && y>45) || (x+amount>440 && x+amount<480 && y<300 && y>80) || (x+amount>570 && x+amount<610 && y<300 && y>80) || (x+amount>610 && x+amount<860 && y<260 && y>45) || (x+amount>800 && y<100) || (!(x+amount>-75 && x+amount<935))) {

    }
    else {x+=amount;}
  }
}