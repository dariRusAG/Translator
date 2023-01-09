import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

// Класс правило грамматики - <Pair left, ArrayList right> === [left -> right].
public class GrammarRule {
    private Pair left;
    private ArrayList<Pair> right;
    private final Pair dot = new Pair("$", "");

    // Конструктор правила
    GrammarRule(Pair left, ArrayList<Pair> right) {
        this.left = left;
        this.right = new ArrayList();
        this.right = right;
    }

    // Вывод правила
    public void print() throws UnsupportedEncodingException {
        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        this.left.print();
        ps.print(" -> ");
        for (int i = 0; i < this.right.size(); i++) {
            this.getPair(i).print();
            ps.print(" ");
        }
    }

    // Возврат левой части правила
    public Pair getLeft() {
        return this.left;
    }

    // Возврат правой части правила
    public ArrayList<Pair> getRight() {
        return this.right;
    }

    // Возврат номера Pair, равный symbol, из правой части правила
    // в случае, если нет такого символа возврат -1
    public int getPosSymbol(Pair symbol) {
        boolean isFound = false;
        int i = 0;
        while (!isFound && (i < this.right.size())) {
            if (this.right.get(i).equals(symbol)) {
                isFound = true;
            } else {
                i++;
            }
        }
        if (!isFound) {
            i = -1;
        }
        return i;
    }

    // Возврат текущего правила со сменой позиций правила в позиции left и right
    public GrammarRule swapPos(int left, int right) {
        ArrayList<Pair> newRight = new ArrayList();
        for (int i = 0; i < left; i++) {
            newRight.add(this.getPair(i));
        }
        newRight.add(this.getPair(right));
        newRight.add(this.getPair(left));
        for (int i = right + 1; i < this.right.size(); i++) {
            newRight.add(this.getPair(i));
        }
        this.right.clear();
        this.right = newRight;
        return this;
    }

    // Возврат Pair из правой части правила по позиции pos
    public Pair getPair(int pos) {
        return this.right.get(pos);
    }
}
