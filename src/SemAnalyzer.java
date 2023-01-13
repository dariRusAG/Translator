import java.util.ArrayList;

public class SemAnalyzer {

    private ArrayList<Pair> tableOfName;
    private ParseTree tree;
    private boolean hasError = false;

//    конструктор класса, принимающий на вход дерево разбора
    SemAnalyzer(ParseTree tree) {
        this.tree = tree;
        this.tableOfName = new ArrayList();
    }

//    процедура проверки контекстных условий
    public void makeAnalysis() throws Exception {
        try {
            setTableOfName();
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
                String typeVar = tableOfName.get(tableOfName.size()-1).getContextType();
                String typeExpression = execExpression(item.getChilds());
                if (!typeVar.equals(typeExpression)) {
                    throw new Exception("Недопустимое преобразование типов переменной "
                            + tableOfName.get(tableOfName.size()-1).getName()
                            + " "
                            + typeVar
                            + " к "
                            + typeExpression);
                }
            }
        }
    }

//    процедура проверки правильности преобразования типов данных (в случае var имя: тип := выражение)
    private String execExpression(ArrayList<TreeItem> childs) {
        return "true";
    }

//    процедура заполнения списка имен в var
    private void setListVar(ArrayList<TreeItem> childs) {
        for (int i = 0; i < childs.size(); i++) {
            Pair item = childs.get(i).getVal();
            if (item.getType().equals("id")) {
                tableOfName.add(item.copy());
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

//    функция результата семантического анализа
    public boolean hasError() {
        return this.hasError;
    }
}