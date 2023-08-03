/* This class represents a square we want to move around the screen.
   We will use this in conjunction with multiple examples, including
   BetterKeyboardExample and EvenBetterKeyboardExample.
 */
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;


public class Skeleton {
    int attackFrameCount = 0;
    int attackCurrent = 0;

    int damagedFrameCount = 0;
    int damagedCurrent = 0;

    int moveFrameCount = 0;
    int moveCurrent = 0;

    int deathFrameCount = 0;
    int deathCurrent = 0;

    double xSpeed = 0;
    double ySpeed = 0;
    int health = 15;
    int state = 0;

    int xPos = 500;
    int yPos = 500;

    BufferedImage spriteSheet = readImage("Skeleton\\skeleton_sheet.png");
    Image[] idleImages = new Image[12];
    Image[] attackImages = new Image[13];
    Image[] damagedImages = new Image[3];
    Image[] deathImages = new Image[13];
  
  public Skeleton() {
    for (int i = 0; i < 12; i++) {
        idleImages[i] = spriteSheet.getSubimage(0+i*64, 128, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 13; i++) {
        attackImages[i] = spriteSheet.getSubimage(0+i*64, 0, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 3; i++) {
        damagedImages[i] = spriteSheet.getSubimage(0+i*64, 256, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 13; i++) {
        deathImages[i] = spriteSheet.getSubimage(0+i*64, 64, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    // 160 240
    //each x = 64
  }
  
  public void drawTo(Graphics g)
  {
    if (health <= 0 && state != 5) {
      g.drawImage(deathImages[deathCurrent], xPos, yPos, null);
    }
    else {
      if (state == 0) {
          g.drawImage(idleImages[moveCurrent], xPos, yPos, null);
      }
      if (state == 1) {
          g.drawImage(attackImages[attackCurrent], xPos, yPos, null);
      }
      if (state == 2) {
          g.drawImage(damagedImages[damagedCurrent], xPos, yPos, null);
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

  public void moveAnimate() {
    moveFrameCount++;
    if (moveFrameCount == 5) {
        moveCurrent++;
        if (moveCurrent>11) {
            moveCurrent = 0;
        }
        moveFrameCount = 0;
    }
  }

  public void attackAnimate() {
    attackFrameCount++;
    if (attackFrameCount == 5) {
        attackCurrent++;
        if (attackCurrent>12) {
            attackCurrent = 0;
            state = 0;
        }
        attackFrameCount = 0;
    }
  }

  public void damagedAnimate() {
    damagedFrameCount++;
    if (damagedFrameCount == 2) {
        damagedCurrent++;
        if (damagedCurrent>2) {
            damagedCurrent = 0;
            state = 0;
        }
        damagedFrameCount = 0;
    }
  }

  public void deathAnimate() {
    deathFrameCount++;
    if (deathFrameCount == 7) {
        deathCurrent++;
        if (deathCurrent>12) {
            deathCurrent = 0;
            state = 5;
        }
        deathFrameCount = 0;
    }
  }
  public void checkAttack(int x, int y) {
    if (x <= xPos+90 && x >= xPos+20 && y <= yPos && y+120 >= yPos) {
      state = 1;
    }
  }

  public void checkIfAttacked(int x, int y, int count, int frame) {
    if (xPos <= x+150 && xPos >= x && yPos <= y+210 && yPos >= y+30 && count == 8 && frame == 0) {
      state = 2;
      health = health - 5;
      System.out.println(health);
    } 
  }
//   public void move()
//   {
//     moveY(ySpeed);
//     moveX(xSpeed);
//   }

//   public void moveY(double amount)
//   {
//     if ((x>-200 && x<230 && y+amount>250 && y+amount<450) || (x>180 && x<240 && y+amount<260 && y+amount>90) || (x>240 && x<430 && y+amount<260 && y+amount>80) || (x>440 && x<480 && y+amount<300 && y+amount>80) || (x>570 && x<610 && y+amount<570 && y+amount>80) || (x>610 && x<860 && y+amount<260 && y+amount>80) || (x>800 && y+amount<100) || (!(y+amount>-30 && y+amount<640))){
      
//     }
//     else {y+=amount;}
//   }

//   public void moveX(double amount) {
//     if ((x+amount>-200 && x+amount<=230 && y>250 && y<450) || (x+amount>180 && x+amount<260 && y<260 && y>90) || (x+amount>240 && x+amount<430 && y<260 && y>80) || (x+amount>440 && x+amount<480 && y<300 && y>80) || (x+amount>570 && x+amount<610 && y<300 && y>80) || (x+amount>610 && x+amount<860 && y<260 && y>80) || (x+amount>800 && y<100) || (!(x+amount>-75 && x+amount<985))) {

//     }
//     else {x+=amount;}
//   }
}