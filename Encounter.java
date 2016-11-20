package edu.wa.tacoma.team1.tcss450.stickmanwalking.sally;

/**
 * Sally Budack
 * TCSS450
 * Fall 2016
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import edu.wa.tacoma.team1.tcss450.stickmanwalking.R;

/**
 * Encounter
 */
public class Encounter {

    //bitmap for the encounter
    //we have already pasted the bitmap in the drawable folder
    private Bitmap bitmap;

    //x and y coordinates
    private int x;
    private int y;

    //encounter speed
    private int speed = 1;

    //min and max coordinates to keep the encounter inside the screen
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    //creating a rect object
    private Rect detectCollision;

    public Encounter(Context context, int screenX, int screenY) {
        Random random = new Random();
        int rand = random.nextInt(5);
        random = new Random();
        int rand2 = random.nextInt(7);
        switch (rand + rand2) {
            case 1:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin1);
                break;
            case 2:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin2);
                break;
            case 3:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin3);
            case 4:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin4);
                break;
            case 5:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin5);
                break;
            case 6:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin6);
                break;
            case 7:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
                break;
            case 8:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin7);
                break;
            case 9:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin8);
            case 10:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin9);
                break;
            case 11:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin10);
                break;
            case 12:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin11);
                break;
            default:
                //getting bitmap from drawable resource
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
        }


        //initializing min and max coordinates
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        if (bitmap != null) {
            //generating a random coordinate to add encounter
            Random generator = new Random();
            speed = generator.nextInt(3) + 10;
            x = screenX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
            //initializing rect object
            detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void update(int playerSpeed) {
        //decreasing x coordinate so that encounter will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the encounter reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the encounter again to the right edge
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }
        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //adding a setter to x coordinate so that we can change it after collision
    public void setX(int x) {
        this.x = x;
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

}