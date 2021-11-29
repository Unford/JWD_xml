package by.epam.xml.parser;

import by.epam.xml.entity.AbstractDeposit;
import by.epam.xml.exception.DepositXmlException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDepositBuilder {
    protected Set<AbstractDeposit> deposits;

    public AbstractDepositBuilder(){
        this.deposits = new HashSet<>();
    }

    public AbstractDepositBuilder(Set<AbstractDeposit> deposits){
        this.deposits = deposits;
    }

    public Set<AbstractDeposit> getDeposits(){
        return deposits;
    }

    public abstract void buildSetDeposits(String filename)throws DepositXmlException;
}
