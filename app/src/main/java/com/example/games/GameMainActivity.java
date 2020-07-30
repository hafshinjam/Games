package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.games.fragments.FourInARowFragment;
import com.example.games.fragments.FourInARowFragment2;
import com.example.games.fragments.TicTacToeFragment;

public class GameMainActivity extends AppCompatActivity {
    private Button mTicTacToeButton;
    private Button mFourInARowButton;
    private Button mFourInARowVer2Button;
    private TextView mGameTag;
    private TextView mGameNameTag;
    private FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_main);
        findAllViews();
        Fragment temp = getSupportFragmentManager().findFragmentByTag("TicTacToe");
        Fragment temp2 = getSupportFragmentManager().findFragmentByTag("FourInARow");
        Fragment temp3 = getSupportFragmentManager().findFragmentByTag("FourInARowVer2");
        if (temp == null && temp2 == null && temp3 == null)
            fragmentManager.beginTransaction().replace(R.id.game_fragment_container, new TicTacToeFragment(), "TicTacToe").commit();
        setGameName();
        setOnClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void findAllViews() {
        mFourInARowButton = findViewById(R.id.four_in_a_row_button);
        mTicTacToeButton = findViewById(R.id.tic_tac_toe_button);
        mGameNameTag = findViewById(R.id.game_name_tag);
        mGameTag = findViewById(R.id.game_tag);
        mFourInARowVer2Button = findViewById(R.id.four_in_a_row_ver2_button);
    }

    private void setGameName() {
        mGameNameTag.setText(R.string.tic_tac_toe_name);
        Fragment temp = getSupportFragmentManager().findFragmentByTag("FourInARowVer2");
        Fragment temp2 = getSupportFragmentManager().findFragmentByTag("FourInARow");
        if (temp instanceof FourInARowFragment2) {
            mGameNameTag.setText(R.string.four_in_a_row_tag_ver2);
        } else if (temp2 instanceof FourInARowFragment) {
            mGameNameTag.setText(R.string.four_in_a_row_button_text);
        }
    }

    private void setOnClickListener() {
        mTicTacToeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mGameNameTag.getText().equals(String.valueOf(R.string.tic_tac_toe_name))) {
                    fragmentManager.beginTransaction().replace(R.id.game_fragment_container, new TicTacToeFragment(), "TicTacToe").commit();
                    mGameNameTag.setText(R.string.tic_tac_toe_name);
                }
            }
        });
        mFourInARowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mGameNameTag.getText().equals(String.valueOf(R.string.four_in_a_row_button_text))) {
                    fragmentManager.beginTransaction().replace(R.id.game_fragment_container, new FourInARowFragment2(), "FourInARow").commit();
                    mGameNameTag.setText(R.string.four_in_a_row_button_text);
                }
            }
        });
        mFourInARowVer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mGameNameTag.getText().equals(String.valueOf(R.string.four_in_a_row_tag_ver2))) {
                    fragmentManager.beginTransaction().replace(R.id.game_fragment_container, new FourInARowFragment(), "FourInARowVer2").commit();
                    mGameNameTag.setText(R.string.four_in_a_row_tag_ver2);
                }
            }
        });
    }
}