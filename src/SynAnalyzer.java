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
    private ArrayList<Pair> lexems;
    private final GrammarInterface grammar;
    private final ArrayList<ArrayList<Situation>> table;
    private ArrayList<Integer> parseString;
    private final Pair dot = new Pair("$", "");
    private final Pair nterm = new Pair("nterm", "");

//    Конструктор класса
    SynAnalyzer(ArrayList<Pair> lexems, GrammarInterface grammar) {
        this.lexems = lexems;
        this.grammar = grammar;
        this.parseString = new ArrayList();
        this.table = new ArrayList(new ArrayList());
    }

//    процедура составления таблицы разбора для метода Эрли
    public void makeTable() {
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
            ArrayList<Situation> situationWithDotAtFrontOfCurrentTerm = getSituationWithDotAtFrontOf(this.table.get(step - 1), this.lexems.get(step - 1), -1);
            for (int i = 0; i < situationWithDotAtFrontOfCurrentTerm.size(); i++) {
                ceil.add(situationWithDotAtFrontOfCurrentTerm.get(i));
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
            boolean isFound = false;
            int j = 0;
            while (!isFound && (j < container.size())) {
                if (currentSituation.equals(container.get(j))) {
                    isFound = true;
                } else {
                    j++;
                }
            }
            if (!isFound) {
                container.add(currentSituation);
            }
        }
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

    // возвращает правило
    private GrammarRule noDots(GrammarRule rule){
        GrammarRule newRule = new GrammarRule(rule.getLeft(), null);
        ArrayList<Pair> right = new ArrayList();
        for(int i = 0; i < rule.getRight().size(); i++){
            if(rule.getRight().get(i).getType() != this.dot.getType()){
                right.add(rule.getRight().get(i));
            }
        }
        newRule.setRight(right);
        return newRule;
    }

    // функция проверки нахождения в таблице
    private boolean findInTableByColumnAndSit(Situation situation, int column, int k){
        ArrayList<Situation> tbColumn = this.table.get(column);
        boolean result = false;
        for(int i = 0; i < tbColumn.size(); i++ ){
            if(tbColumn.get(i).getRule().getLeft().equals(situation.getRule().getLeft()) && tbColumn.get(i).getRule().getRight().get(k).equals(this.dot)){
                result = true;
            }
        }
        return result;
    }
    // процедура построения цепочки разбора
    public void parse(){
        int tableSize = this.table.size() - 1;
        int colSize = this.table.get(tableSize).size() - 1;
        //последняя ситуация последнего столбца
        Situation lex = this.table.get(tableSize).get(colSize);
        procedureR(lex,lexems.size()-1);
    }

    // выборка верной ситуации
    public void procedureR(Situation situation, int j){
        GrammarRule rule = noDots(situation.getRule());
        parseString.add(this.grammar.getRuleIndex(rule));
        int m = rule.getRight().size() - 1 ;
        int k = m;
        int c = j;
        while( k != 0 ){
            if(rule.getRight().get(k).getType() != "nterm"){
                k --;
                c --;
            }else {
                ArrayList<Situation> sit = new ArrayList();
                ArrayList<Situation> tableSt = this.table.get(c);//Ic table
                Pair left = rule.getRight().get(k);//Xk
                //находим ситуации в Ic
                for(int i = 0; i < tableSt.size(); i++ ){
                    if (left.equals(tableSt.get(i).getRule().getLeft())) {
                        sit.add(tableSt.get(i));
                    }
                }
                //из них выбираем верное
                int r = 0;
                Situation rSituation = null;
                for(int i = 0; i < sit.size(); i++ ){
                    if(this.findInTableByColumnAndSit(situation, sit.get(i).getPos(), k) != false){
                        rSituation = sit.get(i);
                        r = rSituation.getPos();
                    }
                }
                procedureR(rSituation, c);
                k --;
                c = r;
            }
        }

    }

    // процедура посроения дерева разбора
    public ParseTree buildTree(ArrayList<Integer> numb_seq){
        int last_item = numb_seq.size() - 1;
        GrammarRule root_rule = this.grammar.getRuleByIndex(numb_seq.get(last_item));
        ParseTree tree = new ParseTree(root_rule.getLeft());
        walk(tree.getRoot(), numb_seq, last_item);
        return tree;
    }


    // обход дерева
    public void walk(TreeItem root, ArrayList<Integer> numb_seq, Integer index ){
        int num = index;
        GrammarRule cur_rule = this.grammar.getRuleByIndex(numb_seq.get(index));//получаем текущее правило
        //присваеваем правую часть детям текущего узла
        ArrayList<Pair> childs = cur_rule.getRight();
        root.addChilds(childs);
        int cs = childs.size();
        if(cs > 0 ){
            int number = childs.size()-1;

            TreeItem walker = root.getChilds().get(number);
            // обходит детей текущего узла
            while(walker != root){
                if(walker.getVal().getType() == "nterm"){//если нетерминал
                    num --;
                    walk(walker, numb_seq, num);
                    if(number != 0){
                        number --;
                        walker = root.getChilds().get(number);
                    }else walker = walker.getParent();

                }else if(number >0){
                    number --;
                    walker = root.getChilds().get(number);
                } else {
                    walker = walker.getParent();
                }
            }
        }
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
