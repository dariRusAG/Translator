import java.io.UnsupportedEncodingException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        String pascalFile = "src/program_2.txt";
        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        LexAnalyzer pascalLexAnal = new LexAnalyzer(pascalFile);
        try {
            pascalLexAnal.makeAnalysis();
//            pascalLexAnal.print();
            PascalGrammar pascalGrammar = new PascalGrammar(new Pair("nterm","программа"));
//            pascalGrammar.print();
            CGrammar cGrammar = new CGrammar(new Pair("nterm", "программа"));
//            cGrammar.print();

            SynAnalyzer pascalSynAnal = new SynAnalyzer(pascalLexAnal.getListLexem(), pascalGrammar);
            pascalSynAnal.makeTable();
//            pascalSynAnal.printTable();

            pascalSynAnal.parse();
            pascalSynAnal.buildTree();

            SemAnalyzer pascalSemAnal = new SemAnalyzer(pascalSynAnal.getTree());
            pascalSemAnal.makeAnalysis();
            if (!pascalSemAnal.hasError()) {
                // Трансляция в С++
                Translator pascalToC = new Translator(cGrammar,
                        pascalSynAnal.getParse(),
                        pascalLexAnal.getListLexem());
            }

        } catch (Exception e) {
            ps.print(e.getMessage());
        }
    }
}