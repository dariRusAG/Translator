import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

// интерфейс класса грамматик (для Паскаль и С++)
public interface GrammarInterface {

// процедура наполнение грамматики правилами
    public void fillRules();

// метод вывода грамматики на экран
    public void print() throws UnsupportedEncodingException;

    public Pair getAxiom();

    public ArrayList<GrammarRule> getRules(Pair left);
}
