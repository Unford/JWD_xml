package by.epam.xml.parser;


import by.epam.xml.exception.DepositXmlException;

public class DepositBuilderFactory {
    private DepositBuilderFactory(){}

    public enum TypeParser{
        SAX, STAX, DOM
    }

    public static AbstractDepositBuilder createDepositBuilder(String type)throws DepositXmlException {
        TypeParser typeParser = TypeParser.valueOf(type.toUpperCase());
        return createDepositBuilder(typeParser);
    }

    public static AbstractDepositBuilder createDepositBuilder(TypeParser typeParser)throws DepositXmlException {
        AbstractDepositBuilder depositBuilder;
        switch (typeParser){
            case DOM:
                depositBuilder = new DepositsDOMBuilder();
                break;
            case SAX:
                depositBuilder = new DepositsSAXBuilder();
                break;
            case STAX:
                depositBuilder = new DepositsStAXEventBuilder();
                break;
            default: throw new DepositXmlException("Invalid parser type, can't create builder");
        }
        return depositBuilder;
    }
}
