# Translator Pascal-CPlusPlus

## Проект данных
1. *src/LexAnalyzer - класс лексического анализатора Pascal*
2. *src/Pair - класс лексемы - пара <name, value>*
3. *src/ParseTree - класс дерева*
4. *src/TreeItem - класс узла дерева разбора*
5. *src/GrammarRule - класс правило грамматики - <Pair left, ArrayList right> === [left -> right]*
6. *src/Situation - класс ситуации алгоритма Эрли <GrammarRule rule, int pos>*
7. *src/GrammarInterface - интерфейс класса грамматик*
8. *src/PascalGrammar implements GrammarInterface - класс грамматики Pascal*
9. *src/CGrammar implements GrammarInterface - класс грамматики C++*
10. *src/SynAnalyzer - класс синтаксического анализатора, основанный на методе Эрли*
11. *src/PossibleOperation - класс возможных операций в Pascal*
12. *src/PossibleOperationRepository - класс контейнер всех возможных операций в Pascal*
13. *src/SemAnalyzer - класс семантического анализатора*
14. *src/Translator - класс транслятор из Pascal в C++*
15. *src/Main - класс приложения транслятора Pascal - C++*
