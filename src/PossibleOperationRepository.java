import java.util.ArrayList;

//класс контейнер всех возможных операций в Pascal
public class PossibleOperationRepository {

    private ArrayList<PossibleOperation> operation;

    // конструктор класса
    PossibleOperationRepository() {
        this.operation = new ArrayList();
        PossibleOperation o1 = new PossibleOperation("+", "integer", "integer", "integer");
        operation.add(o1);
        PossibleOperation o2 = new PossibleOperation("*", "integer", "integer", "integer");
        operation.add(o2);
        PossibleOperation o3 = new PossibleOperation("/", "integer", "integer", "integer");
        operation.add(o3);
        PossibleOperation o4 = new PossibleOperation("-", "integer", "integer", "integer");
        operation.add(o4);
        PossibleOperation o5 = new PossibleOperation("+=", "integer", "integer", "integer");
        operation.add(o5);
        PossibleOperation o6 = new PossibleOperation("-=", "integer", "integer", "integer");
        operation.add(o6);
        PossibleOperation o7 = new PossibleOperation("/=", "integer", "integer", "integer");
        operation.add(o7);
        PossibleOperation o8 = new PossibleOperation("*=", "integer", "integer", "integer");
        operation.add(o8);
        PossibleOperation o9 = new PossibleOperation("+", "integer", "real", "real");
        operation.add(o9);
        PossibleOperation o10 = new PossibleOperation("*", "integer", "real", "real");
        operation.add(o10);
        PossibleOperation o11 = new PossibleOperation("/", "integer", "real", "real");
        operation.add(o11);
        PossibleOperation o12 = new PossibleOperation("-", "integer", "real", "real");
        operation.add(o12);
        PossibleOperation o13 = new PossibleOperation("+=", "real", "integer", "real");
        operation.add(o13);
        PossibleOperation o14 = new PossibleOperation("-=", "real", "integer", "real");
        operation.add(o14);
        PossibleOperation o15 = new PossibleOperation("/=", "real", "integer", "real");
        operation.add(o15);
        PossibleOperation o16 = new PossibleOperation("*=", "real", "integer", "real");
        operation.add(o16);
        PossibleOperation o17 = new PossibleOperation("+", "real", "real", "real");
        operation.add(o17);
        PossibleOperation o18 = new PossibleOperation("*", "real", "real", "real");
        operation.add(o18);
        PossibleOperation o19 = new PossibleOperation("/", "real", "real", "real");
        operation.add(o19);
        PossibleOperation o20 = new PossibleOperation("-", "real", "real", "real");
        operation.add(o20);
        PossibleOperation o21 = new PossibleOperation("+=", "real", "real", "real");
        operation.add(o21);
        PossibleOperation o22 = new PossibleOperation("-=", "real", "real", "real");
        operation.add(o22);
        PossibleOperation o23 = new PossibleOperation("/=", "real", "real", "real");
        operation.add(o23);
        PossibleOperation o24 = new PossibleOperation("*=", "real", "real", "real");
        operation.add(o24);
        PossibleOperation o25 = new PossibleOperation("*=", "string", "integer", "string");
        operation.add(o24);
        PossibleOperation o26 = new PossibleOperation("*", "string", "integer", "string");
        operation.add(o26);
        PossibleOperation o27 = new PossibleOperation("+=", "string", "integer", "string");
        operation.add(o27);
        PossibleOperation o28 = new PossibleOperation("+", "string", "integer", "string");
        operation.add(o28);
        PossibleOperation o29 = new PossibleOperation("+=", "string", "string", "string");
        operation.add(o29);
        PossibleOperation o30 = new PossibleOperation("+", "string", "string", "string");
        operation.add(o30);
        PossibleOperation o31 = new PossibleOperation("+=", "string", "real", "string");
        operation.add(o31);
        PossibleOperation o32 = new PossibleOperation("+", "string", "real", "string");
        operation.add(o32);
        PossibleOperation o33 = new PossibleOperation("*=", "char", "integer", "string");
        operation.add(o33);
        PossibleOperation o34 = new PossibleOperation("*", "char", "integer", "string");
        operation.add(o34);
        PossibleOperation o35 = new PossibleOperation("+=", "char", "integer", "string");
        operation.add(o35);
        PossibleOperation o36 = new PossibleOperation("+", "char", "integer", "string");
        operation.add(o36);
        PossibleOperation o37 = new PossibleOperation("+=", "char", "string", "string");
        operation.add(o37);
        PossibleOperation o38 = new PossibleOperation("+", "char", "string", "string");
        operation.add(o38);
        PossibleOperation o39 = new PossibleOperation("+=", "char", "real", "string");
        operation.add(o39);
        PossibleOperation o40 = new PossibleOperation("+", "char", "real", "string");
        operation.add(o40);
        PossibleOperation o41 = new PossibleOperation("+=", "char", "char", "string");
        operation.add(o41);
        PossibleOperation o42 = new PossibleOperation("+", "char", "char", "string");
        operation.add(o42);
        PossibleOperation o43 = new PossibleOperation("<", "integer", "integer", "boolean");
        operation.add(o43);
        PossibleOperation o44 = new PossibleOperation(">", "integer", "integer", "boolean");
        operation.add(o44);
        PossibleOperation o45 = new PossibleOperation("<=", "integer", "integer", "boolean");
        operation.add(o45);
        PossibleOperation o46 = new PossibleOperation(">=", "integer", "integer", "boolean");
        operation.add(o46);
        PossibleOperation o47 = new PossibleOperation("=", "integer", "integer", "boolean");
        operation.add(o47);
        PossibleOperation o48 = new PossibleOperation("<>", "integer", "integer", "boolean");
        operation.add(o48);
        PossibleOperation o49 = new PossibleOperation("<", "real", "real", "boolean");
        operation.add(o49);
        PossibleOperation o50 = new PossibleOperation(">", "real", "real", "boolean");
        operation.add(o50);
        PossibleOperation o51 = new PossibleOperation("<=", "real", "real", "boolean");
        operation.add(o51);
        PossibleOperation o52 = new PossibleOperation(">=", "real", "real", "boolean");
        operation.add(o52);
        PossibleOperation o53 = new PossibleOperation("=", "real", "real", "boolean");
        operation.add(o53);
        PossibleOperation o54 = new PossibleOperation("<>", "real", "real", "boolean");
        operation.add(o54);
        PossibleOperation o55 = new PossibleOperation("<", "integer", "real", "boolean");
        operation.add(o55);
        PossibleOperation o56 = new PossibleOperation(">", "integer", "real", "boolean");
        operation.add(o56);
        PossibleOperation o57 = new PossibleOperation("<=", "integer", "real", "boolean");
        operation.add(o57);
        PossibleOperation o87 = new PossibleOperation("<=", "real", "integer", "boolean");
        operation.add(o87);
        PossibleOperation o58 = new PossibleOperation(">=", "integer", "real", "boolean");
        operation.add(o58);
        PossibleOperation o86 = new PossibleOperation(">=", "real", "integer", "boolean");
        operation.add(o86);
        PossibleOperation o59 = new PossibleOperation("=", "integer", "real", "boolean");
        operation.add(o59);
        PossibleOperation o60 = new PossibleOperation("<>", "integer", "real", "boolean");
        operation.add(o60);
        PossibleOperation o85 = new PossibleOperation("<>", "real", "integer", "boolean");
        operation.add(o85);
        PossibleOperation o61 = new PossibleOperation("<", "string", "string", "boolean");
        operation.add(o61);
        PossibleOperation o62 = new PossibleOperation(">", "string", "string", "boolean");
        operation.add(o62);
        PossibleOperation o63 = new PossibleOperation("<=", "string", "string", "boolean");
        operation.add(o63);
        PossibleOperation o64 = new PossibleOperation(">=", "string", "string", "boolean");
        operation.add(o64);
        PossibleOperation o65 = new PossibleOperation("=", "string", "string", "boolean");
        operation.add(o65);
        PossibleOperation o66 = new PossibleOperation("<>", "string", "string", "boolean");
        operation.add(o66);
        PossibleOperation o67 = new PossibleOperation("<", "char", "char", "boolean");
        operation.add(o67);
        PossibleOperation o68 = new PossibleOperation(">", "char", "char", "boolean");
        operation.add(o68);
        PossibleOperation o69 = new PossibleOperation("<=", "char", "char", "boolean");
        operation.add(o69);
        PossibleOperation o70 = new PossibleOperation(">=", "char", "char", "boolean");
        operation.add(o70);
        PossibleOperation o71 = new PossibleOperation("=", "char", "char", "boolean");
        operation.add(o71);
        PossibleOperation o72 = new PossibleOperation("<>", "char", "char", "boolean");
        operation.add(o72);
        PossibleOperation o73 = new PossibleOperation("<", "boolean", "boolean", "boolean");
        operation.add(o73);
        PossibleOperation o74 = new PossibleOperation(">", "boolean", "boolean", "boolean");
        operation.add(o74);
        PossibleOperation o75 = new PossibleOperation("<=", "boolean", "boolean", "boolean");
        operation.add(o75);
        PossibleOperation o76 = new PossibleOperation(">=", "boolean", "boolean", "boolean");
        operation.add(o76);
        PossibleOperation o77 = new PossibleOperation("=", "boolean", "boolean", "boolean");
        operation.add(o77);
        PossibleOperation o78 = new PossibleOperation("<>", "boolean", "boolean", "boolean");
        operation.add(o78);
        PossibleOperation o79 = new PossibleOperation("abs", "integer", "integer");
        operation.add(o79);
        PossibleOperation o80 = new PossibleOperation("abs", "real", "real");
        operation.add(o80);
        PossibleOperation o81 = new PossibleOperation("sqr", "integer", "integer");
        operation.add(o81);
        PossibleOperation o82 = new PossibleOperation("sqr", "real", "real");
        operation.add(o82);
        PossibleOperation o83 = new PossibleOperation("sqrt", "real", "real");
        operation.add(o83);
        PossibleOperation o84 = new PossibleOperation("exp", "real", "real");
        operation.add(o84);
    }

//    функция поиска бинарной операции
    public String getReturnType(String operation, String operandType1, String operandType2) throws Exception {
        PossibleOperation current = new PossibleOperation(operation, operandType1, operandType2, "");
        boolean isFind = false;
        int i = 0;
        while (i < this.operation.size() && !isFind) {
            if (this.operation.get(i).equals(current)) {
                return this.operation.get(i).getReturnType();
            } else {
                i++;
            }
        }
        throw new Exception("Недопустимая операция "
                + operation
                + " с аргументами типа:("
                + operandType1 + ","
                + operandType2 + ")");
    }

//    функция поиска унарной операции
    public String getReturnType(String operation, String operandType) throws Exception {
        PossibleOperation current = new PossibleOperation(operation, operandType, "", "");
        boolean isFind = false;
        int i = 0;
        while (i < this.operation.size() && !isFind) {
            if (this.operation.get(i).equals(current)) {
                return this.operation.get(i).getReturnType();
            } else {
                i++;
            }
        }
        throw new Exception("Недопустимый вызов функции - "
                + operation
                + " с аргументом типа: "
                + operandType);
    }
}
