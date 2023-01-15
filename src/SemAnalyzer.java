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
                    throw new Exception("Недопустимое преобразование типов переменной "
                            + tableOfName.get(tableOfName.size() - 1).getName()
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

//    процедура проверки правильности преобразования типов данных (в случае var имя: тип := выражение)
    private String execExpression(ArrayList<TreeItem> childs) throws Exception {
        String parent = childs.get(0).getParent().getVal().getName();
        if ((parent.equals("выражение") || parent.equals("операнд T")) && childs.size() > 1) {
            String operation = childs.get(1).getVal().getName();
            String operand1 = execExpression(childs.get(0).getChilds());
            String operand2 = execExpression(childs.get(2).getChilds());
            return this.repository.getReturnType(operation, operand1, operand2);
        }
        if ((parent.equals("выражение") || parent.equals("операнд T")) && childs.size() == 1) {
            return execExpression(childs.get(0).getChilds());
        }
        if (parent.equals("операнд F") && childs.size() == 1) {
            Pair atom = childs.get(0).getVal();
            return switch (atom.getType()) {
                case "nterm" ->
                        "boolean";
                case "id" ->
                        getTypeOfVariable(atom.getName());
                default ->
                        atom.getType();
            };
        }
        if (parent.equals("операнд F") && childs.size() > 1) {
            if (childs.get(0).getVal().getType().equals("bracket")) {
                return execExpression(childs.get(1).getChilds());
            } else {
                String operation = childs.get(0).getChilds().get(0).getVal().getName();
                String opearand = execExpression(childs.get(2).getChilds());
                return this.repository.getReturnType(operation, opearand);
            }
        }
        return "true";
    }

//    процедура заполнения списка имен в var
    private void setListVar(ArrayList<TreeItem> childs) throws Exception {
        for (int i = 0; i < childs.size(); i++) {
            Pair item = childs.get(i).getVal();
            if (item.getType().equals("id")) {
                if (!isDuplicate(item.getName())) {
                    tableOfName.add(item.copy());
                } else {
                    throw new Exception("Использовано не уникальное имя переменной"
                            + item.getName());
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

//    функция, возвращающая контекстный тип переменной
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
            throw new Exception("Использована неинициализированная переменная "
                    + name);
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
                throw new Exception("Недопустимая длина строки в переменной "
                        + elem.getName()
                        + ". Получена строка длинной "
                        + elem.getContextValue().length()
                        + ", а ожидалась < "
                        + this.MAX_LENGTH_STRING);
            }
        }
    }

// возвращает полное выражение
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

//    функция результата семантического анализа
    public boolean hasError() {
        return this.hasError;
    }
}