import java.io.UnsupportedEncodingException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
         String pascalFile = "program1.txt";
//        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        LexAnalyzer pascalLexAnal = new LexAnalyzer(pascalFile);
        try {
            pascalLexAnal.makeAnalysis();
//            pascalLexAnal.print();
            PascalGrammar pascalGrammar = new PascalGrammar(new Pair("nterm","программа"));
//            pascalGrammar.print();
            CGrammar cGrammar = new CGrammar(new Pair("nterm", "программа"));
            cGrammar.print();
//            String pascalFile = "src/program_1.pas";
//            PrintStream ps = new PrintStream(System.out, false, "utf-8");
            SynAnalyzer pascalSynAnal = new SynAnalyzer(pascalLexAnal.getListLexem(), pascalGrammar);
//            pascalSynAnal.makeTable();
//            pascalSynAnal.printTable();

            pascalSynAnal.parse();
            pascalSynAnal.buildTree();

            //30,31,6,4,39,16,14,12,38,35,1,0
            int n = 9;

//            ParseTree tree = pascalSynAnal.buildTree(arrInt);



        } catch (Exception e) {
            PrintStream ps = new PrintStream(System.out, false, "cp1251");
            ps.print(e.getMessage());
        }
    }
}