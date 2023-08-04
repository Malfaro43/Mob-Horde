/* This class represents a square we want to move around the screen.
   We will use this in conjunction with multiple examples, including
   BetterKeyboardExample and EvenBetterKeyboardExample.
 */
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;


public class OldKnight {
    int idleFrameCount = 0;
    int swordFrameCount = 0;
    int slingshotFrameCount = 0;
    int moveFrameCount = 0;
    double xSpeed = 0;
    double ySpeed = 0;
    BufferedImage[] idle = {readImage("01 - Hobbit\\pngs\\Hobbit - Idle1.png"), readImage("01 - Hobbit\\pngs\\Hobbit - Idle2.png"), readImage("01 - Hobbit\\pngs\\Hobbit - Idle3.png"), readImage("01 - Hobbit\\pngs\\Hobbit - Idle4.png")};
    BufferedImage[] move = {readImage("01 - Hobbit\\pngs\\Hobbit - run1.png"), readImage("01 - Hobbit\\pngs\\Hobbit - run2.png"), readImage("01 - Hobbit\\pngs\\Hobbit - run3.png"), readImage("01 - Hobbit\\pngs\\Hobbit - run4.png"), readImage("01 - Hobbit\\pngs\\Hobbit - run5.png"), readImage("01 - Hobbit\\pngs\\Hobbit - run6.png"), readImage("01 - Hobbit\\pngs\\Hobbit - run7.png"), readImage("01 - Hobbit\\pngs\\Hobbit - run8.png")};
    BufferedImage[] slingshot = {readImage("01 - Hobbit\\pngs\\Hobbit - attack1.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack2.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack3.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack4.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack5.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack6.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack7.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack8.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack9.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack10.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack11.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack12.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack13.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack14.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack15.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack16.png"), readImage("01 - Hobbit\\pngs\\Hobbit - attack17.png")};
    BufferedImage[] sword = {readImage("slashes\\spr_slice_o_0.png"), readImage("slashes\\spr_slice_o_1.png"), readImage("slashes\\spr_slice_o_2.png"), readImage("slashes\\spr_slice_o_3.png"), readImage("slashes\\spr_slice_o_4.png"), readImage("slashes\\spr_slice_o_5.png")};
    int idleCurrent = 0;
    int moveCurrent = 0;
    int slingshotCurrent = 0;
    int swordCurrent = 0;
    int x = 400;
    int y = 500;
    int maxSpeed = 5;
    int state = 0;
    boolean still = true;
    boolean moving = false;
    boolean attack = false;
  
  public OldKnight() {

  }
  
  public void drawTo(Graphics g)
  {
    if (state == 0) {
      g.drawImage(idle[idleCurrent].getScaledInstance(200, 200, Image.SCALE_DEFAULT), x, y, null);
    }
    else if (state == 1) {
      g.drawImage(move[moveCurrent].getScaledInstance(200, 200, Image.SCALE_DEFAULT), x, y, null);
    }
    else if (state == 2) {
      g.drawImage(slingshot[slingshotCurrent].getScaledInstance(200, 200, Image.SCALE_DEFAULT), x, y, null);
    }
    else if (state == 3) {
      g.drawImage(move[0].getScaledInstance(200, 200, Image.SCALE_DEFAULT), x, y, null);
      g.drawImage(sword[swordCurrent], x+120, y+20, null);
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
    if (idleFrameCount == 9) {
        idleCurrent++;
        if (idleCurrent>3) {
            idleCurrent = 0;
        }
        idleFrameCount = 0;
    }
  }

  public void moveAnimate() {
    moveFrameCount++;
    if (moveFrameCount == 5) {
        moveCurrent++;
        if (moveCurrent>6) {
            moveCurrent = 0;
        }
        moveFrameCount = 0;
    }
  }
  
  public void attackSwordAnimate() {
    swordFrameCount++;
    if (swordFrameCount == 5) {
        swordCurrent++;
        if (swordCurrent>3) {
            swordCurrent = 0;
            state = 0;
        }
        swordFrameCount = 0;
    }
  }

  public void attackThrowAnimate() {
    slingshotFrameCount++;
    if (slingshotFrameCount == 5) {
        slingshotCurrent++;
        if (slingshotCurrent>16) {
            slingshotCurrent = 0;
            state = 0;
        }
        slingshotFrameCount = 0;
    }
  }
  
  public void move()
  {
    moveY(ySpeed);
    moveX(xSpeed);
  }

  public void moveY(double amount)
  {
    if ((x>-200 && x<230 && y+amount>250 && y+amount<450) || (x>180 && x<240 && y+amount<260 && y+amount>90) || (x>240 && x<430 && y+amount<260 && y+amount>80) || (x>440 && x<480 && y+amount<300 && y+amount>80) || (x>570 && x<610 && y+amount<570 && y+amount>80) || (x>610 && x<860 && y+amount<260 && y+amount>80) || (x>800 && y+amount<100) || (!(y+amount>-30 && y+amount<640))){
      
    }
    else {y+=amount;}
  }

  public void moveX(double amount) {
    if ((x+amount>-200 && x+amount<=230 && y>250 && y<450) || (x+amount>180 && x+amount<260 && y<260 && y>90) || (x+amount>240 && x+amount<430 && y<260 && y>80) || (x+amount>440 && x+amount<480 && y<300 && y>80) || (x+amount>570 && x+amount<610 && y<300 && y>80) || (x+amount>610 && x+amount<860 && y<260 && y>80) || (x+amount>800 && y<100) || (!(x+amount>-75 && x+amount<985))) {

    }
    else {x+=amount;}
  }
}