import java.io.UnsupportedEncodingException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        String pascalFile = "src/code.pas";
        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        LexAnalyzer pascalLexAnal = new LexAnalyzer(pascalFile);
        try {
            pascalLexAnal.makeAnalysis();
            pascalLexAnal.print();

        } catch (Exception e) {
            ps.print(e.getMessage());
        }
    }
}