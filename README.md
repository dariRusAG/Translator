# Translator Pascal-CPlusPlus

## Проект данных
- *src/LexAnalyzer - класс лексического анализатора Pascal*<br>
- *src/Pair - класс лексемы - пара <name, value>*
- *src/ParseTree - класс дерева*
- *src/TreeItem - класс узла дерева разбора*
- *src/GrammarRule - класс правило грамматики - <Pair left, ArrayList right> === [left -> right]*
- *src/Situation - класс ситуации алгоритма Эрли <GrammarRule rule, int pos>*
- *src/GrammarInterface - интерфейс класса грамматик*
- *src/PascalGrammar implements GrammarInterface - класс грамматики Pascal*
- *src/CGrammar implements GrammarInterface - класс грамматики C++*
- *src/SynAnalyzer - класс синтаксического анализатора, основанный на методе Эрли*
- *src/PossibleOperation - класс возможных операций в Pascal*
- *src/PossibleOperationRepository - класс контейнер всех возможных операций в Pascal*
- *src/SemAnalyzer - класс семантического анализатора*
- *src/Translator - класс транслятор из Pascal в C++*
- *src/Main - класс приложения транслятора Pascal - C++*
