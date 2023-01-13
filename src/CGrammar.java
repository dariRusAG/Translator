import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

// класс грамматики C++
public class CGrammar implements GrammarInterface {

    private final Pair axiom;
    private final ArrayList<GrammarRule> rules;

// конструктор грамматики C++
    CGrammar(Pair axiom) {
        this.axiom = axiom;
        this.rules = new ArrayList();
        fillRules();
    }

// перегрузка процедуры наполнение грамматики правилами
    @Override
    public void fillRules() {
        ArrayList<Pair> right0 = new ArrayList();
        right0.add(new Pair("keyword", "int"));
        right0.add(new Pair("keyword", "main"));
        right0.add(new Pair("bracket", "("));
        right0.add(new Pair("bracket", ")"));
        right0.add(new Pair("nterm", "составной оператор"));
        GrammarRule r0 = new GrammarRule(new Pair("nterm", "программа"), right0);
        this.rules.add(r0);

        ArrayList<Pair> right1 = new ArrayList();
        right1.add(new Pair("bracket", "{"));
        right1.add(new Pair("nterm", "операторы"));
        right1.add(new Pair("bracket", "}"));
        GrammarRule r1 = new GrammarRule(new Pair("nterm", "составной оператор"), right1);
        this.rules.add(r1);

        ArrayList<Pair> right2 = new ArrayList();
        right2.add(new Pair("nterm", "тип"));
        right2.add(new Pair("nterm", "список имен"));
        right2.add(new Pair("separator", ";"));
        right2.add(new Pair("nterm", "раздел описания"));
        GrammarRule r2 = new GrammarRule(new Pair("nterm", "раздел описания"), right2);
        this.rules.add(r2);

        ArrayList<Pair> right3 = new ArrayList();
        right3.add(new Pair("nterm", "тип"));
        right3.add(new Pair("id", ""));
        right3.add(new Pair("assignment", "="));
        right3.add(new Pair("nterm", "выражение"));
        right3.add(new Pair("separator", ";"));
        right3.add(new Pair("nterm", "раздел описания"));
        GrammarRule r3 = new GrammarRule(new Pair("nterm", "раздел описания"), right3);
        this.rules.add(r3);

        ArrayList<Pair> right4 = new ArrayList();
        right4.add(new Pair("nterm", "тип"));
        right4.add(new Pair("nterm", "список имен"));
        right4.add(new Pair("separator", ";"));
        GrammarRule r4 = new GrammarRule(new Pair("nterm", "раздел описания"), right4);
        this.rules.add(r4);

        ArrayList<Pair> right5 = new ArrayList();
        right5.add(new Pair("nterm", "тип"));
        right5.add(new Pair("id", ""));
        right5.add(new Pair("assignment", "="));
        right5.add(new Pair("nterm", "выражение"));
        right5.add(new Pair("separator", ";"));
        GrammarRule r5 = new GrammarRule(new Pair("nterm", "раздел описания"), right5);
        this.rules.add(r5);

        ArrayList<Pair> right6 = new ArrayList();
        right6.add(new Pair("keyword", "int"));
        GrammarRule r6 = new GrammarRule(new Pair("nterm", "тип"), right6);
        this.rules.add(r6);

        ArrayList<Pair> right7 = new ArrayList();
        right7.add(new Pair("keyword", "float"));
        GrammarRule r7 = new GrammarRule(new Pair("nterm", "тип"), right7);
        this.rules.add(r7);

        ArrayList<Pair> right8 = new ArrayList();
        right8.add(new Pair("keyword", "bool"));
        GrammarRule r8 = new GrammarRule(new Pair("nterm", "тип"), right8);
        this.rules.add(r8);

        ArrayList<Pair> right9 = new ArrayList();
        right9.add(new Pair("keyword", "char"));
        GrammarRule r9 = new GrammarRule(new Pair("nterm", "тип"), right9);
        this.rules.add(r9);

        ArrayList<Pair> right10 = new ArrayList();
        right10.add(new Pair("keyword", "string"));
        GrammarRule r10 = new GrammarRule(new Pair("nterm", "тип"), right10);
        this.rules.add(r10);

        ArrayList<Pair> right11 = new ArrayList();
        right11.add(new Pair("nterm", "выражение"));
        right11.add(new Pair("plus operator", ""));
        right11.add(new Pair("nterm", "операнд T"));
        GrammarRule r11 = new GrammarRule(new Pair("nterm", "выражение"), right11);
        this.rules.add(r11);

        ArrayList<Pair> right12 = new ArrayList();
        right12.add(new Pair("nterm", "операнд T"));
        GrammarRule r12 = new GrammarRule(new Pair("nterm", "выражение"), right12);
        this.rules.add(r12);

        ArrayList<Pair> right13 = new ArrayList();
        right13.add(new Pair("nterm", "операнд T"));
        right13.add(new Pair("mult operator", ""));
        right13.add(new Pair("nterm", "операнд F"));
        GrammarRule r13 = new GrammarRule(new Pair("nterm", "операнд T"), right13);
        this.rules.add(r13);

        ArrayList<Pair> right14 = new ArrayList();
        right14.add(new Pair("nterm", "операнд F"));
        GrammarRule r14 = new GrammarRule(new Pair("nterm", "операнд T"), right14);
        this.rules.add(r14);

        ArrayList<Pair> right15 = new ArrayList();
        right15.add(new Pair("bracket", "("));
        right15.add(new Pair("nterm", "выражение"));
        right15.add(new Pair("bracket", ")"));
        GrammarRule r15 = new GrammarRule(new Pair("nterm", "операнд F"), right15);
        this.rules.add(r15);

        ArrayList<Pair> right16 = new ArrayList();
        right16.add(new Pair("id", ""));
        GrammarRule r16 = new GrammarRule(new Pair("nterm", "операнд F"), right16);
        this.rules.add(r16);

        ArrayList<Pair> right17 = new ArrayList();
        right17.add(new Pair("integer", ""));
        GrammarRule r17 = new GrammarRule(new Pair("nterm", "операнд F"), right17);
        this.rules.add(r17);

        ArrayList<Pair> right18 = new ArrayList();
        right18.add(new Pair("real", ""));
        GrammarRule r18 = new GrammarRule(new Pair("nterm", "операнд F"), right18);
        this.rules.add(r18);

        ArrayList<Pair> right19 = new ArrayList();
        right19.add(new Pair("char", ""));
        GrammarRule r19 = new GrammarRule(new Pair("nterm", "операнд F"), right19);
        this.rules.add(r19);

        ArrayList<Pair> right20 = new ArrayList();
        right20.add(new Pair("string", ""));
        GrammarRule r20 = new GrammarRule(new Pair("nterm", "операнд F"), right20);
        this.rules.add(r20);

        ArrayList<Pair> right21 = new ArrayList();
        right21.add(new Pair("nterm", "имя функции"));
        right21.add(new Pair("bracket", "("));
        right21.add(new Pair("nterm", "выражение"));
        right21.add(new Pair("bracket", ")"));
        GrammarRule r21 = new GrammarRule(new Pair("nterm", "операнд F"), right21);
        this.rules.add(r21);

        ArrayList<Pair> right22 = new ArrayList();
        right22.add(new Pair("nterm", "булево"));
        GrammarRule r22 = new GrammarRule(new Pair("nterm", "операнд F"), right22);
        this.rules.add(r22);

        ArrayList<Pair> right23 = new ArrayList();
        right23.add(new Pair("keyword", "pow"));
        GrammarRule r23 = new GrammarRule(new Pair("nterm", "имя функции"), right23);
        this.rules.add(r23);

        ArrayList<Pair> right24 = new ArrayList();
        right24.add(new Pair("keyword", "sqrt"));
        GrammarRule r24 = new GrammarRule(new Pair("nterm", "имя функции"), right24);
        this.rules.add(r24);

        ArrayList<Pair> right25 = new ArrayList();
        right25.add(new Pair("keyword", "abs"));
        GrammarRule r25 = new GrammarRule(new Pair("nterm", "имя функции"), right25);
        this.rules.add(r25);

        ArrayList<Pair> right26 = new ArrayList();
        right26.add(new Pair("keyword", "exp"));
        GrammarRule r26 = new GrammarRule(new Pair("nterm", "имя функции"), right26);
        this.rules.add(r26);

        ArrayList<Pair> right27 = new ArrayList();
        right27.add(new Pair("id", ""));
        GrammarRule r27 = new GrammarRule(new Pair("nterm", "список имен"), right27);
        this.rules.add(r27);

        ArrayList<Pair> right28 = new ArrayList();
        right28.add(new Pair("id", ""));
        right28.add(new Pair("separator", ","));
        right28.add(new Pair("nterm", "список имен"));
        GrammarRule r28 = new GrammarRule(new Pair("nterm", "список имен"), right28);
        this.rules.add(r28);

        ArrayList<Pair> right29 = new ArrayList();
        right29.add(new Pair("nterm", "присваивание"));
        right29.add(new Pair("nterm", "операторы"));
        GrammarRule r29 = new GrammarRule(new Pair("nterm", "операторы"), right29);
        this.rules.add(r29);

        ArrayList<Pair> right30 = new ArrayList();
        right30.add(new Pair("nterm", "цикл"));
        right30.add(new Pair("nterm", "операторы"));
        GrammarRule r30 = new GrammarRule(new Pair("nterm", "операторы"), right30);
        this.rules.add(r30);

        ArrayList<Pair> right31 = new ArrayList();
        right31.add(new Pair("nterm", "ветвление"));
        right31.add(new Pair("nterm", "операторы"));
        GrammarRule r31 = new GrammarRule(new Pair("nterm", "операторы"), right31);
        this.rules.add(r31);

        ArrayList<Pair> right32 = new ArrayList();
        right32.add(new Pair("nterm", "присваивание"));
        GrammarRule r32 = new GrammarRule(new Pair("nterm", "операторы"), right32);
        this.rules.add(r32);

        ArrayList<Pair> right33 = new ArrayList();
        right33.add(new Pair("nterm", "цикл"));
        GrammarRule r33 = new GrammarRule(new Pair("nterm", "операторы"), right33);
        this.rules.add(r33);

        ArrayList<Pair> right34 = new ArrayList();
        right34.add(new Pair("nterm", "ветвление"));
        GrammarRule r34 = new GrammarRule(new Pair("nterm", "операторы"), right34);
        this.rules.add(r34);

        ArrayList<Pair> right35 = new ArrayList();
        right35.add(new Pair("id", ""));
        right35.add(new Pair("nterm", "знак присваивания"));
        right35.add(new Pair("nterm", "выражение"));
        right35.add(new Pair("separator", ";"));
        GrammarRule r35 = new GrammarRule(new Pair("nterm", "присваивание"), right35);
        this.rules.add(r35);

        ArrayList<Pair> right36 = new ArrayList();
        right36.add(new Pair("assignment", "="));
        GrammarRule r36 = new GrammarRule(new Pair("nterm", "знак присваивания"), right36);
        this.rules.add(r36);

        ArrayList<Pair> right37 = new ArrayList();
        right37.add(new Pair("assignment", "+="));
        GrammarRule r37 = new GrammarRule(new Pair("nterm", "знак присваивания"), right37);
        this.rules.add(r37);

        ArrayList<Pair> right38 = new ArrayList();
        right38.add(new Pair("assignment", "-="));
        GrammarRule r38 = new GrammarRule(new Pair("nterm", "знак присваивания"), right38);
        this.rules.add(r38);

        ArrayList<Pair> right39 = new ArrayList();
        right39.add(new Pair("assignment", "/="));
        GrammarRule r39= new GrammarRule(new Pair("nterm", "знак присваивания"), right39);
        this.rules.add(r39);

        ArrayList<Pair> right40 = new ArrayList();
        right40.add(new Pair("assignment", "*="));
        GrammarRule r40 = new GrammarRule(new Pair("nterm", "знак присваивания"), right40);
        this.rules.add(r40);

        ArrayList<Pair> right41 = new ArrayList();
        right41.add(new Pair("nterm", "цикл for"));
        GrammarRule r41 = new GrammarRule(new Pair("nterm", "цикл"), right41);
        this.rules.add(r41);

        ArrayList<Pair> right42 = new ArrayList();
        right42.add(new Pair("nterm", "цикл while"));
        GrammarRule r42 = new GrammarRule(new Pair("nterm", "цикл"), right42);
        this.rules.add(r42);

        ArrayList<Pair> right43 = new ArrayList();
        right43.add(new Pair("nterm", "цикл repeat"));
        GrammarRule r43 = new GrammarRule(new Pair("nterm", "цикл"), right43);
        this.rules.add(r43);

        ArrayList<Pair> right44 = new ArrayList();
        right44.add(new Pair("bracket", "("));
        right44.add(new Pair("keyword", "for"));
        right44.add(new Pair("id", ""));
        right44.add(new Pair("assignment", "="));
        right44.add(new Pair("integer", ""));
        right44.add(new Pair("separator", ";"));
        right44.add(new Pair("id", ""));
        right44.add(new Pair("compare", "<="));
        right44.add(new Pair("integer", ""));
        right44.add(new Pair("separator", ";"));
        right44.add(new Pair("id", ""));
        right44.add(new Pair("keyword", "++"));
        right44.add(new Pair("bracket", ")"));
        right44.add(new Pair("nterm", "составной оператор"));
        GrammarRule r44 = new GrammarRule(new Pair("nterm", "цикл for"), right44);
        this.rules.add(r44);

        ArrayList<Pair> right45 = new ArrayList();
        right45.add(new Pair("bracket", "("));
        right45.add(new Pair("keyword", "for"));
        right45.add(new Pair("id", ""));
        right45.add(new Pair("assignment", "="));
        right45.add(new Pair("integer", ""));
        right45.add(new Pair("separator", ";"));
        right45.add(new Pair("id", ""));
        right45.add(new Pair("compare", ">="));
        right45.add(new Pair("integer", ""));
        right45.add(new Pair("separator", ";"));
        right45.add(new Pair("id", ""));
        right45.add(new Pair("keyword", "--"));
        right45.add(new Pair("bracket", ")"));
        right45.add(new Pair("nterm", "составной оператор"));
        GrammarRule r45 = new GrammarRule(new Pair("nterm", "цикл for"), right45);
        this.rules.add(r45);

        ArrayList<Pair> right46 = new ArrayList();
        right46.add(new Pair("keyword", "while"));
        right46.add(new Pair("bracket", "("));
        right46.add(new Pair("nterm", "булево выражение"));
        right46.add(new Pair("bracket", ")"));
        right46.add(new Pair("nterm", "составной оператор"));
        GrammarRule r46 = new GrammarRule(new Pair("nterm", "цикл while"), right46);
        this.rules.add(r46);

        ArrayList<Pair> right47 = new ArrayList();
        right47.add(new Pair("nterm", "выражение"));
        right47.add(new Pair("compare", ""));
        right47.add(new Pair("nterm", "выражение"));
        GrammarRule r47 = new GrammarRule(new Pair("nterm", "булево выражение"), right47);
        this.rules.add(r47);

        ArrayList<Pair> right48 = new ArrayList();
        right48.add(new Pair("nterm", "булево"));
        GrammarRule r48 = new GrammarRule(new Pair("nterm", "булево выражение"), right48);
        this.rules.add(r48);

        ArrayList<Pair> right49 = new ArrayList();
        right49.add(new Pair("keyword", "true"));
        GrammarRule r49 = new GrammarRule(new Pair("nterm", "булево"), right49);
        this.rules.add(r49);

        ArrayList<Pair> right50 = new ArrayList();
        right50.add(new Pair("keyword", "false"));
        GrammarRule r50 = new GrammarRule(new Pair("nterm", "булево"), right50);
        this.rules.add(r50);

        ArrayList<Pair> right51 = new ArrayList();
        right51.add(new Pair("keyword", "do"));
        right51.add(new Pair("nterm", "составной оператор"));
        right51.add(new Pair("keyword", "while"));
        right51.add(new Pair("bracket", "("));
        right51.add(new Pair("nterm", "булево выражение"));
        right51.add(new Pair("bracket", ")"));
        right51.add(new Pair("separator", ";"));
        GrammarRule r51 = new GrammarRule(new Pair("nterm", "цикл repeat"), right51);
        this.rules.add(r51);

        ArrayList<Pair> right52 = new ArrayList();
        right52.add(new Pair("nterm", "ветвление if"));
        GrammarRule r52 = new GrammarRule(new Pair("nterm", "ветвление"), right52);
        this.rules.add(r52);

        ArrayList<Pair> right53 = new ArrayList();
        right53.add(new Pair("nterm", "ветвление case"));
        GrammarRule r53 = new GrammarRule(new Pair("nterm", "ветвление"), right53);
        this.rules.add(r53);

        ArrayList<Pair> right54 = new ArrayList();
        right54.add(new Pair("keyword", "if"));
        right54.add(new Pair("bracket", "("));
        right54.add(new Pair("nterm", "булево выражение"));
        right54.add(new Pair("bracket", ")"));
        right54.add(new Pair("nterm", "составной оператор"));
        GrammarRule r54 = new GrammarRule(new Pair("nterm", "ветвление if"), right54);
        this.rules.add(r54);

        ArrayList<Pair> right55 = new ArrayList();
        right55.add(new Pair("keyword", "if"));
        right55.add(new Pair("bracket", "("));
        right55.add(new Pair("nterm", "булево выражение"));
        right55.add(new Pair("bracket", ")"));
        right55.add(new Pair("nterm", "составной оператор"));
        right55.add(new Pair("keyword", "else"));
        right55.add(new Pair("nterm", "составной оператор"));
        GrammarRule r55 = new GrammarRule(new Pair("nterm", "ветвление if"), right55);
        this.rules.add(r55);

        ArrayList<Pair> right56 = new ArrayList();
        right56.add(new Pair("keyword", "switch"));
        right56.add(new Pair("bracket", "("));
        right56.add(new Pair("id", ""));
        right56.add(new Pair("bracket", ")"));
        right56.add(new Pair("bracket", "{"));
        right56.add(new Pair("nterm", "переключатель"));
        right56.add(new Pair("keyword", "default"));
        right56.add(new Pair("separator", ":"));
        right56.add(new Pair("nterm", "составной оператор"));
        right56.add(new Pair("bracket", "}"));
        GrammarRule r56 = new GrammarRule(new Pair("nterm", "ветвление case"), right56);
        this.rules.add(r56);

        ArrayList<Pair> right57 = new ArrayList();
        right57.add(new Pair("keyword", "case"));
        right57.add(new Pair("nterm", "выбор"));
        right57.add(new Pair("setarator", ":"));
        right57.add(new Pair("nterm", "составной оператор"));
        GrammarRule r57 = new GrammarRule(new Pair("nterm", "переключатель"), right57);
        this.rules.add(r57);

        ArrayList<Pair> right58 = new ArrayList();
        right58.add(new Pair("keyword", "case"));
        right58.add(new Pair("nterm", "выбор"));
        right58.add(new Pair("setarator", ":"));
        right58.add(new Pair("nterm", "составной оператор"));
        right58.add(new Pair("nterm", "переключатель"));
        GrammarRule r58 = new GrammarRule(new Pair("nterm", "переключатель"), right58);
        this.rules.add(r58);

        ArrayList<Pair> right59 = new ArrayList();
        right59.add(new Pair("char", ""));
        GrammarRule r59 = new GrammarRule(new Pair("nterm", "выбор"), right59);
        this.rules.add(r59);

        ArrayList<Pair> right60 = new ArrayList();
        right60.add(new Pair("string", ""));
        GrammarRule r60 = new GrammarRule(new Pair("nterm", "выбор"), right60);
        this.rules.add(r60);

        ArrayList<Pair> right61 = new ArrayList();
        right61.add(new Pair("integer", ""));
        GrammarRule r61 = new GrammarRule(new Pair("nterm", "выбор"), right61);
        this.rules.add(r61);

        ArrayList<Pair> right62 = new ArrayList();
        right62.add(new Pair("real", ""));
        GrammarRule r62 = new GrammarRule(new Pair("nterm", "выбор"), right62);
        this.rules.add(r62);

        ArrayList<Pair> right63 = new ArrayList();
        right63.add(new Pair("nterm", "булево"));
        GrammarRule r63 = new GrammarRule(new Pair("nterm", "выбор"), right63);
        this.rules.add(r63);

        ArrayList<Pair> right64= new ArrayList();
        right64.add(new Pair("keyword", "cout"));
        right64.add(new Pair("keyword", "<<"));
        right64.add(new Pair("nterm","список имен"));
        right64.add(new Pair("separator",";"));
        GrammarRule r64 = new GrammarRule(new Pair("nterm", "ввод/вывод"), right64);
        this.rules.add(r64);

        ArrayList<Pair> right65 = new ArrayList();
        right65.add(new Pair("keyword", "cout"));
        right65.add(new Pair("keyword", "<<"));
        right65.add(new Pair("nterm","список имен"));
        right65.add(new Pair("keyword", "<<"));
        right65.add(new Pair("string", "\"\\n\""));
        right65.add(new Pair("separator",";"));
        GrammarRule r65 = new GrammarRule(new Pair("nterm", "ввод/вывод"), right65);
        this.rules.add(r65);

        ArrayList<Pair> right66 = new ArrayList();
        right66.add(new Pair("keyword", "cin"));
        right66.add(new Pair("keyword", ">>"));
        right66.add(new Pair("nterm","список имен"));
        right66.add(new Pair("separator",";"));
        GrammarRule r66 = new GrammarRule(new Pair("nterm", "ввод/вывод"), right66);
        this.rules.add(r66);

        ArrayList<Pair> right67 = new ArrayList();
        right67.add(new Pair("nterm", "ввод/вывод"));
        GrammarRule r67 = new GrammarRule(new Pair("nterm", "операторы"), right67);
        this.rules.add(r67);

        ArrayList<Pair> right68 = new ArrayList();
        right68.add(new Pair("nterm", "ввод/вывод"));
        right68.add(new Pair("nterm", "операторы"));
        GrammarRule r68 = new GrammarRule(new Pair("nterm", "операторы"), right68);
        this.rules.add(r68);
    }

// перегрузка метода вывода грамматики на экран
    @Override
    public void print() throws UnsupportedEncodingException {
        PrintStream ps = new PrintStream(System.out,false,"utf-8");
        ps.print("Аксиома грамматики - ");
        this.axiom.print();
        ps.println();
        ps.println("Правила грамматики:");
        for (int i = 0; i < this.rules.size(); i++) {
            ps.println("==========  " + i + "  ==========");
            this.rules.get(i).print();
            ps.println();
        }
    }

// возвращает аксиому грамматики
    @Override
    public Pair getAxiom() {
        return this.axiom;
    }

// возвращает правила граматики по левой части
    @Override
    public ArrayList<GrammarRule> getRules(Pair left) {
        ArrayList<GrammarRule> result = new ArrayList();
        for (int i = 0; i < this.rules.size(); i++) {
            if (left.equals(this.rules.get(i).getLeft())) {
                result.add(this.rules.get(i));
            }
        }
        return result;
    }

// получение правила по индексу
    @Override
    public GrammarRule getRuleByIndex(int index) {
        if(index < this.rules.size())
            return this.rules.get(index);
        else return null;
    }

// получение индекса
    @Override
    public int getRuleIndex(GrammarRule rule){
       for (int i = 0; i < this.rules.size(); i++) {
           
          if(this.rules.get(i).getLeft().equals(rule.getLeft())  && this.rules.get(i).getRight().equals(rule.getRight())){
             return i;   
          }
       }
       return -1;
    }
}
