package edu.wa.tacoma.team1.tcss450.stickmanwalking.sally;

/**
 * Sally Budack
 * TCSS450
 * Fall 2016
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import edu.wa.tacoma.team1.tcss450.stickmanwalking.R;


/**
 * GameView class. The game panel where the game is played. This class implements the Runnable interface. It has a volatile boolean type variable running that will track whether the game is running or not. It also has the gameThread which the main game loop.
 * Inside the loop, the following methods are called.
 * update() -> update the coordinate of our characters.
 * draw() ->  draw the characters to the canvas.
 * control() ->  control the frames per seconds drawn, call the delay method of Thread, making our frame rate to aroud 60fps.
 * Additional methods:
 * pause() ->  stoppthe gameThread.
 * resume() -> resume the game, starting the gameThread.
 */
public class GameView extends SurfaceView implements Runnable {
    //Stickman stickman;
    volatile boolean playing;
    private Thread gameThread = null;
    private Player playerWalk[];

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    int a;
    //Adding encounters object array
    private Encounter[] encounters;

    //Adding 3 encounters you may increase the size
    private int encounterCount = 5;
    //Adding an flecks list
    private ArrayList<Flecks> flecks = new
            ArrayList<Flecks>();
    //defining a boom object to display blast
    private Boom boom;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        playerWalk = new Player[6];
        Bitmap bitmap[] = new Bitmap[6];
        bitmap[0] = (BitmapFactory.decodeResource(context.getResources(), R
                .drawable.walk1));
        bitmap[1] = (BitmapFactory.decodeResource(context.getResources(), R
                .drawable.walk2));
        bitmap[2] = (BitmapFactory.decodeResource(context.getResources(), R
                .drawable.walk3));
        bitmap[3] = (BitmapFactory.decodeResource(context.getResources(), R
                .drawable.walk4));
        bitmap[4] = (BitmapFactory.decodeResource(context.getResources(), R
                .drawable.walk5));
        bitmap[5] = (BitmapFactory.decodeResource(context.getResources(), R
                .drawable.walk6));
        for (int i = 0; i < playerWalk.length; i++) {
            playerWalk[i] = new Player(context, screenX, screenY, bitmap[i]);
        }


//        stickman = new Stickman();
        surfaceHolder = getHolder();
        paint = new Paint();

        //adding 200 flecks
        int fleckNums = 200;
        for (int i = 0; i < fleckNums; i++) {
            Flecks s = new Flecks(screenX, screenY);
            flecks.add(s);
        }


        //initializing encounter object array
        encounters = new Encounter[encounterCount];
        for (int i = 0; i < encounterCount; i++) {
            encounters[i] = new Encounter(context, screenX, screenY);
        }
        //initializing boom object
        boom = new Boom(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        for (int i = 0; i < playerWalk.length; i++) {
            a = i;
            playerWalk[i].update();


            //setting boom outside the screen
            boom.setX(-250);
            boom.setY(-250);

            for (Flecks s : flecks) {
                s.update(playerWalk[a].getSpeed());
            }
        }
        for (int i = 0; i < encounterCount; i++) {
            encounters[i].update(playerWalk[a].getSpeed());
            //if collision occurrs with player
            if (Rect.intersects(playerWalk[a].getDetectCollision(), encounters[i]
                    .getDetectCollision())) {
//                        reward.setX(encounters[i].getX());
//                        reward.setY(encounters[i].getY());
//                        fireworks.setX(encounters[i].getX());
//                        fireworks.setY(encounters[i].getY());
                boom.setX(encounters[i].getX());
                boom.setY(encounters[i].getY());
                //moving encounter outside the left edge
                encounters[i].setX(-200);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            int R = (int) (Math.random() * 256);
            int G = (int) (Math.random() * 256);
            int B = (int) (Math.random() * 256);
            canvas.drawColor(Color.GREEN);

            //setting the paint color to white to draw the flecks
            paint.setARGB(255, R, G, B);

            //drawing all flecks
            for (Flecks s : flecks) {
                paint.setStrokeWidth(s.getFleckWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            canvas.drawBitmap(
                    playerWalk[a].getBitmap(),
                    playerWalk[a].getX(),
                    playerWalk[a].getY(),
                    paint);

            //drawing the encounters
            for (int i = 0; i < encounterCount; i++) {
                canvas.drawBitmap(
                        encounters[i].getBitmap(),
                        encounters[i].getX(),
                        encounters[i].getY(),
                        paint
                );
            }

            //drawing boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            resume();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                playerWalk[a].stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                playerWalk[a].setBoosting();
                break;
        }
        return true;
    }

}