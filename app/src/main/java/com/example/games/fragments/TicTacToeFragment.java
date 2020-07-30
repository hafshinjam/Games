package com.example.games.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.games.R;


public class TicTacToeFragment extends Fragment {
    private String[][] mCellsTicTacToe = new String[3][3];
    private Button[][] mCellButtons = new Button[3][3];

    private int player1Score = 0;
    private int player2Score = 0;
    private String mTurn = "player1";
    private String CELL_TIC_TAC_TOE_CONDITIONS = "com.example.games.fragments.CellConditionTicTacToe";

    public TicTacToeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CELL_TIC_TAC_TOE_CONDITIONS, mCellsTicTacToe);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
        findViews(view);
        if (savedInstanceState != null) {
            mCellsTicTacToe = (String[][]) savedInstanceState.getSerializable(CELL_TIC_TAC_TOE_CONDITIONS);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mCellsTicTacToe[i][j].equals("player1"))
                        mCellButtons[i][j].setText("X");
                    else if (mCellsTicTacToe[i][j].equals("player2"))
                        mCellButtons[i][j].setText("O");
                }
            }
        } else
            emptyCells();
        setClickListeners();
        return view;
    }

    private void findViews(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCellButtons[i][j] = new Button(getActivity());
                mCellButtons[i][j] = view.findViewById(getResources().
                        getIdentifier("button" + i + "" + j, "id",
                                getActivity().getPackageName()));
            }
        }
    }

    private void turnOperations(int row, int column) {
        if (mCellsTicTacToe[row][column].equals("empty")) {
            if (mTurn.equals("player1")) {
                mCellButtons[row][column].setText("X");
                mCellsTicTacToe[row][column] = "player1";
                checkIfScored(row, column);
                mTurn = "player2";
            } else {
                mCellButtons[row][column].setText("O");
                mCellsTicTacToe[row][column] = "player2";
                checkIfScored(row, column);
                mTurn = "player1";
            }
        }
    }

    private void checkIfScored(int row, int column) {
        String resultTie = "it's a tie !!!";
        if (checkStraight(row, column)) {
            finishSet(mTurn);
            return;
        } else if (((row == 0 || row == 2) && (column == 0 || column == 2)) || (row == 1 && column == 1)) {
            if (checkDiagonally(row, column)) {
                finishSet(mTurn);
                return;
            }
        }
        if (isFull())
            finishSet(resultTie);
    }

    private boolean checkDiagonally(int row, int column) {
        int tempCount = 1;
        int tempRow = row - 1;
        int tempColumn = column - 1;
        while (tempColumn >= 0 && tempRow >= 0) {
            if (!mCellsTicTacToe[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            if (tempCount == 3)
                return true;
            tempColumn--;
            tempRow--;
        }
        tempColumn = column + 1;
        tempRow = row + 1;
        while (tempColumn < 3 && tempRow < 3) {
            if (!mCellsTicTacToe[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            if (tempCount == 3)
                return true;
            tempColumn++;
            tempRow++;
        }
        tempCount = 1;
        tempColumn = column + 1;
        tempRow = row - 1;
        while (tempColumn < 3 && tempRow >= 0) {
            if (!mCellsTicTacToe[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            if (tempCount == 3)
                return true;
            tempRow--;
            tempColumn++;
        }
        tempColumn = column - 1;
        tempRow = row + 1;
        while (tempColumn >= 0 && tempRow < 3) {
            if (!mCellsTicTacToe[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            if (tempCount == 3)
                return true;
            tempColumn--;
            tempRow++;
        }
        return false;
    }

    private boolean checkStraight(int row, int column) {
        int tempCount = 1;
        int tempRow = row;
        int tempColumn = column - 1;
        while (tempColumn >= 0) {
            if (mCellsTicTacToe[row][tempColumn].equals(mTurn)) {
                tempCount++;
                if (tempCount == 3)
                    return true;
            }
            tempColumn--;
        }
        tempColumn = column + 1;
        while (tempColumn < 3) {
            if (mCellsTicTacToe[row][tempColumn].equals(mTurn)) {
                tempCount++;
                if (tempCount == 3)
                    return true;
            }
            tempColumn++;
        }
        tempCount = 1;
        tempColumn = column;
        tempRow = row - 1;
        while (tempRow >= 0) {
            if (mCellsTicTacToe[tempRow][column].equals(mTurn)) {
                tempCount++;
                if (tempCount == 3)
                    return true;
            }
            tempRow--;
        }
        tempRow = row + 1;
        while (tempRow < 3) {
            if (mCellsTicTacToe[tempRow][column].equals(mTurn)) {
                tempCount++;
                if (tempCount == 3)
                    return true;
            }
            tempRow++;
        }
        return false;
    }

    private boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mCellsTicTacToe[i][j].equals("empty"))
                    return false;
            }
        }
        return true;
    }

    private void finishSet(String result) {
        if (result.equals("player1")) {
            player1Score++;
            Toast.makeText(getActivity(), result + " is winner of this set!!! ", Toast.LENGTH_LONG).show();
            disableButtons();
        } else if (result.equals("player2")) {
            player2Score++;
            Toast.makeText(getActivity(), result + " is winner of this set!!! ", Toast.LENGTH_LONG).show();
            disableButtons();
        } else
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), R.string.new_game_start_message, Toast.LENGTH_LONG).show();
                newSet();
            }
        }, 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newSet();
            }
        }, 8000);
    }

    private void emptyCells() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCellsTicTacToe[i][j] = "empty";
                mCellButtons[i][j].setText("");
            }
        }
    }

    private void newSet() {
        emptyCells();
        mTurn = "player1";
        enableButtons();
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCellButtons[i][j].setClickable(false);
            }
        }
    }

    private void enableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCellButtons[i][j].setClickable(true);
            }
        }
    }

    private void setClickListeners() {
        mCellButtons[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(0, 0);
            }
        });
        mCellButtons[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(0, 1);
            }
        });
        mCellButtons[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(0, 2);
            }
        });

        mCellButtons[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(1, 0);
            }
        });
        mCellButtons[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(1, 1);
            }
        });
        mCellButtons[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(1, 2);
            }
        });

        mCellButtons[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(2, 0);
            }
        });
        mCellButtons[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(2, 1);
            }
        });
        mCellButtons[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperations(2, 2);
            }
        });
    }
}