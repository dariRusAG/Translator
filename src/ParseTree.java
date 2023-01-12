
public class ParseTree {
    private TreeItem root;

    // конструктор дерева
    ParseTree(Pair val){
        this.root  = new TreeItem(val);
        this.root.setParent(null);
    }

    // возвращает TreeItem - корень дерева
    public TreeItem getRoot(){
        return root;
    }
}
