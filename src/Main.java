import java.io.UnsupportedEncodingException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {

//        String pascalFile = "src/code.pas";
        String pascalFile = "src/program_1.pas";
        // String pascalFile = "program1.txt";
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
            pascalSynAnal.printTable();

//            pascalSynAnal.parse();

            //30,31,6,4,39,16,14,12,38,35,1,0
//            ArrayList<Integer> arrInt = new ArrayList<>();
//            arrInt.add(30);
//            arrInt.add(31);
//            arrInt.add(6);
//            arrInt.add(4);
//            arrInt.add(39);
//            arrInt.add(16);
//            arrInt.add(14);
//            arrInt.add(12);
//            arrInt.add(38);
//            arrInt.add(35);
//            arrInt.add(1);
//            arrInt.add(0);
//            //ParseTree tree = pascalSynAnal.buildTree(arrInt);
//
//            arrInt.add(0);

        } catch (Exception e) {
            PrintStream ps = new PrintStream(System.out, false, "cp1251");
            ps.print(e.getMessage());
        }


    }
}