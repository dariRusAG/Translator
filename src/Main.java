import java.io.UnsupportedEncodingException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
//        Для расширенных тестов
        String pascalFile = "tests/advancedTests/test_1.pas";

//        Для кратких тестов
//        String pascalFile = "tests/shortTests/test_1.pas";

        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        LexAnalyzer pascalLexAnal = new LexAnalyzer(pascalFile);
        try {
            pascalLexAnal.makeAnalysis();
//            Вывод результата работы лексического анализатор
//            pascalLexAnal.print();

            PascalGrammar pascalGrammar = new PascalGrammar(new Pair("nterm", "программа"));
//            Вывод всей грамматики Pascal
//            pascalGrammar.print();

            SynAnalyzer pascalSynAnal = new SynAnalyzer(pascalLexAnal.getListLexem(), pascalGrammar);
            pascalSynAnal.makeTable();
//            Вывод таблицы разбора по методу Эрли
//            pascalSynAnal.printTable();

            pascalSynAnal.parse();
//            Вывод цепочки разбора
//            pascalSynAnal.printParse();
            pascalSynAnal.buildTree();

            SemAnalyzer pascalSemAnal = new SemAnalyzer(pascalSynAnal.getTree());
            pascalSemAnal.makeAnalysis();

            if (!pascalSemAnal.hasError()) {
                CGrammar cGrammar = new CGrammar(new Pair("nterm", "программа"));
//                Вывод всей грамматики С++
//                cGrammar.print();
                // Трансляция в С++
                Translator pascalToC = new Translator(
                        cGrammar,
                        pascalGrammar,
                        pascalSynAnal.getTree(),
                        pascalLexAnal.getListLexem()
                );
                pascalToC.translate("result.cpp");
            }
        } catch (Exception e) {
            ps.print(e.getMessage());
        }
    }
}