package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage heart_full , heart_half , heart_blank;

    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;    //0: the first screen , 1 the second screen



    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Cambria" , Font.PLAIN , 40);

        //CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

        //CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }
    }

    public void drawPlayerLife(){

        //gp.player.life = 2;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //DRAW BLANK HEART
        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank , x,  y , null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.life){
            g2.drawImage(heart_half , x , y , null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full , x,  y , null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawTitleScreen(){

            if(titleScreenState == 0){
                g2.setColor(Color.blue);
                g2.fillRect(0 , 0 , gp.screenWidth , gp.screenHeight);
                //TITLE NAME
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,70F));
                String text = "Blue Boy Adventure";
                int x = getXforCenteredText(text);
                int y = gp.tileSize*3;

                //SHADOW
                g2.setColor(Color.black);
                g2.drawString(text , x + 5 , y + 5);
                //MAIN COLOR
                g2.setColor(Color.white);
                g2.drawString(text , x , y);

                //BLUE BOY IMAGE
                x = gp.screenWidth/2 - (gp.tileSize*2)/2;
                y += gp.tileSize*2;
                g2.drawImage(gp.player.down1 ,x , y , gp.tileSize*2 , gp.tileSize*2 , null);

                //MENU
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

                text = "NEW GAME";
                x = getXforCenteredText(text);
                y += gp.tileSize*4;
                g2.drawString(text , x , y);
                if(commandNum == 0){
                    g2.drawString(">" ,x-gp.tileSize , y);
                }

                text = "LOAD GAME";
                x = getXforCenteredText(text);
                y += gp.tileSize;
                g2.drawString(text , x , y);
                if(commandNum == 1){
                    g2.drawString(">" ,x-gp.tileSize , y);
                }

                text = "QUIT";
                x = getXforCenteredText(text);
                y += gp.tileSize;
                g2.drawString(text ,x , y);
                if(commandNum == 2){
                    g2.drawString(">" ,x-gp.tileSize , y);
                }
            }

            else if(titleScreenState == 1){

                g2.setColor(Color.white);
                g2.setFont(g2.getFont().deriveFont(42F));

                String text = "Select your class!";
                int x = getXforCenteredText(text);
                int y = gp.tileSize*3;
                g2.drawString(text , x,  y);

                text = "Fighter";
                x = getXforCenteredText(text);
                y += gp.tileSize*3;
                g2.drawString(text ,x , y);
                if(commandNum == 0){
                    g2.drawString(">" , x-gp.tileSize , y);
                }

                text = "Thief";
                x = getXforCenteredText(text);
                y += gp.tileSize;
                g2.drawString(text ,x , y);
                if(commandNum == 0){
                    g2.drawString(">" , x-gp.tileSize , y);
                }

                text = "Sorcerer";
                x = getXforCenteredText(text);
                y += gp.tileSize;
                g2.drawString(text ,x , y);
                if(commandNum == 0){
                    g2.drawString(">" , x-gp.tileSize , y);
                }

                text = "Back";
                x = getXforCenteredText(text);
                y += gp.tileSize*2;
                g2.drawString(text ,x , y);
                if(commandNum == 0){
                    g2.drawString(">" , x-gp.tileSize , y);
                }
            }
        }

    public void drawCharacterScreen(){

        //CREATE A FRAME
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeigth = gp.tileSize*10;
        drawSubWindow(frameX , frameY , frameWidth , frameHeigth);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(25F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        //NAMES
        g2.drawString("Level" , textX , textY);
        textY += lineHeight;
        g2.drawString("Life" , textX , textY);
        textY += lineHeight;
        g2.drawString("Strength" , textX , textY);
        textY += lineHeight;
        g2.drawString("Attack" , textX , textY);
        textY += lineHeight;
        g2.drawString("Defense" , textX , textY);
        textY += lineHeight;
        g2.drawString("Dexerity" , textX , textY);
        textY += lineHeight;
        g2.drawString("Exp" , textX , textY);
        textY += lineHeight;
        g2.drawString("Next level" , textX , textY);
        textY += lineHeight;
        g2.drawString("Coin" , textX , textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon" , textX , textY);
        textY += lineHeight + 20;
        g2.drawString("Shield" , textX , textY);

        //VALUES
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/"+gp.player.maxLife);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexerity);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value , tailX);
        g2.drawString(value , textX , textY);
        textY += lineHeight;


        //for the weapon and the shield
        g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize , textY-15 , null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize , textY-15 , null);


    }

    public void drawPauseScreen(){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text , x, y);
    }

    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*5;
        drawSubWindow(x, y , width , height);

        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x,  int y , int width , int height){

        Color c = new Color(0,0,0);
        g2.setColor(Color.black);
        g2.fillRoundRect(x, y , width , height , 35 , 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5 , y+5 , width-10 , height- 10 , 25 , 25);
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text , g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXforAlignToRightText(String text , int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text , g2).getWidth();
        int x = tailX - length;
        return x;
    }
}



