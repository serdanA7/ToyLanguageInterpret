package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.types.BoolIType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class NegationExpression implements IExp {
    private final IExp expression;

    public NegationExpression(IExp expression) {
        this.expression = expression;
    }

    @Override
    public IType typecheck(IMyDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type = expression.typecheck(typeEnv);
        if (!type.equals(new BoolIType())) {
            throw new ExpressionException("NegationExpression: Expression must be of boolean type.");
        }
        return new BoolIType();
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> symTable, IMyHeap heap) throws ADTException, ExpressionException {
        IValue value = expression.eval(symTable, heap);
        if (!(value instanceof BoolValue)) {
            throw new ExpressionException("NegationExpression: Expression must be of boolean type.");
        }
        return new BoolValue(!((BoolValue) value).getVal());
    }

    @Override
    public IExp deepCopy() {
        return new NegationExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "!" + expression.toString();
    }
}
