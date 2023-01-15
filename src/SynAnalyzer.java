// Синтаксический анализатор
// Что делает: проверяет синтаксис языка.
// Вход: массив токенов.
// Выход: дерево разбора (или AST).

// Для синтаксического анализа был выбран алгоритм нисходящего разбора с возвратами.

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

// Класс синтаксического анализатора
public class SynAnalyzer {
    private int current = 0;
    private ArrayList<Pair> lexems;
    private final GrammarInterface grammar;
    private final ArrayList<ArrayList<Situation>> table;
    private ArrayList<Integer> parseString;
    private ParseTree parseTree;
    private final Pair dot = new Pair("$", "");
    private final Pair nterm = new Pair("nterm", "");

    //    Конструктор класса
    SynAnalyzer(ArrayList<Pair> lexems, GrammarInterface grammar) {
        this.lexems = lexems;
        this.grammar = grammar;
        this.parseString = new ArrayList();
        this.parseTree = new ParseTree(null);
        this.table = new ArrayList(new ArrayList());
    }

    //    процедура составления таблицы разбора для метода Эрли
    public void makeTable() throws Exception {
        int step = 0;
        ArrayList<Situation> ceil0 = new ArrayList();
        ArrayList<GrammarRule> axiomRules = this.grammar.getRules(this.grammar.getAxiom());
        addDefaultRules(ceil0, axiomRules, step);
        boolean wasAdding = true;
        while (wasAdding) {
            ArrayList<Situation> situationWithDotInEnd = this.getSituationWithDotInEnd(ceil0);
            for (int i = 0; i < situationWithDotInEnd.size(); i++) {
                Pair left = situationWithDotInEnd.get(i).getRule().getLeft();
                this.addSituationWithDotAtFrontOf(ceil0, left, step);
            }
            ArrayList<Situation> situationWithDotAtFrontOfNterm = this.getSituationWithDotAtFrontOfNterm(ceil0);
            for (int i = 0; i < situationWithDotAtFrontOfNterm.size(); i++) {
                int posDot = situationWithDotAtFrontOfNterm.get(i).getRule().getPosSymbol(this.dot);
                Pair Nterm = situationWithDotAtFrontOfNterm.get(i).getRule().getPair(posDot + 1);
                ArrayList<GrammarRule> possibleRules = this.grammar.getRules(Nterm);
                addDefaultRules(ceil0, possibleRules, step);
            }
            if (situationWithDotInEnd.size() + situationWithDotAtFrontOfNterm.size() == 0) {
                wasAdding = false;
            }
        }
        this.table.add(ceil0);
        step++;
        while (step <= this.lexems.size()) {
            ArrayList<Situation> ceil = new ArrayList();
            Pair currentLexem = this.lexems.get(step - 1);
            ArrayList<Situation> situationWithDotAtFrontOfCurrentTerm = getSituationWithDotAtFrontOf(this.table.get(step - 1), currentLexem, -1);
            for (int i = 0; i < situationWithDotAtFrontOfCurrentTerm.size(); i++) {
                ceil.add(situationWithDotAtFrontOfCurrentTerm.get(i));
            }
            if (situationWithDotAtFrontOfCurrentTerm.isEmpty()) {
                ArrayList<Pair> expected = getTermAtFrontOfDot(this.table.get(step - 1));
                String expectedString = "{<" + expected.get(0).getType() + " " + expected.get(0).getName() + ">";
                for (int i = 1; i < expected.size(); i++) {
                    expectedString += ", <" + expected.get(i).getType() + " " + expected.get(i).getName() + ">";
                }
                expectedString += "}";
                String error = "В строке " + currentLexem.getNumString() + " получен <" + currentLexem.getType() + " " + currentLexem.getName() + ">, а ожидалось " + expectedString;
                throw new Exception(error);
            }
            wasAdding = true;
            while (wasAdding) {
                ArrayList<Situation> situationWithDotInEnd = this.getSituationWithDotInEnd(ceil);
                for (int i = 0; i < situationWithDotInEnd.size(); i++) {
                    int index = situationWithDotInEnd.get(i).getPos();
                    Pair Nterm = situationWithDotInEnd.get(i).getRule().getLeft();
                    ArrayList<Situation> successMatchSituation = getSituationWithDotAtFrontOf(this.table.get(index), Nterm, -1);
                    for (int j = 0; j < successMatchSituation.size(); j++) {
                        ceil.add(successMatchSituation.get(j));
                    }
                }
                ArrayList<Situation> situationWithDotAtFrontOfNterm = this.getSituationWithDotAtFrontOfNterm(ceil);
                for (int i = 0; i < situationWithDotAtFrontOfNterm.size(); i++) {
                    int posDot = situationWithDotAtFrontOfNterm.get(i).getRule().getPosSymbol(this.dot);
                    Pair Nterm = situationWithDotAtFrontOfNterm.get(i).getRule().getPair(posDot + 1);
                    ArrayList<GrammarRule> possibleRules = this.grammar.getRules(Nterm);
                    addDefaultRules(ceil, possibleRules, step);
                }
                if (situationWithDotInEnd.size() + situationWithDotAtFrontOfNterm.size() == 0) {
                    wasAdding = false;
                }
            }
            table.add(ceil);
            step++;
        }
    }

