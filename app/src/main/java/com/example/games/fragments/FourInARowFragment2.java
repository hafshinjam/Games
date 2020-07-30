package com.example.games.fragments;

import android.graphics.Color;
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

public class FourInARowFragment2 extends Fragment {
    private Button[][] mCellButtons = new Button[5][5];
    private String[][] mCellCondition = new String[5][5];
    private String SAVED_CELL_CONDITION_TYPE2 = "com.example.games.fragments.CellConditionType2";

    private int player1Score = 0;
    private int player2Score = 0;
    private String mTurn = "player1";

    public FourInARowFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_CELL_CONDITION_TYPE2, mCellCondition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_in_a_row, container, false);
        findViews(view);
        if (savedInstanceState != null) {
            mCellCondition = (String[][]) savedInstanceState.getSerializable(SAVED_CELL_CONDITION_TYPE2);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (mCellCondition[i][j].equals("player1"))
                        mCellButtons[i][j].setBackgroundColor(Color.RED);
                    else if (mCellCondition[i][j].equals("player2"))
                        mCellButtons[i][j].setBackgroundColor(Color.BLUE);
                }
            }
        } else
            clearConditions();
        setClickListeners();
        return view;
    }

    private void turnOperation(int row, int column) {
        for (int i = 4; i >= 0; i--) {
            if (mCellCondition[i][column].equals("empty")) {
                mCellCondition[i][column] = mTurn;
                if (mCellCondition[i][column].equals("player1"))
                    mCellButtons[i][column].setBackgroundColor(Color.RED);
                else if (mCellCondition[i][column].equals("player2"))
                    mCellButtons[i][column].setBackgroundColor(Color.BLUE);
                checkIfScored(i, column);
                checkIfFinished();
                if (mTurn.equals("player1"))
                    mTurn = "player2";
                else mTurn = "player1";
                return;
            }
        }
    }

    private void checkIfFinished() {
        for (int i = 0; i < mCellCondition.length; i++)
            for (String condition : mCellCondition[i])
                if (condition.equals("empty"))
                    return;
        finishOperation();
    }

    private void finishOperation() {
        final String winner;
        final String player1Score = "\n player1: " + this.player1Score;
        final String player2Score = "\n player2: " + this.player2Score;
        if (this.player1Score > this.player2Score)
            winner = " player 1 wins!";
        else if (this.player2Score > this.player1Score)
            winner = " player 2 wins!";
        else
            winner = "it's a tie";
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), winner, Toast.LENGTH_LONG).show();
            }
        }, 2000);

        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                String scoreBoard = "Scores are : " + player1Score + player2Score;
                Toast.makeText(getActivity(), scoreBoard, Toast.LENGTH_LONG).show();
            }
        }, 4000);
    }

    private void checkIfScored(int row, int column) {
        checkDiagonal(row, column);
        checkStraight(row, column);
    }

    private void checkDiagonal(int row, int column) {
        int tempCount = 1;
        int tempRow = row + 1;
        int tempColumn = column - 1;
        //check cells diagonally to left bellow
        while (tempColumn >= 0 && tempRow < 5) {
            if (!mCellCondition[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            tempColumn--;
            tempRow++;
        }
        //check cells diagonally to right above
        tempColumn = column + 1;
        tempRow = row - 1;
        while (tempColumn < 5 && tempRow >= 0) {
            if (!mCellCondition[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            tempRow--;
            tempColumn++;
        }
        if (tempCount >= 4)
            addScore(mTurn);
        //check cells diagonally to right bellow
        tempCount = 1;
        tempColumn = column + 1;
        tempRow = row + 1;
        while (tempColumn < 5 && tempRow < 5) {
            if (!mCellCondition[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            tempColumn++;
            tempRow++;
        }
        //check cells diagonally to left above
        tempColumn = column - 1;
        tempRow = row - 1;
        while (tempColumn >= 0 && tempRow >= 0) {
            if (!mCellCondition[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            tempColumn--;
            tempRow--;
        }
        if (tempCount >= 4)
            addScore(mTurn);
    }

    private void checkStraight(int row, int column) {
        int tempCount = 1;
        int tempRow = row + 1;
        int tempColumn = column;
        //checking cells bellow
        while (tempRow < 5) {
            if (!mCellCondition[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            tempRow++;
        }
        if (tempCount >= 4)
            addScore(mTurn);
        tempRow = row;
        tempColumn = column - 1;
        tempCount = 1;
        //checking cells before
        while (tempColumn >= 0) {
            if (!mCellCondition[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            tempColumn--;
        }
        //checking cells after
        tempColumn = column + 1;
        while (tempColumn < 5) {
            if (!mCellCondition[tempRow][tempColumn].equals(mTurn))
                break;
            tempCount++;
            tempColumn++;
        }
        if (tempCount >= 4)
            addScore(mTurn);
    }

    private void addScore(String tempTurn) {
        if (tempTurn.equals("player1"))
            player1Score++;
        else
            player2Score++;
    }

    private void clearConditions() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mCellCondition[i][j] = "empty";
            }
        }
    }

    private void setClickListeners() {
        mCellButtons[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(0, 0);
            }
        });
        mCellButtons[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(0, 1);
            }
        });
        mCellButtons[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(0, 2);
            }
        });
        mCellButtons[0][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(0, 3);
            }
        });
        mCellButtons[0][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(0, 4);
            }
        });


        mCellButtons[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(1, 0);
            }
        });
        mCellButtons[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(1, 1);
            }
        });
        mCellButtons[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(1, 2);
            }
        });
        mCellButtons[1][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(1, 3);
            }
        });
        mCellButtons[1][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(1, 4);
            }
        });


        mCellButtons[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(2, 0);
            }
        });
        mCellButtons[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(2, 1);
            }
        });
        mCellButtons[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(2, 2);
            }
        });
        mCellButtons[2][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(2, 3);
            }
        });
        mCellButtons[2][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(2, 4);
            }
        });


        mCellButtons[3][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(3, 0);
            }
        });
        mCellButtons[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(3, 1);
            }
        });
        mCellButtons[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(3, 2);
            }
        });
        mCellButtons[3][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(3, 3);
            }
        });
        mCellButtons[3][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(3, 4);
            }
        });


        mCellButtons[4][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(4, 0);
            }
        });
        mCellButtons[4][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(4, 1);
            }
        });
        mCellButtons[4][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(4, 2);
            }
        });
        mCellButtons[4][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(4, 3);
            }
        });
        mCellButtons[4][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOperation(4, 4);
            }
        });
    }

    private void findViews(View view) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mCellButtons[i][j] = new Button(getActivity());
                mCellButtons[i][j] = view.findViewById(getResources().
                        getIdentifier("cell" + i + "" + j + "_button", "id",
                                getActivity().getPackageName()));
            }
        }
    }
}