import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Класс транслятора из Pascal в C++
public class Translator {
    private final GrammarInterface outputGrammar;
    private final GrammarInterface inputGrammar;
    private ArrayList<Integer> parseString;
    private ArrayList<Pair> lexems;
    private String body = "";
    private String header = "";
    private ArrayList<Integer> type = new ArrayList() {
        {
            add(6);
            add(7);
            add(8);
            add(9);
            add(10);
        }
    };

//    Конструктор класса, принимающий на вход грамматику, список разбора и список лексем
    Translator(GrammarInterface outputGrammar, GrammarInterface inputGrammar, ParseTree tree, ArrayList<Pair> lexems) {
        this.outputGrammar = outputGrammar;
        this.inputGrammar = inputGrammar;
        this.parseString = getParse(tree.getRoot().getChilds());
        this.parseString.add(0, 0);
        this.lexems = lexems;
    }

    private ArrayList<Integer> getParse(ArrayList<TreeItem> childs) {
        ArrayList<Integer> parseString = new ArrayList();
        int index = childs.size() - 1;
        for (int i = index; i >= 0; i--) {
            if (childs.get(i).getVal().getType().equals("nterm")) {
                ArrayList<GrammarRule> possibleRules = inputGrammar.getRules(childs.get(i).getVal());
                if (possibleRules.size() == 1) {
                    parseString.add(inputGrammar.getRuleIndex(possibleRules.get(0)));
                } else {
                    int j = 0;
                    boolean isFind = false;
                    while (!isFind && j < possibleRules.size()) {
                        ArrayList<Pair> right = possibleRules.get(j).getRight();
                        ArrayList<TreeItem> downLvl = childs.get(i).getChilds();
                        if (downLvl.size() == right.size()) {
                            boolean isOk = true;
                            for (int k = 0; k < childs.get(i).getChilds().size(); k++) {
                                if (!downLvl.get(k).getVal().equals(right.get(k))) {
                                    isOk = false;
                                }
                            }
                            isFind = isOk;
                            if (isFind) {
                                parseString.add(inputGrammar.getRuleIndex(possibleRules.get(j)));
                            }
                        }
                        j++;
                    }
                }
                parseString.addAll(getParse(childs.get(i).getChilds()));
            }
        }
        return parseString;
    }

//    процедура транслирования, результат записывается в файл filename
    public void translate(String filename) {
        prepare();
        fillValues();
        formatIO();
        formatFunc();
        formatCode();
        if (!this.header.isEmpty()) {
            this.header += "\nusing namespace std;\n\n";
        }
        String program = this.header + this.body;
        try ( FileWriter writer = new FileWriter(filename, false)) {
            writer.write(program);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void prepare() {
        int stash = 0;
        for (int i = 0; i < this.parseString.size(); i++) {
            GrammarRule current = this.outputGrammar.getRuleByIndex(parseString.get(i));
            String right = "";
            right = current.getRight().stream().map(pair -> pair.toString() + " ").reduce(right, String::concat);
            String left = current.getLeft().toString();
            if (this.body.isEmpty()) {
                this.body += right;
            } else {
                if (stash == 0 && this.type.contains(parseString.get(i))) {
                    stash = parseString.get(i);
                } else {
                    if (stash != 0 && this.type.contains(parseString.get(i))) {
                        GrammarRule typeRule = this.outputGrammar.getRuleByIndex(stash);
                        right = "";
                        right = typeRule.getRight().stream().map(pair -> pair.toString() + " ").reduce(right, String::concat);
                        left = typeRule.getLeft().toString();
                        stash = parseString.get(i);
                    }
                    if (i == 1) {
                        this.body = replaceLast(this.body, left, right).replace("<", "<раздел описания> <");
                    } else {
                        this.body = replaceLast(this.body, left, right);
                    }
                }
            }
        }
        if (stash != 0) {
            GrammarRule typeRule = this.outputGrammar.getRuleByIndex(stash);
            String right = "";
            right = typeRule.getRight().stream().map(pair -> pair.toString() + " ").reduce(right, String::concat);
            String left = typeRule.getLeft().toString();
            this.body = replaceLast(this.body, left, right);
        }
    }

    private void fillValues() {
        for (int i = 0; i < this.lexems.size(); i++) {
            String typeOfLexem = this.lexems.get(i).getType();
            String value = this.lexems.get(i).getName();
            switch (typeOfLexem) {
                case "id" -> {
                    this.body = replaceFirst(this.body, "?id?", value);
                }
                case "compare" -> {
                    if (value.equals("=")) {
                        value = "==";
                    }
                    if (value.equals("<>")) {
                        value = "!=";
                    }
                    this.body = replaceFirst(this.body, "?compare?", value);
                }
                case "real" -> {
                    this.body = replaceFirst(this.body, "?real?", value);
                }
                case "integer" -> {
                    this.body = replaceFirst(this.body, "?integer?", value);
                }
                case "char" -> {
                    this.body = replaceFirst(this.body, "?char?", value);
                }
                case "string" -> {
                    this.body = replaceFirst(this.body, "?string?", value.replace("\'", "\""));
                }
                case "plus operator" -> {
                    this.body = replaceFirst(this.body, "?plus operator?", value);
                }
                case "mult operator" -> {
                    this.body = replaceFirst(this.body, "?mult operator?", value);
                }
                case "keyword" -> {
                    if (value.equals("for")) {
                        i++;
                        value = this.lexems.get(i).getName();
                        this.body = replaceFirst(this.body, "?id?", value);
                        this.body = replaceFirst(this.body, "?id?", value);
                        this.body = replaceFirst(this.body, "?id?", value);
                    }
                }
            }
        }
    }

    private void formatIO() {
        int start = this.body.indexOf("cin");
        if (start != -1) {
            this.header += "#include <iostream> \n";
            while (start != -1) {
                int end = this.body.indexOf(";", start);
                String frontOld = this.body.substring(0, start);
                String backOld = this.body.substring(end, this.body.length());
                String cin = this.body.substring(start, end).replace(",", " >> ");
                this.body = frontOld + cin + backOld;
                start = this.body.indexOf("cin", end);
            }
        }
        start = this.body.indexOf("cout");
        if (start != -1) {
            if (this.header.isEmpty()) {
                this.header += "#include <iostream> \n";
            }
            while (start != -1) {
                int end = this.body.indexOf(";", start);
                String frontOld = this.body.substring(0, start);
                String backOld = this.body.substring(end, this.body.length());
                String cout = this.body.substring(start, end).replace(",", " << ");
                this.body = frontOld + cout + backOld;
                start = this.body.indexOf("cout", end);
            }
        }

        start = this.body.indexOf("string");
        if (start != -1) {
            if (this.header.isEmpty()) {
                this.header += "#include <iostream> \n";
            }
        }
    }

    private void formatCode() {
        this.body = this.body.replaceAll("( +)", " ").trim().replaceAll("( ;)", ";");
        int start = 0;
        int end = 0;
        String newBody = "";
        int counterOfTab = 0;
        for (int i = 0; i < this.body.length(); i++) {
            char current = this.body.charAt(i);
            if (current == '{' || current == '}' || current == ';') {
                end = i;
                if (current == '{') {
                    counterOfTab++;
                }
                if (current == '}') {
                    counterOfTab--;
                }
                newBody += this.body.substring(start, end + 1)
                        + "\n"
                        + "    ".repeat(counterOfTab);
                start = end + 2;
            }
        }
        this.body = newBody;
    }

    private void formatFunc() {
        if (this.parseString.contains(21)) {
            this.header += "#include <cmath>\n";
        }
        if (this.parseString.contains(23)) {
            int start = this.body.indexOf("pow");
            if (start != -1) {
                while (start != -1) {
                    int end = this.body.indexOf(")", start);
                    String frontOld = this.body.substring(0, start);
                    String backOld = this.body.substring(end, this.body.length());
                    String pow = this.body.substring(start, end - 1) + ",2";
                    this.body = frontOld + pow + backOld;
                    start = this.body.indexOf("pow", end);
                }
            }
        }
    }

    private String replaceLast(String input, String oldSub, String newSub) {
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
            } else {
                countOfEq = 0;
            }
            i--;
        }
        return input.substring(0, start) + newSub + input.substring(end + 1, input.length());
    }

    private String replaceFirst(String input, String oldSub, String newSub) {
        int start = 0;
        int end = 0;
        int countOfEq = 0;
        boolean isFind = false;
        int i = 0;
        while (i < input.length() && !isFind) {
            if (input.charAt(i) == oldSub.charAt(countOfEq)) {
                if (countOfEq == 0) {
                    start = i;
                }
                if (countOfEq == oldSub.length() - 1) {
                    isFind = true;
                    end = i;
                }
                countOfEq++;
            } else {
                countOfEq = 0;
            }
            i++;
        }
        return input.substring(0, start) + newSub + input.substring(end + 1, input.length());
    }
}