    //    Добавление ситуации "По умолчанию"
    private void addDefaultRules(ArrayList<Situation> container, ArrayList<GrammarRule> rules, int pos) {
        for (int i = 0; i < rules.size(); i++) {
            GrammarRule ruleWithDot = rules.get(i).getRuleWithDot(0);
            Situation currentSituation = new Situation(ruleWithDot, pos);
            if (!this.isFound(currentSituation, container)) {
                container.add(currentSituation);
            }
        }
    }

    private boolean isFound(Situation situation, ArrayList<Situation> container) {
        boolean isFound = false;
        int i = 0;
        while (!isFound && (i < container.size())) {
            if (situation.equals(container.get(i))) {
                isFound = true;
            } else {
                i++;
            }
        }
        return isFound;
    }

    //    Возвращает ситуации "с точкой на конце"
    private ArrayList<Situation> getSituationWithDotInEnd(ArrayList<Situation> current) {
        ArrayList<Situation> result = new ArrayList();
        for (int i = 0; i < current.size(); i++) {
            if (!current.get(i).getIsProcessedEnd()) {
                ArrayList<Pair> rightSideRule = current.get(i).getRule().getRight();
                if (this.dot.equals(rightSideRule.get(rightSideRule.size() - 1))) {
                    Situation dotInEnd = new Situation(current.get(i).getRule().copy(), current.get(i).getPos());
                    result.add(dotInEnd);
                }
                current.get(i).setIsProcessedEnd(true);
            }
        }
        return result;
    }

    //    Добавляет ситуации "с точкой перед терминалом"
    private void addSituationWithDotAtFrontOf(ArrayList<Situation> current, Pair symbol, int pos) {
        for (int i = 0; i < current.size(); i++) {
            if (!current.get(i).getIsProcessedAtFront()) {
                GrammarRule rule = current.get(i).getRule().copy();
                int posDot = rule.getPosSymbol(this.dot);
                if ((posDot != -1) && (posDot + 1 < rule.getRight().size()) && symbol.equals(rule.getPair(posDot + 1))) {
                    Situation success = new Situation(rule.swapPos(posDot, posDot + 1), pos);
                    current.add(success);
                }
                current.get(i).setIsProcessedAtFront(true);
            }
        }
    }

    //    Возвращает ситуации "с точкой перед терминалом"
    private ArrayList<Situation> getSituationWithDotAtFrontOf(ArrayList<Situation> current, Pair symbol, int pos) {
        ArrayList<Situation> result = new ArrayList();
        for (int i = 0; i < current.size(); i++) {
            GrammarRule rule = current.get(i).getRule().copy();
            int posDot = rule.getPosSymbol(this.dot);
            if ((posDot != -1) && (posDot + 1 < rule.getRight().size()) && symbol.equals(rule.getPair(posDot + 1))) {
                if (pos != -1) {
                    Situation success = new Situation(rule.swapPos(posDot, posDot + 1), pos);
                    result.add(success);
                } else {
                    int currentPos = current.get(i).getPos();
                    Situation success = new Situation(rule.swapPos(posDot, posDot + 1), currentPos);
                    result.add(success);
                }
            }
        }
        return result;
    }

