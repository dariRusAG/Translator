import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        String pascalFile = "src/program_3.txt";
        PrintStream ps = new PrintStream(System.out, false, "utf-8");

        LexAnalyzer pascalLexAnal = new LexAnalyzer(pascalFile);
        pascalLexAnal.makeAnalysis();
        pascalLexAnal.print();
        PascalGrammar pascalGrammar = new PascalGrammar(new Pair("nterm", "программа"));
        pascalGrammar.print();
        SynAnalyzer pascalSynAnal = new SynAnalyzer(pascalLexAnal.getListLexem(), pascalGrammar);
        try {
            pascalSynAnal.makeTable();
            pascalSynAnal.printTable();
            pascalSynAnal.parse();
            pascalSynAnal.printParse();
            pascalSynAnal.buildTree();
            try {
                SemAnalyzer pascalSemAnal = new SemAnalyzer(pascalSynAnal.getTree());
                pascalSemAnal.makeAnalysis();
                if (!pascalSemAnal.hasError()) {
                    CGrammar cGrammar = new CGrammar(new Pair("nterm", "программа"));
                    cGrammar.print();
                    // Трансляция в С++
                    Translator pascalToC = new Translator(cGrammar,
                            pascalSynAnal.getParse(),
                            pascalLexAnal.getListLexem());
                    pascalToC.translate("program.cpp");
                }
            } catch (Exception e) {
                ps.print(e.getMessage());
            }
        } catch (Exception e) {
            ps.print(e.getMessage());
        }
    }
}