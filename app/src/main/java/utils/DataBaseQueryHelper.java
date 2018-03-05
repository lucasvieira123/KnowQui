package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * Created by lucas-vieira on 05/03/18.
 */

public class DataBaseQueryHelper {

    private static final int COUNT_DEFAULT_FIELDS_PUBLIC = 2;
    private Class classe;
    private final Field[] privateAttributes;
    private final String columnsSeparateWithComma;
    private static String NAME_TABLE = null;

    public DataBaseQueryHelper(Class classe) {
        this.classe = classe;
        privateAttributes = getPrivateAttributes();
        columnsSeparateWithComma = getColumnsInStringFromPrivateAttributes();
        NAME_TABLE = classe.getSimpleName();
    }

    public Field[] getPrivateAttributes() {
        Field[] allAttributesPrivatesAndPublics = classe.getDeclaredFields();
        int countPrivateAttributes = allAttributesPrivatesAndPublics.length - COUNT_DEFAULT_FIELDS_PUBLIC;
        Field[] privateAttributes = new Field[countPrivateAttributes];
        for (int i = 0; i < allAttributesPrivatesAndPublics.length; i++) {
            if (isPrivate(allAttributesPrivatesAndPublics[i])) {
                privateAttributes[i] = allAttributesPrivatesAndPublics[i];
            }
        }

        return privateAttributes;
    }

    private  boolean isPrivate(Field attribute) {
        return Modifier.isPrivate(attribute.getModifiers());
    }

    public  String getColumnsInStringFromPrivateAttributes() {
        String columnsSeparateWithComma = "";
        for (int i = 0; i < privateAttributes.length; i++) {
            columnsSeparateWithComma = columnsSeparateWithComma.concat(privateAttributes[i].getName().concat(", "));
        }

        columnsSeparateWithComma = removeLastComma(columnsSeparateWithComma.trim());

        return columnsSeparateWithComma;
    }

    private  String removeLastComma(String str) {
        return str = str.substring(0, str.length() - 1);
    }

    public  String getValues(Object o) {
        String valuesSepareteWithComma = "";
        String getMethedName;
        String attribute;
        String value;
        for (int i = 0; i < privateAttributes.length; i++) {
            attribute = privateAttributes[i].getName();
            getMethedName = createNameMethedWithGetFrom(attribute);
            value = tryGetValue(o, getMethedName);
            valuesSepareteWithComma = valuesSepareteWithComma + value.concat(",");
        }
        return removeLastComma(valuesSepareteWithComma);
    }

    private  String createNameMethedWithGetFrom(String attribute) {
        String attributeWithFirstLetterUpperCase = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        String nameMethedWithGetFromAttribute = "get".concat(attributeWithFirstLetterUpperCase);
        return nameMethedWithGetFromAttribute;
    }

    private String tryGetValue(Object o, String getMethedName) {
        Object result = null;
        Integer valueInteger;
        Double valueDouble;
        try {
            Method method = o.getClass().getMethod(getMethedName);
            result = method.invoke(o);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
            e1.printStackTrace();
        }

        //todo pogue,tentar usar algum padrão de projeto, por exemplo chain of resp.
        if (result instanceof Integer) {
            valueInteger = ((Integer) result);
            return String.valueOf(valueInteger);

        } else if (result instanceof Double) {
            valueDouble = ((Double) result);
            return String.valueOf(valueDouble);

        } else if (result instanceof String) {
            String stringWithoutSpa = (String) result;
            return "'" + stringWithoutSpa + "'";

        } else if (result instanceof Boolean) {
            if ((Boolean) result == true) {
                return "1";
            } else {
                return "0";
            }
        }

        /*return getMethedName.concat("= error");*/
        return "null";


    }

    public String getStatementInsert(Object o){
        String valuesSepareteWithComma = getValues(o);
        String STATEMENT_INSERT = "INSERT INTO " + NAME_TABLE + " (" + columnsSeparateWithComma + ") VALUES " +
                "(" + valuesSepareteWithComma + ")";

        return STATEMENT_INSERT;
    }

}
