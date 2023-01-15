
import java.util.ArrayList;

public class TreeItem {

    private TreeItem parent;
    private Pair val;
    private ArrayList<TreeItem> childs;


    // конструктор класса, атом разбора [nterm/term]
    TreeItem(Pair pair){
        this.val = pair;
        this.childs = new ArrayList();
    }

    // процедура установки родителя узла
    public void setParent(TreeItem par){
        this.parent = par;
    }


    // процедура добавления детей узла
    public void addChilds(ArrayList<Pair> list){
        int childs_numb = list.size();
        for(int i = 0; i < childs_numb; i++){
            TreeItem newChild = new TreeItem(list.get(i));
            newChild.setParent(this);
            this.childs.add(i, newChild);
        }
    }

    // функция, возвращающая детей узла
    public ArrayList<TreeItem> getChilds(){
        return this.childs;
    }

    //  функция, возвращающая родителя узла
    public TreeItem getParent(){
        return this.parent;
    }

    // функция, возвращающая значение узла
    public Pair getVal(){
        return this.val;
    }

//    Перегрузка
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TreeItem)) {
            return false;
        }
        TreeItem c = (TreeItem) o;
        boolean eq = true;
        if (c.getVal().equals(this.getVal())) {
            if (c.getChilds().size() != this.getChilds().size()) {
                eq = false;
            }
            int i = 0;
            while (i < this.childs.size() && eq) {
                eq = this.getChilds().get(i).equals(c.getChilds().get(i));
                i++;
            }
        }
        return eq;
    }
}