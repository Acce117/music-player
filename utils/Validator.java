package utils;

public class Validator {

    public static void emptyStringCheck(String str)throws Exception{
        if(str.equals(""))
            throw new Exception("Not validate entry");
    }
}
