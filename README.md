# Translator Pascal-CPlusPlus

## Запуск Докера

```json
docker build - t java-docker 
docker run java-docker
```

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

## Допустимые Pascal лексемы
- Типы данных: integer, real, char, string, boolean
- Арифметические операторы: +, -, *, /
- Логические операторы: >, <, <=, >=, <>, =
- Встроенные функции: abs, sqr, sqrt, exp
- Встроенные процедуры: write, writeln, readln
- Операторы присваивания: :=, +=, -=, *=, /=
- Циклы: for to, for downto, while do, repeat until
- Ветвления: case of else
- Условный оператор: if then else, if then
