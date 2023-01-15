// класс возможных операций в Pascal
public class PossibleOperation {

    private String operation;
    private String typeOfOperand1;
    private String typeOfOperand2;
    private String returnType;

    // конструктор унарной операции принимающий символ операции, тип аргумента и тип возвращаемого значения
    PossibleOperation(String operation, String typeOfOperand, String returnType) {
        this.operation = operation;
        this.typeOfOperand1 = typeOfOperand;
        this.returnType = returnType;
        this.typeOfOperand2 = "";
    }

    // конструктор бинарной операции принимающий символ операции, типы аргументов и тип возвращаемого значения
    PossibleOperation(String operation, String typeOfOperand1, String typeOfOperand2, String returnType) {
        this.operation = operation;
        this.typeOfOperand1 = typeOfOperand1;
        this.returnType = returnType;
        this.typeOfOperand2 = typeOfOperand2;
    }

    // возвращает тип возвращаемый операцией
    public String getReturnType() {
        return this.returnType;
    }

//    перегрузка функции сравнения операций
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PossibleOperation)) {
            return false;
        }
        PossibleOperation c = (PossibleOperation) o;
        boolean eq = false;
        if (this.operation.equals(c.operation)
                && ((this.typeOfOperand1.equals(c.typeOfOperand1) && this.typeOfOperand2.equals(c.typeOfOperand2))
                || (this.typeOfOperand1.equals(c.typeOfOperand2) && this.typeOfOperand2.equals(c.typeOfOperand1)))) {
            eq = true;
        }
        return eq;
    }
}
