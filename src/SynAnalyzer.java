// Синтаксический анализатор
// Что делает: проверяет синтаксис языка.
// Вход: массив токенов.
// Выход: дерево разбора (или AST).

// Для синтаксического анализа был выбран алгоритм нисходящего разбора с возвратами.

import java.util.ArrayList;

// Класс синтаксического анализатора
public class SynAnalyzer {
    private ArrayList<Pair> lexems;
    private final GrammarInterface grammar;
    private final ArrayList<ArrayList<Situation>> table;
    private final Pair dot = new Pair("$", "");
    private final Pair nterm = new Pair("nterm", "");

//    Конструктор класса
    SynAnalyzer(ArrayList<Pair> lexems, GrammarInterface grammar) {
        this.lexems = lexems;
        this.grammar = grammar;
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


    //граматика.гетрулс.гет(номер правила)
//    вход цепочка: 124125136234413
//берется 3 - номер правила
//граматика.гетрулс.гет(номер правила)
//структура правила - левая часть - массив(правая часть)
//TreeItem (Pair) <- левая часть
//(правая часть.сайз) детей - создать
//каждому ребенку <- элемент правой части правила
//идем по детям справа налево
//	если нетерминал - берем следущее правила и к 3 строке
//	переход к следущему ребенку
//вернуть к родителю и перейти к шагу 8 пока не корень
//30,31,6,4,39,16,14,12,38,35,1,0

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

//    public void printTree(TreeItem root) throws UnsupportedEncodingException {
//        PrintStream ps = new PrintStream(System.out, false, "utf-8");
//        ArrayList<TreeItem> childs = root.getChilds();
//        int number = childs.size()-1;
//        TreeItem walker = root.getChilds().get(number);
//       // обходит детей текущего узла
//        while(walker != root){
//            if(walker.getVal().getType() == "nterm"){//если нетерминал
//                  printTree(walker);
//
//            }else if(number >0){
//                number --;
//                walker = root.getChilds().get(number);
//            } else walker = walker.getParent();
//        }
//        ps.print();
//    }


}
