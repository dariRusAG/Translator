// Семантический анализатор
// Что делает: проверяет семантику языка программирования.
// Вход: дерево разбора.
// Выход: результат семантического анализа.

import java.util.ArrayList;

public class SemAnalyzer {

    private ArrayList<Pair> tableOfName;
    private ParseTree tree;
    private boolean hasError = false;
    private PossibleOperationRepository repository = new PossibleOperationRepository();
    private final int COUNT_OF_VARIABLE = 255;
    private final int MAX_LENGTH_STRING = 255;

//    конструктор класса, принимающий на вход дерево разбора
    SemAnalyzer(ParseTree tree) {
        this.tree = tree;
        this.tableOfName = new ArrayList();
    }

//    процедура проверки контекстных условий
    public void makeAnalysis() throws Exception {
        try {
            setTableOfName();
            if (this.tableOfName.size() > this.COUNT_OF_VARIABLE) {
                throw new Exception("Недопустимое количество переменных. Максимальное возможное число - 255");
            }
            validationString();
            ArrayList<Pair> scope = new ArrayList();
            validationBody(this.tree.getRoot().getChilds().get(1).getChilds().get(1).getChilds(), scope);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

//    процедура проверки корректности дерева
    private void setTableOfName() throws Exception {
        TreeItem blockOfVar = this.tree.getRoot().getChilds().get(0);
        if (!blockOfVar.getVal().getName().equals("раздел описания")) {
            throw new Exception("Некорректное дерево");
        } else {
            walkBlockVar(blockOfVar.getChilds());
        }
    }

//    процедура проверки корректности данных в блоке var
    private void walkBlockVar(ArrayList<TreeItem> blockOfVar) throws Exception {
        int start = 0;
        int end = 0;
        for (int i = 0; i < blockOfVar.size(); i++) {
            TreeItem item = blockOfVar.get(i);
            if (item.getVal().getName().equals("список имен")) {
                start = tableOfName.size();
                setListVar(item.getChilds());
                end = tableOfName.size();
            }
            if (item.getVal().getType().equals("id")) {
                start = tableOfName.size();
                tableOfName.add(item.getVal().copy());
                end = tableOfName.size();
            }
            if (item.getVal().getName().equals("тип")) {
                String type = item.getChilds().get(0).getVal().getName();
                setTypes(start, end, type);
            }
            if (item.getVal().getName().equals("раздел описания")) {
                walkBlockVar(item.getChilds());
            }
            if (item.getVal().getName().equals("выражение")) {
                String typeVar = tableOfName.get(tableOfName.size() - 1).getContextType();
                String typeExpression = execExpression(item.getChilds());
                if (!((typeVar.equals("real") && typeExpression.equals("integer"))
                        || (typeVar.equals("string") && typeExpression.equals("char"))
                        || typeVar.equals(typeExpression))) {
                    throw new Exception("Недопустимое преобразование типов переменной \'"
                            + tableOfName.get(tableOfName.size() - 1).getName()
                            + "\' в строке "
                            + tableOfName.get(tableOfName.size() - 1).getNumString()
                            + " - "
                            + typeVar
                            + " к "
                            + typeExpression);
                } else {
                    tableOfName.get(tableOfName.size() - 1).setContextValue(collectExpression(item.getChilds()));
                }
            }
        }
    }

//    проверяет правильность преобразования типов данных (в случае var имя: тип := выражение), возвращает returntype операции
    private String execExpression(ArrayList<TreeItem> childs) throws Exception {
        String parent = childs.get(0).getParent().getVal().getName();
        if ((parent.equals("выражение") || parent.equals("операнд T")) && childs.size() > 1) {
            String operation = childs.get(1).getVal().getName();
            String operand1 = execExpression(childs.get(0).getChilds());
            String operand2 = execExpression(childs.get(2).getChilds());
            try {
                return this.repository.getReturnType(operation, operand1, operand2);
            } catch (Exception e) {
                throw new Exception(e.getMessage()
                        + " в строке "
                        + childs.get(1).getVal().getNumString());
            }
        }
        if ((parent.equals("выражение") || parent.equals("операнд T")) && childs.size() == 1) {
            return execExpression(childs.get(0).getChilds());
        }
        if (parent.equals("операнд F") && childs.size() == 1) {
            Pair atom = childs.get(0).getVal();
            try {
                return switch (atom.getType()) {
                    case "nterm" ->
                            "boolean";
                    case "id" ->
                            getTypeOfVariable(atom.getName());
                    default ->
                            atom.getType();
                };
            } catch (Exception e) {
                throw new Exception(e.getMessage()
                        + " в строке "
                        + atom.getNumString());
            }
        }
        if (parent.equals("операнд F") && childs.size() > 1) {
            if (childs.get(0).getVal().getType().equals("bracket")) {
                return execExpression(childs.get(1).getChilds());
            } else {
                String operation = childs.get(0).getChilds().get(0).getVal().getName();
                String opearand = execExpression(childs.get(2).getChilds());
                try {
                    return this.repository.getReturnType(operation, opearand);
                } catch (Exception e) {
                    throw new Exception(e.getMessage()
                            + " в строке "
                            + childs.get(0).getChilds().get(0).getVal().getNumString());
                }
            }
        }
        return "true";
    }

//    процедура заполнения списка имен в var, проверка на уникальность имени переменной
    private void setListVar(ArrayList<TreeItem> childs) throws Exception {
        for (int i = 0; i < childs.size(); i++) {
            Pair item = childs.get(i).getVal();
            if (item.getType().equals("id")) {
                if (!isDuplicate(item.getName())) {
                    tableOfName.add(item.copy());
                } else {
                    throw new Exception("Использовано не уникальное имя переменной \'"
                            + item.getName()
                            + "\' в строке "
                            + item.getNumString());
                }
            }
            if (item.getName().equals("список имен")) {
                setListVar(childs.get(i).getChilds());
            }
        }
    }

//    процедура установки контекстного типа для лексем в tableOfName
    private void setTypes(int start, int end, String type) {
        for (int i = start; i < end; i++) {
            tableOfName.get(i).setContextType(type);
        }
    }

//    функция, возвращающая контекстный тип переменной, проверка на то была ли переменная объявлена/проинциализирована
    private String getTypeOfVariable(String name) throws Exception {
        String type = "";
        int i = 0;
        while (i < this.tableOfName.size() && type.isEmpty()) {
            if (this.tableOfName.get(i).getName().equals(name)) {
                type = this.tableOfName.get(i).getContextType();
            } else {
                i++;
            }
        }
        if (type.isEmpty()) {
            throw new Exception("Использована необъявленная переменная \'"
                    + name
                    + "\'"
            );
        }
        if (this.tableOfName.get(i).getContextValue().isEmpty()) {
            throw new Exception("Использована непроинициализированная переменная \'"
                    + name
                    + "\'");
        }
        return type;
    }

//    проверка на дублирование имени переменной
    private boolean isDuplicate(String name) {
        boolean isFind = false;
        int i = 0;
        while (i < this.tableOfName.size() && !isFind) {
            if (this.tableOfName.get(i).getName().equals(name)) {
                isFind = true;
            } else {
                i++;
            }
        }
        return isFind;
    }

//    проверка длины для переменной типа string
    private void validationString() throws Exception {
        for (Pair elem : this.tableOfName) {
            if (elem.getContextType().equals("string") && elem.getContextValue().length() > this.MAX_LENGTH_STRING) {
                throw new Exception("Недопустимая длина строки в переменной \'"
                        + elem.getName()
                        + "\' в строке "
                        + elem.getNumString()
                        + ". Получена строка длинной "
                        + elem.getContextValue().length()
                        + ", а ожидалась < "
                        + this.MAX_LENGTH_STRING);
            }
        }
    }

//    возвращает полное выражение
    private String collectExpression(ArrayList<TreeItem> childs) {
        String result = "";
        int i = 0;
        while (i < childs.size()) {
            TreeItem current = childs.get(i);
            if (current.getChilds().size() > 0) {
                result += collectExpression(current.getChilds());
            } else {
                result += current.getVal().getName();
            }
            i++;
        }
        return result;
    }

//    проверка тела программы на использование необъявленной переменной и недопустимое преобразование типов
    private void validationBody(ArrayList<TreeItem> childs, ArrayList<Pair> scope) throws Exception {
        int i = 0;
        while (i < childs.size()) {
            TreeItem current = childs.get(i);
            if (current.getChilds().isEmpty()) {
                if (current.getVal().getName().equals("readln")) {
                    setUserValue(childs.get(i + 2).getChilds());
                    i += 2;
                }
                switch (current.getVal().getType()) {
                    case "id" -> {
                        if (isInit(current.getVal())) {
                            scope.add(findByName(current.getVal().getName()));
                            scope.get(scope.size() - 1).setNumString(current.getVal().getNumString());
                        } else {
                            throw new Exception("Использована необъявленная переменная \'"
                                    + current.getVal().getName()
                                    + "\' в строке "
                                    + current.getVal().getNumString());
                        }
                    }
                    case "assignment" -> {
                        String typeVar = scope.get(scope.size() - 1).getContextType();
                        i++;
                        String typeExpression = "";
                        if (current.getParent().getVal().getName().equals("знак присваивания")) {
                            typeExpression = execExpression(current.getParent().getParent().getChilds().get(2).getChilds());
                        } else {
                            typeExpression = current.getParent().getChilds().get(3).getVal().getType();
                        }
                        if (current.getVal().getName().equals(":=")) {
                            if (!((typeVar.equals("real") && typeExpression.equals("integer"))
                                    || (typeVar.equals("string") && typeExpression.equals("char"))
                                    || typeVar.equals(typeExpression))) {
                                throw new Exception("Недопустимое преобразование типов переменной \'"
                                        + scope.get(scope.size() - 1).getName()
                                        + "\' в строке "
                                        + scope.get(scope.size() - 1).getNumString()
                                        + " - "
                                        + typeVar
                                        + " к "
                                        + typeExpression);
                            } else {
                                String contextValue = "";
                                if (current.getParent().getVal().getName().equals("знак присваивания")) {
                                    contextValue = collectExpression(current.getParent().getParent().getChilds().get(2).getChilds());
                                } else {
                                    contextValue = current.getParent().getChilds().get(3).getVal().getName();
                                }
                                scope.get(scope.size() - 1).setContextValue(contextValue);
                                findByName(scope.get(scope.size() - 1).getName()).setContextValue(contextValue);
                            }
                        } else {
                            String operation = current.getVal().getName();
                            try {
                                typeExpression = this.repository.getReturnType(operation, typeVar, typeExpression);
                            } catch (Exception e) {
                                throw new Exception(e.getMessage()
                                        + " в строке "
                                        + current.getVal().getNumString());
                            }
                            if (!((typeVar.equals("real") && typeExpression.equals("integer"))
                                    || (typeVar.equals("string") && typeExpression.equals("char"))
                                    || typeVar.equals(typeExpression))) {
                                throw new Exception("Недопустимое преобразование типов переменной \'"
                                        + scope.get(scope.size() - 1).getName()
                                        + "\' в строке "
                                        + scope.get(scope.size() - 1).getNumString()
                                        + " - "
                                        + typeVar
                                        + " к "
                                        + typeExpression);
                            } else {
                                String contextValue = scope.get(scope.size() - 1).getContextValue()
                                        + current.getVal().getName().substring(0, 1)
                                        + collectExpression(current.getParent().getParent().getChilds().get(2).getChilds());
                                scope.get(scope.size() - 1).setContextValue(contextValue);
                                findByName(scope.get(scope.size() - 1).getName()).setContextValue(contextValue);
                            }
                        }
                    }
                    case "compare" -> {
                        String operation = current.getVal().getName();
                        String operandType1 = execExpression(childs.get(i - 1).getChilds());
                        String operandType2 = execExpression(childs.get(i + 1).getChilds());
                        try {
                            this.repository.getReturnType(operation, operandType1, operandType2);
                        } catch (Exception e) {
                            throw new Exception(e.getMessage()
                                    + " в строке "
                                    + current.getVal().getNumString());
                        }
                        i++;
                    }
                }
            } else {
                validationBody(current.getChilds(), scope);
            }
            i++;
        }
    }

//    возвращает ответ на "инциализирована ли переменная в коде"
    private boolean isInit(Pair variable) {
        boolean isFind = false;
        int i = 0;
        while (i < this.tableOfName.size() && !isFind) {
            if (this.tableOfName.get(i).getName().equals(variable.getName())) {
                isFind = true;
                this.tableOfName.get(i).setInUse(true);
            } else {
                i++;
            }
        }
        return isFind;
    }

//    возвращает лексему по имени
    private Pair findByName(String name) {
        boolean isFind = false;
        int i = 0;
        Pair result = null;
        while (i < this.tableOfName.size() && !isFind) {
            if (this.tableOfName.get(i).getName().equals(name)) {
                result = this.tableOfName.get(i);
                isFind = true;
            } else {
                i++;
            }
        }
        return result;
    }

//    устанавливает лексеме контекстное значение user_value (значение будет введено пользователем)
    private void setUserValue(ArrayList<TreeItem> childs) {
        int i = 0;
        while (i < childs.size()) {
            TreeItem current = childs.get(i);
            switch (current.getVal().getType()) {
                case "id" ->
                        findByName(current.getVal().getName()).setContextValue("user_value");
                default ->
                        setUserValue(current.getChilds());
            }
            i++;
        }
    }

//    функция результата семантического анализа
    public boolean hasError() {
        return this.hasError;
    }
}