    //    Возвращает ситуации "с точкой перед нетерминалом"
    private ArrayList<Situation> getSituationWithDotAtFrontOfNterm(ArrayList<Situation> current) {
        ArrayList<Situation> result = new ArrayList();
        for (int i = 0; i < current.size(); i++) {
            if (!current.get(i).getIsProcessedAtFront()) {
                GrammarRule rule = current.get(i).getRule().copy();
                int posDot = rule.getPosSymbol(this.dot);
                if ((posDot != -1) && (posDot + 1 < rule.getRight().size()) && this.nterm.equals(rule.getPair(posDot + 1))) {
                    Situation dotAtFrontOfNterm = new Situation(rule, current.get(i).getPos());
                    result.add(dotAtFrontOfNterm);
                }
                current.get(i).setIsProcessedAtFront(true);
            }
        }
        return result;
    }

//    Возвращает термин перед точкой
    private ArrayList<Pair> getTermAtFrontOfDot(ArrayList<Situation> container) {
        ArrayList<Pair> terms = new ArrayList();
        for (int i = 0; i < container.size(); i++) {
            GrammarRule rule = container.get(i).getRule();
            int pos = rule.getPosSymbol(dot);
            if ((pos != -1) && (pos + 1 < rule.getRight().size())) {
                Pair current = container.get(i).getRule().getPair(pos + 1);
                if (!current.getType().equals("nterm") && !isFound(current, terms)) {
                    terms.add(current);
                }
            }
        }
        return terms;
    }

    private boolean isFound(Pair term, ArrayList<Pair> container) {
        boolean isFound = false;
        int i = 0;
        while (!isFound && (i < container.size())) {
            if (term.equals(container.get(i))) {
                isFound = true;
            } else {
                i++;
            }
        }
        return isFound;
    }

    // возвращает правило
    private GrammarRule noDots(GrammarRule rule) {
        GrammarRule newRule = new GrammarRule(rule.getLeft(), null);
        ArrayList<Pair> right = new ArrayList();
        for (int i = 0; i < rule.getRight().size(); i++) {
            if (!rule.getRight().get(i).getType().equals(this.dot.getType())) {
                right.add(rule.getRight().get(i));
            }
        }
        newRule.setRight(right);
        return newRule;
    }

    // функция проверки нахождения в таблице
    private boolean findInTableByColumnAndSit(Situation situation, int column, Pair k) {
        ArrayList<Situation> tbColumn = this.table.get(column);
        boolean result = false;

        for (int i = 0; i < tbColumn.size(); i++) {
            GrammarRule rule = tbColumn.get(i).getRule();
            int posDot = rule.getPosSymbol(dot) + 1;
            if (rule.getLeft().equals(situation.getRule().getLeft())
                    && rule.getRight().size() > posDot
                    && rule.getPair(posDot).equals(k)
                    && tbColumn.get(i).getPos() == situation.getPos()) {
                result = true;
            }
        }
        return result;
    }

    // процедура построения цепочки разбора
    public void parse() {
        int tableSize = this.table.size() - 1;
        int colSize = this.table.get(tableSize).size();
        ArrayList<Situation> tbColumn = this.table.get(tableSize);
        //последняя ситуация последнего столбца
        Situation lex = null;
        for (int i = 0; i < colSize; i++) {
            if (tbColumn.get(i).getRule().getLeft().equals(this.grammar.getAxiom()) && tbColumn.get(i).getRule().getRight().get(tbColumn.get(i).getRule().getRight().size() - 1).equals(this.dot)) {
                lex = tbColumn.get(i);
            }
        }
        procedureR(lex, table.size() - 1);
    }

