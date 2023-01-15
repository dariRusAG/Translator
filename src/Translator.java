import java.util.ArrayList;

public class Translator {

    private final GrammarInterface grammar;
    private ArrayList<Integer> parseString;
    private ArrayList<Pair> lexems;
    private String out = "";

//    Конструктор класса, принимающий на вход грамматику, список разбора и список лексем
    Translator(GrammarInterface grammar, ArrayList<Integer> parseString, ArrayList<Pair> lexems) {
        this.grammar = grammar;
        this.parseString = parseString;
        this.lexems = lexems;
        makeOut();
    }

    private void makeOut() {
        for (int i = 0; i < this.parseString.size(); i++) {
            GrammarRule current = this.grammar.getRuleByIndex(parseString.get(i));
            ArrayList<Pair> rightPairs = current.getRight();
            String right = "";
            right = rightPairs.stream().map(pair -> pair.toString() + " ").reduce(right, String::concat);
            String left = current.getLeft().toString();
            if (this.out.isEmpty()) {
                this.out += right;
            } else {
                String newOut = myReplace(this.out,left,right);
                if (i == 1) {
                    String varBlock = newOut.replace("<", "<раздел описания> <");
                    newOut = varBlock;
                }
                this.out = newOut;
            }
        }
        while (true){}
    }

    private String myReplace(String input, String oldSub, String newSub) {
        int start = 0;
        int end = 0;
        int countOfEq = 0;
        boolean isFind = false;
        int i = input.length() - 1;
        while (i > 0 && !isFind) {
            if (input.charAt(i) == oldSub.charAt(oldSub.length() - countOfEq - 1)) {
                if (countOfEq == 0) {
                    end = i;
                }
                if (countOfEq == oldSub.length() - 1) {
                    isFind = true;
                    start = i;
                }
                countOfEq++;
            }
            i--;
        }
        String frontOld = input.substring(0,start);
        String backOld = input.substring(end+1, input.length());
        return frontOld + newSub + backOld;
    }
}
