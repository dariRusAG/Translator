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
        int currentIndexNterm = 0;
        while (wasAdding) {
            ArrayList<Situation> situationWithDotInEnd = this.getSituationWithDotInEnd(ceil0);
            for (int i = 0; i < situationWithDotInEnd.size(); i++) {
                Pair left = situationWithDotInEnd.get(i).getRule().getLeft();
                this.addSituationWithDotAtFrontOf(ceil0, left, step);
            }
            ArrayList<Situation> situationWithDotAtFrontOfNterm = this.getSituationWithDotAtFrontOfNterm(ceil0, currentIndexNterm);
            if (!situationWithDotAtFrontOfNterm.isEmpty()) {
                currentIndexNterm += ceil0.size();
            }
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
    private ArrayList<Situation> getSituationWithDotAtFrontOfNterm(ArrayList<Situation> current, int index) {
        ArrayList<Situation> result = new ArrayList();
        for (int i = index; i < current.size(); i++) {
            GrammarRule rule = current.get(i).getRule();
            int posDot = rule.getPosSymbol(this.dot);
            if ((posDot != -1) && (posDot + 1 < rule.getRight().size()) && this.nterm.equals(rule.getPair(posDot + 1))) {
                result.add(current.get(i));
            }
        }
        return result;
    }
}