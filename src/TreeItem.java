
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


}