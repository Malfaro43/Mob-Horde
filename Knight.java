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

    int health = 10;
    int potion = 3;

    BufferedImage spriteSheet = readImage("Nightborne\\NightBorne.png");
    Image heart = readImage("heart.png").getScaledInstance(27, 24, Image.SCALE_DEFAULT);
    Image potionImage = readImage("potions.png").getSubimage(0, 64, 16, 16).getScaledInstance(24, 24, Image.SCALE_DEFAULT);
    
    Image[] idleImages = new Image[9];
    Image[] moveImages = new Image[6];
    Image[] attackImages = new Image[10];
    Image[] hurtImages = new Image[5];
    Image[] deathImages = new Image[23];
    Image[] idleImagesFlipped = new Image[9];
    Image[] moveImagesFlipped = new Image[6];
    Image[] attackImagesFlipped = new Image[10];
    Image[] hurtImagesFlipped = new Image[5];
    Image[] deathImagesFlipped = new Image[23];

    int attackCurrent = 0;
    int idleCurrent = 0;
    int moveCurrent = 0;
    int hurtCurrent = 0;
    int deathCurrent = 0;

    int x = 520;
    int y = 20;

    int state = 0;

    double movingUp = 0;
    double movingDown = 0;
    double movingRight = 0;
    double movingLeft = 0;

    boolean flipped = false;
  
  public Knight() {
    for (int i = 0; i < 9; i++) {
      idleImages[i] = spriteSheet.getSubimage(0+i*80, 0, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
      idleImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*80, 0, 80, 80)).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 6; i++) {
      moveImages[i] = spriteSheet.getSubimage(0+i*80, 80, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
      moveImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*80, 80, 80, 80)).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 10; i++) {
      attackImages[i] = spriteSheet.getSubimage(0+i*80, 160, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
      attackImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*80, 160, 80, 80)).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 5; i++) {
      hurtImages[i] = spriteSheet.getSubimage(0+i*80, 240, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
      hurtImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*80, 240, 80, 80)).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
    }
    for (int i = 0; i < 23; i++) {
      deathImages[i] = spriteSheet.getSubimage(0+i*80, 320, 80, 80).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
      deathImagesFlipped[i] = flipImage(spriteSheet.getSubimage(0+i*80, 320, 80, 80)).getScaledInstance(210, 210, Image.SCALE_DEFAULT);
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


  public void drawTo(Graphics g)
  {
    for (int i = 0; i<health; i++) {
      g.drawImage(heart, 10+i*27, 10, null);
    }
    for (int i = 0; i<potion; i++) {
      g.drawImage(potionImage, 10+i*27, 40, null);
    }
    if (! flipped) {
      if (health <= 0 && state != 5) {
        g.drawImage(deathImages[deathCurrent], x, y, null);
      }
      else {
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
    else {
      if (health <= 0 && state != 5) {
        g.drawImage(deathImages[deathCurrent], x, y, null);
      }
      else {
        if (state == 0) {
          g.drawImage(idleImagesFlipped[idleCurrent], x, y, null);
        }
        else if (state == 1) {
          g.drawImage(moveImagesFlipped[moveCurrent], x, y, null);
        }
        else if (state == 2) {
          g.drawImage(attackImagesFlipped[attackCurrent], x, y, null);
        }
        else if (state == 3) {
          g.drawImage(hurtImagesFlipped[hurtCurrent], x, y, null);
        }
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
    movingUp = 0;
    movingDown = 0;
    movingRight = 0;
    movingLeft = 0;

    attackFrameCount++;
    if (attackFrameCount == 2) {
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
    if (hurtFrameCount == 2) {
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

  public void moveY(double amount)
  {
    if ((x>-200 && x<230 && y+amount>205 && y+amount<350) || (x>180 && x<240 && y+amount<260 && y+amount>90) || (x>240 && x<440 && y+amount<260 && y+amount>45) || (x>430 && x<480 && y+amount<280 && y+amount>45) || (x>570 && x<620 && y+amount<280 && y+amount>45) || (x>610 && x<860 && y+amount<260 && y+amount>-30) || (x>800 && y+amount<100) || (!(y+amount>-100 && y+amount<600))){
    
    }
    else {y+=amount;}
  }

  public void moveX(double amount) {
    if ((x+amount>-200 && x+amount<=230 && y>205 && y<350) || (x+amount>180 && x+amount<260 && y<260 && y>90) || (x+amount>240 && x+amount<440 && y<260 && y>45) || (x+amount>430 && x+amount<480 && y<280 && y>45) || (x+amount>570 && x+amount<620 && y<280 && y>45) || (x+amount>610 && x+amount<860 && y<260 && y>-30) || (x+amount>800 && y<100) || (!(x+amount>-75 && x+amount<960))) {

    }
    else {x+=amount;}
  }
}