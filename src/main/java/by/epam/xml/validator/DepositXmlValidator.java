package by.epam.xml.validator;

import java.io.File;

public class DepositXmlValidator {

    public boolean isValidFilepath(String filepath){
        if (filepath == null || filepath.isBlank()){
            return false;
        }
        File file = new File(filepath);
        return file.exists();
    }
}
