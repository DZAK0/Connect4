import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sac.State;
import sac.StateFunction;
import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import sac.game.GameState;
import sac.game.GameStateImpl;

public class main extends GameStateImpl {

    public static final int m = 6; // liczba wierszy
    public static final int n = 7; // liczba kolumn
    char symbol;
    public char[][] board = null;
    boolean xStart = true;
    String s = ""; // string trzymajacy wynik
    private static Scanner scan;

    public main() {
        board = new char[m][n];
        symbol = ' ';
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = symbol;
            }
        }
    }

    public main(main parent) {
        board = new char[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = parent.board[i][j];
        setMaximizingTurnNow(parent.isMaximizingTurnNow());

    }

    public List<GameState> generateChildren() {

        List<GameState> children = new ArrayList<GameState>();
        for (int i = 0; i < n; i++) {
            String moveName = Integer.toString(i + 1);
            main child = new main(this);
            child.setMoveName(moveName);
            child.makeMove(i + 1);
            children.add(child);

        }
        return children;

    }

    public boolean makeMove(int column) {

        for (int i = m - 1; i >= 0; i--) {

            if (board[i][column - 1] == ' ') {

                if (isMaximizingTurnNow()) {
                    board[i][column - 1] = 'X';
                    setMaximizingTurnNow(!isMaximizingTurnNow());
                    return true;
                } else {
                    board[i][column - 1] = 'O';
                    setMaximizingTurnNow(!isMaximizingTurnNow());
                    return true;
                }

            }

        }
        return false;
    }

    public String toString() {

        StringBuilder txt = new StringBuilder();

        txt.append("  ----------------------------");
        txt.append("\n");
        for (int i = 0; i < m; i++) {
            txt.append(" | ");
            for (int j = 0; j < n; j++) {

                txt.append(board[i][j] + " | ");
            }
            txt.append("\n");
            txt.append("  ----------------------------");
            txt.append("\n");
        }
        int licznik = 1;

        txt.append(" | ");
        for (int i = 0; i < n; i++) {
            txt.append(licznik + " | ");
            licznik++;
        }
        txt.append("\n");

        return txt.toString();
    }

    public boolean sufitO() {
        for (int i = 0; i < n; i++)
            if (board[0][i] == 'O') {
                return true;
            }
        return false;
    }

    public boolean sufitX() {
        for (int i = 0; i < n; i++)
            if (board[0][i] == 'X') {
                return true;
            }
        return false;
    }

    public boolean isGameFinished() {

        for (int i = 0; i < n; i++) {
            if (board[0][i] == 'X') {
                return true;
            } else if (board[0][i] == 'O') {
                return true;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] == 'X') {
                    if (i < m - 3 && j > 2)
                        if (board[i + 1][j - 1] == 'X')
                            if (board[i + 2][j - 2] == 'X')
                                if (board[i + 3][j - 3] == 'X') {
                                    return true;
                                }
                    if (i < m - 3)
                        if (board[i + 1][j] == 'X')
                            if (board[i + 2][j] == 'X')
                                if (board[i + 3][j] == 'X') {
                                    return true;
                                }

                    if (i < m - 3 && j < n - 3)
                        if (board[i + 1][j + 1] == 'X')
                            if (board[i + 2][j + 2] == 'X')
                                if (board[i + 3][j + 3] == 'X') {
                                    return true;
                                }

                    if (j < n - 3)
                        if (board[i][j + 1] == 'X')
                            if (board[i][j + 2] == 'X')
                                if (board[i][j + 3] == 'X') {
                                    return true;
                                }
                }
                if (board[i][j] == 'O') {
                    if (i < m - 3 && j > 2)
                        if (board[i + 1][j - 1] == 'O')
                            if (board[i + 2][j - 2] == 'O')
                                if (board[i + 3][j - 3] == 'O') {
                                    return true;
                                }

                    if (i < m - 3)
                        if (board[i + 1][j] == 'O')
                            if (board[i + 2][j] == 'O')
                                if (board[i + 3][j] == 'O') {
                                    return true;
                                }

                    if (i < m - 3 && j < n - 3)
                        if (board[i + 1][j + 1] == 'O')
                            if (board[i + 2][j + 2] == 'O')
                                if (board[i + 3][j + 3] == 'O') {
                                    return true;
                                }

                    if (j < n - 3)
                        if (board[i][j + 1] == 'O')
                            if (board[i][j + 2] == 'O')
                                if (board[i][j + 3] == 'O') {
                                    return true;
                                }
                }
            }
        }
        return false;
    }

    ///////////////////////

    public double Check_LD() {
        double licznik = 0.0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i < m - 3 && j > 2) {
                    s = "";
                    StringBuilder sB = new StringBuilder(s);
                    sB.append(board[i][j]);
                    sB.append(board[i + 1][j - 1]);
                    sB.append(board[i + 2][j - 2]);
                    sB.append(board[i + 3][j - 3]);
                    s = sB.toString();
                    licznik += punktCale(s);
                }
            }
        }
        return licznik;
    }

    public double Check_D() {
        double licznik = 0.0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i < m - 3) {
                    s = "";
                    StringBuilder sB = new StringBuilder(s);
                    sB.append(board[i][j]);
                    sB.append(board[i + 1][j]);
                    sB.append(board[i + 2][j]);
                    sB.append(board[i + 3][j]);
                    s = sB.toString();
                    licznik += punktCale(s);
                }
            }
        }
        return licznik;
    }

    public double Check_PD() {
        double licznik = 0.0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i < m - 3 && j < n - 3) {
                    s = "";
                    StringBuilder sB = new StringBuilder(s);
                    sB.append(board[i][j]);
                    sB.append(board[i + 1][j + 1]);
                    sB.append(board[i + 2][j + 2]);
                    sB.append(board[i + 3][j + 3]);
                    s = sB.toString();
                    licznik += punktCale(s);
                }
            }
        }
        return licznik;
    }

    public double Check_P() {
        double licznik = 0.0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j < n - 3) {
                    s = "";
                    StringBuilder sB = new StringBuilder(s);
                    sB.append(board[i][j]);
                    sB.append(board[i][j + 1]);
                    sB.append(board[i][j + 2]);
                    sB.append(board[i][j + 3]);
                    s = sB.toString();
                    licznik += punktCale(s);
                }
            }
        }
        return licznik;
    }

    public double punktCale(String k)
    {
        if(k.equals("O   ")) return -3.0;
        if(k.equals("    ")) return -8.0;
        if(k.equals("0  X")) return -2.0;
        if(k.equals("O O ")) return -13.0;
        if(k.equals("O OO")) return -113.0;
        if(k.equals("O OX")) return -7;
        if(k.equals("O X ")) return -1.0;
        if(k.equals("O XO")) return -1.0;
        if(k.equals("O XX")) return -1.0;
        if(k.equals("OO  ")) return -18.0;
        if(k.equals("OO O")) return -113.0;
        if(k.equals("00 X")) return -12.0;
        if(k.equals("OOO ")) return -218;
        if(k.equals("OOOX")) return -113.0;
        if(k.equals("OOX ")) return -6;
        if(k.equals("OOXO")) return -6;
        if(k.equals("OOXX")) return -6;
        if(k.equals("X   ")) return 6;
        if(k.equals("X  O")) return 4;
        if(k.equals("X  X")) return 12;
        if(k.equals("X O ")) return 2;
        if(k.equals("X OO")) return 2;
        if(k.equals("X OX")) return 2;
        if(k.equals("X X ")) return 18;
        if(k.equals("X XO")) return 10;
        if(k.equals("X XX")) return 218;
        if(k.equals("XX  ")) return 24;
        if(k.equals("XX O")) return 16;
        if(k.equals("XX X")) return 224;
        if(k.equals("XXO ")) return 8;
        if(k.equals("XXOO")) return 8;
        if(k.equals("XXOX")) return 8;
        if(k.equals("XXX ")) return 424;
        if(k.equals("XXXO")) return 216;
        else
            return 0.0;
    }

    public double punktPolowa(String k)
    {
        if(k.equals("O   ")) return -1.5;
        if(k.equals("    ")) return -4.0;
        if(k.equals("0  X")) return -1.0;
        if(k.equals("O O ")) return -6.5;
        if(k.equals("O OO")) return -56.5;
        if(k.equals("O OX")) return -3.5;
        if(k.equals("O X ")) return -0.5;
        if(k.equals("O XO")) return -0.5;
        if(k.equals("O XX")) return -0.5;
        if(k.equals("OO  ")) return -9.0;
        if(k.equals("OO O")) return -56.5;
        if(k.equals("00 X")) return -6.0;
        if(k.equals("OOO ")) return -109;
        if(k.equals("OOOX")) return -56.5;
        if(k.equals("OOX ")) return -3;
        if(k.equals("OOXO")) return -3;
        if(k.equals("OOXX")) return -3;
        if(k.equals("X   ")) return 3;
        if(k.equals("X  O")) return 2;
        if(k.equals("X  X")) return 6;
        if(k.equals("X O ")) return 1;
        if(k.equals("X OO")) return 1;
        if(k.equals("X OX")) return 1;
        if(k.equals("X X ")) return 9;
        if(k.equals("X XO")) return 5;
        if(k.equals("X XX")) return 109;
        if(k.equals("XX  ")) return 12;
        if(k.equals("XX O")) return 8;
        if(k.equals("XX X")) return 112;
        if(k.equals("XXO ")) return 4;
        if(k.equals("XXOO")) return 4;
        if(k.equals("XXOX")) return 4;
        if(k.equals("XXX ")) return 212;
        if(k.equals("XXXO")) return 108;
        else
            return 0.0;
    }

    //////////////////////

    static {
        setHFunction(new StateFunction() {
            @Override
            public double calculate(State state) {
                main hwgs = (main) state;
                if (hwgs.isGameFinished())
                    return (hwgs.isMaximizingTurnNow()) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                else {
                    double z=hwgs.Check_D();
                    double x=hwgs.Check_LD();
                    double c=hwgs.Check_P();
                    double v=hwgs.Check_PD();
                    double suma = z+x+c+v;
                    return suma;
                }
            }
        });
    }

    public static void main(String[] args) {
        main c = new main();
        int ruch;
        int start;
        boolean rozpocznij;
        Scanner odczyt = new Scanner(System.in);
        scan = new Scanner(System.in);
        System.out.println("Jesli ma zaczac komputer, wybierz 0");
        System.out.println("Jesli ma zaczac gracz,  wybierz 1");
        start = scan.nextInt();
        if (start == 1) {
            rozpocznij = true;
        } else {
            rozpocznij = false;
        }
        System.out.println(c);
        boolean end = false;
        boolean turn = c.xStart; // ustawione na true - 1
        c.setMaximizingTurnNow(rozpocznij);
        while (!end) {
            // czlowiek
            if (c.isMaximizingTurnNow()) {
                ruch = odczyt.nextInt();
                c.makeMove(ruch);
                System.out.println(c);
                turn = !turn;
            }
            // komputer
            else {
                GameSearchAlgorithm a = new AlphaBetaPruning(c);
                a.execute();
                System.out.println(a.getMovesScores());

                c.makeMove(Integer.valueOf(a.getFirstBestMove()));
                System.out.println(c);
                // turn = !turn;

            }

            end = c.isGameFinished();

        }
        if (!(c.isMaximizingTurnNow())) {
            System.out.println("Wygrywa GRACZ!");
        } else {
            System.out.println("Wygrywa KOMPUTER!");
        }
        odczyt.close();
    }

}