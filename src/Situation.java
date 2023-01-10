import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

// Класс ситуации алгоритма Эрли <Rule rule, int pos>
public class Situation {
    private final GrammarRule rule;
    private final int pos;
    private boolean isProcessedEnd;
    private boolean isProcessedAtFront;

//    Конструктор класса Situation
    Situation(GrammarRule rule, int pos) {
        this.rule = rule;
        this.pos = pos;
        this.isProcessedEnd = false;
        this.isProcessedAtFront = false;
    }

//    Возвращает rule
    public GrammarRule getRule() {
        return rule;
    }
//    Возвращает pos
    public int getPos() {
        return pos;
    }

//    Вывод ситуации на экран
    public void print() throws UnsupportedEncodingException {
        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        ps.print("[");
        rule.print();
        ps.print(", " + pos + "]");
    }

//    Сравниваем текущую ситуацию с объектом
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Situation)) {
            return false;
        }
        Situation c = (Situation) o;
        boolean eq = true;
        if (!this.rule.getLeft().equals(c.getRule().getLeft())) {
            eq = false;
        }
        if (this.rule.getRight().size() != c.getRule().getRight().size()) {
            eq = false;
        }
        int i = 0;
        while (eq && (i < this.rule.getRight().size())) {
            if (!this.rule.getPair(i).equals(c.getRule().getPair(i))) {
                eq = false;
            }
            i++;
        }
        return eq;
    }

//    Процедура установки значения "Использовалась ли ситуация при поиски ситуаций с точкой на конце"
    public void setIsProcessedEnd(boolean isProcessed) {
        this.isProcessedEnd = isProcessed;
    }

//    Функция, возвращающая значение "Использовалась ли ситуация при поиски ситуаций с точкой на конце"
    public boolean getIsProcessedEnd() {
        return this.isProcessedEnd;
    }

//    Процедура установки значения "Использовалась ли ситуация при поиски ситуаций с точкой перед нетерминалом".
    public void setIsProcessedAtFront(boolean isProcessed) {
        this.isProcessedAtFront = isProcessed;
    }

//    Функция, возвращающая значение "Использовалась ли ситуация при поиски ситуаций с точкой перед нетерминалом"
    public boolean getIsProcessedAtFront() {
        return this.isProcessedAtFront;
    }
}
