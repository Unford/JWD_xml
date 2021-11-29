package by.epam.xml.parser;


import by.epam.xml.exception.DepositXmlException;

public class DepositBuilderFactory {
    private DepositBuilderFactory(){}

    private enum TypeParser{
        SAX, STAX, DOM
    }

    public static AbstractDepositBuilder createDepositBuilder(String type)throws DepositXmlException {
        TypeParser typeParser = TypeParser.valueOf(type.toUpperCase());
        switch (typeParser){
            case DOM:
                return new DepositsDOMBuilder();
            case SAX:
                return new DepositsSAXBuilder();
            case STAX:
                return new DepositsStAXBuilder();
            default:
                throw new EnumConstantNotPresentException(
                    typeParser.getDeclaringClass(), typeParser.name());
        }
    }
}