    // выборка верной ситуации
    private void procedureR(Situation situation, int j) {
        GrammarRule rule = noDots(situation.getRule());
        int rulnum = this.grammar.getRuleIndex(rule);
        this.parseString.add(rulnum);
        int m = rule.getRight().size() - 1;
        int k = m;
        int c = j;
        while (k >= 0) {
            if (!rule.getRight().get(k).getType().equals("nterm")) {
                k--;
                c--;
            } else {
                ArrayList<Situation> sit = new ArrayList();
                ArrayList<Situation> tableSt = this.table.get(c);//Ic table
                Pair left = rule.getRight().get(k).copy();//Xk
                //находим ситуации в Ic
                for (int i = 0; i < tableSt.size(); i++) {

                    if (left.equals(tableSt.get(i).getRule().getLeft()) && tableSt.get(i).getRule().getPosSymbol(dot) == tableSt.get(i).getRule().getRight().size() - 1) {
                        sit.add(tableSt.get(i));
                    }
                }
                //из них выбираем верное
                int r = 0;
                Situation rSituation = null;
                for (int i = 0; i < sit.size(); i++) {
                    if (this.findInTableByColumnAndSit(situation, sit.get(i).getPos(), left) != false) {
                        rSituation = sit.get(i);
                        r = rSituation.getPos();
                    }
                }
                procedureR(rSituation, c);
                k--;
                c = r;
            }
        }
    }

    // процедура построения дерева разбора
    public void buildTree() {
        ArrayList<Integer> numb_seq = this.parseString;
        int last_item = 0;
        GrammarRule root_rule = this.grammar.getRuleByIndex(numb_seq.get(last_item));
        ParseTree tree = new ParseTree(root_rule.getLeft().copy());
        int n = walk(tree.getRoot(), numb_seq, last_item);
        this.parseTree = tree;
        recursive(this.parseTree.getRoot());
    }

    // процедура рекурсивного обхода дерева
    private void recursive(TreeItem elem) {
        if (elem.getChilds().size() != 0) {
            ArrayList<TreeItem> childs = elem.getChilds();
            for (int i = 0; i < childs.size(); i++) {
                recursive(childs.get(i));
            }
        } else {
            elem.getVal().setAllFields(lexems.get(current));
            current++;
        }
    }

    // обход дерева
    private int walk(TreeItem root, ArrayList<Integer> numb_seq, Integer index) {
        int num = index;
        GrammarRule cur_rule = this.grammar.getRuleByIndex(numb_seq.get(index));//получаем текущее правило
        //присваеваем правую часть детям текущего узла
        ArrayList<Pair> childs = cur_rule.getRight();
        root.addChilds(childs);
        int cs = childs.size();
        if (cs > 0) {
            int number = childs.size() - 1;
            TreeItem walker = root.getChilds().get(number);
            // обходит детей текущего узла
            while (walker != root) {
                if (walker.getVal().getType().equals("nterm")) {//если нетерминал
                    num++;
                    int n = walk(walker, numb_seq, num);
                    num = n;
                    if (number != 0) {
                        number--;
                        walker = root.getChilds().get(number);
                    } else {
                        walker = walker.getParent();
                    }

                } else if (number > 0) {
                    number--;
                    walker = root.getChilds().get(number);
                } else {
                    walker = walker.getParent();
                }
            }
        }
        return num;
    }

    //    возвращает дерево после синтаксического разбора
    public ParseTree getTree() {
        return this.parseTree;
    }

//    возвращает цепочку разбора
    public ArrayList<Integer> getParse() {
        return this.parseString;
    }

    //    печать таблицы разбора на экран
    public void printTable() throws UnsupportedEncodingException {
        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        for (int i = 0; i < this.table.size(); i++) {
            ps.println("========  I[" + i + "] ========");
            ArrayList<Situation> situation = this.table.get(i);
            for (int j = 0; j < situation.size(); j++) {
                situation.get(j).print();
                ps.println();
            }
        }
    }
}