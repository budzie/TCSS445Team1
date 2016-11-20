package edu.wa.tacoma.team1.tcss450.stickmanwalking.chenwei;

/**
 * Chenwei Qiu
 * 11/8/2016
 * GameActivityLink.java
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.wa.tacoma.team1.tcss450.stickmanwalking.R;
import edu.wa.tacoma.team1.tcss450.stickmanwalking.sally.GameActivityLink;

/**
 *  This class represents the game page
 */
public class GameActivity extends AppCompatActivity {
    private EditText editText;
    private String current_user;
    private int score;

    /**
     * this method create the every component in this activity in the beginning as well as listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        current_user = extras.getString("username");


        editText = (EditText) findViewById(R.id.score);


        Button np = (Button) findViewById(R.id.nextpage);
        np.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    score = Integer.parseInt(editText.getText().toString()) ;
                } catch (NumberFormatException e) {
                    Toast.makeText(GameActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(GameActivity.this, GameActivityLink.class);
                i.putExtra("username", current_user);
                i.putExtra("score", score);
                finish();
                startActivity(i);
            }
        });
    }
}
