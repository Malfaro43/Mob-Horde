/* This class represents a square we want to move around the screen.
   We will use this in conjunction with multiple examples, including
   BetterKeyboardExample and EvenBetterKeyboardExample.
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

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

    int roamCounter = 0;

    double xSpeed = 0;
    double ySpeed = 0;
    int health;
    int attackSpeed = 5;
    int state = 0;
    boolean flipped = false;

    int amount;
    int direction;

    int xPos;
    int yPos;

    BufferedImage spriteSheet = readImage("Skeleton\\skeleton_sheet.png");
    Image[] idleImages = new Image[12];
    Image[] attackImages = new Image[13];
    Image[] damagedImages = new Image[3];
    Image[] deathImages = new Image[13];
    Image[] idleImagesFlipped = new Image[12];
    Image[] attackImagesFlipped = new Image[13];
    Image[] damagedImagesFlipped = new Image[3];
    Image[] deathImagesFlipped = new Image[13];
  
  public Skeleton(int amount, int speed) {
    health = amount;
    attackSpeed = speed;
    spawnGen();
    for (int i = 0; i < 12; i++) {
      idleImages[i] = spriteSheet.getSubimage(0+i*64, 128, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
      idleImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*64, 128, 64, 64)).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 13; i++) {
      attackImages[i] = spriteSheet.getSubimage(0+i*64, 0, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
      attackImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*64, 0, 64, 64)).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 3; i++) {
      damagedImages[i] = spriteSheet.getSubimage(0+i*64, 256, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
      damagedImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*64, 256, 64, 64)).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 13; i++) {
      deathImages[i] = spriteSheet.getSubimage(0+i*64, 64, 64, 64).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
      deathImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*64, 64, 64, 64)).getScaledInstance(170, 170, Image.SCALE_DEFAULT);
    }
    int[] possibilites = new int[] {3, -3};
    Random rand = new Random();
    amount = rand.nextInt(possibilites.length);
    direction = rand.nextInt(2);  
    // 160 240
    //each x = 64
  }
  
  public void drawTo(Graphics g)
  {
    if (! flipped) {
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
    else {
      if (health <= 0 && state != 5) {
        g.drawImage(deathImagesFlipped[deathCurrent], xPos, yPos, null);
      }
      else {
        if (state == 0) {
            g.drawImage(idleImagesFlipped[moveCurrent], xPos, yPos, null);
        }
        if (state == 1) {
            g.drawImage(attackImagesFlipped[attackCurrent], xPos, yPos, null);
        }
        if (state == 2) {
            g.drawImage(damagedImagesFlipped[damagedCurrent], xPos, yPos, null);
        }
      }      
    }
  }

  public Image flipImage(BufferedImage i) {
    BufferedImage newImg = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int pixel = 0; pixel < i.getHeight(); pixel++) {
      for (int pixel2 = 0; pixel2 < i.getWidth()/2; pixel2++) {
        int old_color = i.getRGB(pixel2, pixel);
        newImg.setRGB(pixel2, pixel, i.getRGB(i.getWidth()-pixel2-1, pixel));
        newImg.setRGB(i.getWidth()-pixel2-1, pixel, old_color);
      }
    }
    return newImg;
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
    if (attackFrameCount == attackSpeed) {
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
    if (!flipped) {
      if (x <= xPos+140 && x >= xPos && y <= yPos+30 && y+120 >= yPos) {
        state = 1;
      }
    }
    else {
      if (x >= xPos-140 && x <= xPos && y <= yPos+30 && y+120 >= yPos) {
        state = 1;
      }
    }
  }

  public void checkIfAttacked(int x, int y, int count, int frame, boolean flipped) {
    if (!flipped) {
      if (xPos <= x+150 && xPos >= x && yPos <= y+110 && yPos >= y-10 && count == 8 && frame == 0) {
        state = 2;
        health = health - 5;
        System.out.println(health);
      }
    }
    else {
      if (xPos >= x-150 && xPos <= x && yPos <= y+110 && yPos >= y-10 && count == 8 && frame == 0) {
        state = 2;
        health = health - 5;
        System.out.println(health);
      }      
    }
  }

  public void checkIfFlip(int x, int y) {
    if (x >= xPos-220 && x+40 <= xPos && yPos <= y+180 && yPos >= y+30) {
      flipped = true;
    }
    if (x <= xPos+220 && x >= xPos && yPos <= y+180 && yPos >= y+30) {
      flipped = false;
    }
  }

  public void spawnGen() {
    int[][] possibilites = new int[][] {{1000, 300}, {970, 250}, {700, 500}, {500, 500}, {30, 500}, {700, 350}, {955, 180}, {500, 500}, {420, 510}};
    Random rand = new Random();
    int[] coords = possibilites[rand.nextInt(possibilites.length)];
    xPos = coords[0];
    yPos = coords[1];
  }

  public void roam() {
    if (roamCounter == 8) {
      int[] possibilites = new int[] {1, -1};
      Random rand = new Random();
      amount = possibilites[rand.nextInt(possibilites.length)];
      direction = rand.nextInt(2);
      roamCounter = 0;   
    }
    if (direction == 1) {
      moveX();
    }
    else {
      moveY();
    }
    roamCounter++;
  }

  public void moveY()
  {
    if ((xPos>-200 && xPos<230 && yPos+amount>250 && yPos+amount<450) || (xPos>180 && xPos<240 && yPos+amount<260 && yPos+amount>90) || (xPos>240 && xPos<430 && yPos+amount<260 && yPos+amount>80) || (xPos>440 && xPos<480 && yPos+amount<300 && yPos+amount>80) || (xPos>570 && xPos<610 && yPos+amount<570 && yPos+amount>80) || (xPos>610 && xPos<860 && yPos+amount<260 && yPos+amount>80) || (xPos>800 && yPos+amount<100) || (!(yPos+amount>-30 && yPos+amount<640))){
      amount = amount * -1;
    }
    else {yPos+=amount;}
  }

  public void moveX() {
    if ((xPos+amount>-200 && xPos+amount<=230 && yPos>250 && yPos<450) || (xPos+amount>180 && xPos+amount<260 && yPos<260 && yPos>90) || (xPos+amount>240 && xPos+amount<430 && yPos<260 && yPos>80) || (xPos+amount>440 && xPos+amount<480 && yPos<300 && yPos>80) || (xPos+amount>570 && xPos+amount<610 && yPos<300 && yPos>80) || (xPos+amount>610 && xPos+amount<860 && yPos<260 && yPos>80) || (xPos+amount>800 && yPos<100) || (!(xPos+amount>-75 && xPos+amount<985))) {
      amount = amount * -1;
    }
    else {xPos+=amount;}
  }

}

