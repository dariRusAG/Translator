// Синтаксический анализатор
// Что делает: проверяет синтаксис языка.
// Вход: массив токенов.
// Выход: дерево разбора (или AST).

// Для синтаксического анализа был выбран алгоритм нисходящего разбора с возвратами.

import java.util.ArrayList;

// Класс синтаксического анализатора
public class SynAnalyzer {
    private ArrayList<Pair> lexems;

    SynAnalyzer(ArrayList<Pair> lexems) {
        this.lexems = new ArrayList();
        this.lexems = lexems;
    }

}
