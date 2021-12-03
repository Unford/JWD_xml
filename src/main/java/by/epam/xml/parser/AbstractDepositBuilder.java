package by.epam.xml.parser;

import by.epam.xml.entity.AbstractDeposit;
import by.epam.xml.exception.DepositXmlException;
import by.epam.xml.validator.DepositXmlValidator;
import by.epam.xml.validator.impl.DepositXmlValidatorImpl;

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
        return Set.copyOf(deposits);
    }

    public abstract void buildSetDeposits(String filename)throws DepositXmlException;

    public void buildSetDeposits(String filename, String schema)throws DepositXmlException {
        DepositXmlValidator validator = DepositXmlValidatorImpl.getInstance();
        if (!validator.isValidXmlFile(filename, schema)){
            throw new DepositXmlException("Xml file is invalid: " + filename);
        }
        buildSetDeposits(filename);
    }


}
