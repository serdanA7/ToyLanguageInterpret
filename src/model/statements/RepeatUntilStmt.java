package model.statements;

import model.adt.IMyDictionary;
import model.adt.IMyStack;
import model.expressions.IExp;
import model.expressions.NegationExpression;
import model.states.PrgState;
import model.types.BoolIType;
import model.types.IType;
import exceptions.StatementException;


public class RepeatUntilStmt implements IStmt {
    private final IStmt stmt;
    private final IExp condition;

    public RepeatUntilStmt(IStmt stmt, IExp condition) {
        this.stmt = stmt;
        this.condition = condition;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        IMyStack<IStmt> stack = state.getExeStack();
        stack.push(new CompStmt(stmt, new WhileStatement(new NegationExpression(condition), stmt)));
        return null;
    }


    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(stmt.deepCopy(), condition.deepCopy());
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws StatementException {
        IType conditionType = condition.typecheck(typeEnv);
        if (!conditionType.equals(new BoolIType())) {
            throw new StatementException("RepeatUntil condition must be a boolean.");
        }
        stmt.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "repeat {" + stmt + "} until (" + condition + ")";
    }
}
