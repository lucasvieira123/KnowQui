package utils;

import android.database.Cursor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Bean;
import model.Historico;


/**
 * Created by lucas-vieira on 05/03/18.
 */

public class DataBaseQueryHelper {

    private static final int COUNT_DEFAULT_FIELDS_PUBLIC = 2;
    private Class classe;
    private final Field[] privateAttributes;
    private Map<String,Class> fieldAndTypes = new HashMap();
    private final String columnsSeparateWithComma;
    private String NAME_TABLE = null;

    public DataBaseQueryHelper(Class classe) {
        this.classe = classe;
        privateAttributes = getPrivateAttributes();
        fieldAndTypes = createMapsOfAttributesAndTypes();
        columnsSeparateWithComma = getColumnsInStringFromPrivateAttributes();
        NAME_TABLE = classe.getSimpleName();
    }

    private Map<String, Class> createMapsOfAttributesAndTypes() {
        Map<String,Class> fieldAndTypes = new HashMap();
        for(int i = 0; i< privateAttributes.length ; i++){
            fieldAndTypes.put(privateAttributes[i].getName(),privateAttributes[i].getType());
        }

        return fieldAndTypes;
    }

    public Field[] getPrivateAttributes() {
        Field[] allAttributesPrivatesAndPublics = classe.getDeclaredFields();
        int countPrivateAttributes = allAttributesPrivatesAndPublics.length - COUNT_DEFAULT_FIELDS_PUBLIC;
        Field[] privateAttributes = new Field[countPrivateAttributes];
        for (int i = 0; i < allAttributesPrivatesAndPublics.length; i++) {
            if (isPrivate(allAttributesPrivatesAndPublics[i]) && notStartWithUnderscore(allAttributesPrivatesAndPublics[i])) {
                privateAttributes[i] = allAttributesPrivatesAndPublics[i];
            }
        }



        return privateAttributes;
    }

    private boolean notStartWithUnderscore(Field field) {
        return !field.getName().substring(0,1).contains("_");

    }

    private boolean isPrivate(Field attribute) {
        return Modifier.isPrivate(attribute.getModifiers());
    }

    public String getColumnsInStringFromPrivateAttributes() {
        String columnsSeparateWithComma = "";
        for (int i = 0; i < privateAttributes.length; i++) {
            columnsSeparateWithComma = columnsSeparateWithComma.concat(privateAttributes[i].getName().concat(", "));
        }

        columnsSeparateWithComma = removeLastComma(columnsSeparateWithComma.trim());

        return columnsSeparateWithComma;
    }

    private String removeLastComma(String str) {
        return str = str.substring(0, str.length() - 1);
    }

    public String getValues(Object o) {
        String valuesSepareteWithComma = "";
        String getMethedName;
        String attribute;
        String value;
        for (int i = 0; i < privateAttributes.length; i++) {
            attribute = privateAttributes[i].getName();
            getMethedName = createNameMethedWithGetFrom(attribute);
            value = tryGetValue(o, getMethedName);
            if(value == null){
                valuesSepareteWithComma = valuesSepareteWithComma + "null".concat(",");
            }else {
                valuesSepareteWithComma = valuesSepareteWithComma + value.concat(",");
            }

        }
        return removeLastComma(valuesSepareteWithComma);
    }

    private String createNameMethedWithGetFrom(String attribute) {
        String attributeWithFirstLetterUpperCase = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        String nameMethedWithGetFromAttribute = "get".concat(attributeWithFirstLetterUpperCase);
        return nameMethedWithGetFromAttribute;
    }

    private String createNameMethedWithSetFrom(String attribute) {
        String attributeWithFirstLetterUpperCase = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        String nameMethedWithGetFromAttribute = "set".concat(attributeWithFirstLetterUpperCase);
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
        if(result.equals("null")){
            return null;
        }else if (result instanceof Integer) {
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

    public String getStatementInsert(Object o) {
        String valuesSepareteWithComma = getValues(o);
        String insertStatement = "INSERT INTO " + NAME_TABLE + " (" + columnsSeparateWithComma + ") VALUES " +
                "(" + valuesSepareteWithComma + ")";

        return insertStatement;
    }

    public String getStatementDelete(Object o) {
        String idValue;
        if (o instanceof Integer) {
            idValue = String.valueOf(o);
            return getStatementDelete(idValue);
        } else {
            idValue = tryGetValue(o, "getId");
            String removeStatement = getStatementDelete(idValue);
            return removeStatement;
        }

    }


    public String getStatementDelete(String idValue) {
        String removeStatement = "DELETE FROM " + NAME_TABLE + " WHERE id = " + idValue;
        return removeStatement;
    }

    public String getStatementList() {
        String listStatement = "SELECT * FROM " + NAME_TABLE;
        return listStatement;
    }

    public List<Object> getList(Cursor cursor) {
        List<Object> objectList = new ArrayList<>();
        Object [] args = new Object[2];
        String nameMethodWithSet;
        String value;
        while (cursor.moveToNext()) {
            Object newObject = tryCreateNewInstance();
            for (String collumn : cursor.getColumnNames()) {
                value = cursor.getString(cursor.getColumnIndex(collumn));
                args[0] = createMapsOfAttributesAndTypes().get(collumn);
                args[1] = value;
                nameMethodWithSet = createNameMethedWithSetFrom(collumn);
                trySetValue(newObject, nameMethodWithSet, args);
            }

            objectList.add(newObject);
        }

        return objectList;
    }

    private void trySetValue(Object object, String nameMethodWithSet, Object[] args) {
        Object objectType = args[0];
        Class classTypeToInvokeMethed = null;
        Object value = null;
        if((((Class) objectType).getName()).contains("Integer")){
            classTypeToInvokeMethed = Integer.class;
            value = Integer.valueOf((String) args[1]);
        }else if((((Class) objectType).getName()).contains("Double")){
            classTypeToInvokeMethed = Double.class;
            value = Double.valueOf((String) args[1]);
        }else if((((Class) objectType).getName()).contains("String")){
            value = ((String) args[1]);
            classTypeToInvokeMethed = String.class;
        }else if((((Class) objectType).getName()).contains("Boolean")){
            if(( args[1]).equals("1")){
                value = true;
            }else {
                value = false;
            }
            classTypeToInvokeMethed = Boolean.class;

        }


        try {
            Method method = object.getClass().getMethod(nameMethodWithSet,classTypeToInvokeMethed);
            method.invoke(object, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }

    private Object tryCreateNewInstance() {
        try {
            return classe.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStatementList(String selection, String[] selectionArgs) {
        String selectionArgsPrepared = String.format(selection, selectionArgs);
        String clauseWhere = " WHERE ".concat(selectionArgsPrepared);
        String listStatement = "SELECT * FROM " + NAME_TABLE.concat(clauseWhere);
        return listStatement;

    }

    public String getStatementElement(String selection, String[] selectionArgs) {
        String selectionArgsPrepared = String.format(selection, selectionArgs);
        String clauseWhere = " WHERE ".concat(selectionArgsPrepared);
        String elementStatement = "SELECT * FROM " + NAME_TABLE.concat(clauseWhere);
        return elementStatement;
    }

    public Object getElement(Cursor cursor) {
        Object [] args = new Object[2];
        String nameMethodWithSet;
        String value;
        while (cursor.moveToNext()) {
            Object newObject = tryCreateNewInstance();
            for (String collumn : cursor.getColumnNames()) {
                value = cursor.getString(cursor.getColumnIndex(collumn));
                args[0] = createMapsOfAttributesAndTypes().get(collumn);
                args[1] = value;
                nameMethodWithSet = createNameMethedWithSetFrom(collumn);
                trySetValue(newObject, nameMethodWithSet, args);
            }

            return newObject;
        }
        return null;
    }

    public String getStatementFirst() {
        return "SELECT * FROM "+NAME_TABLE+" ORDER BY id ASC LIMIT 1";
    }

    public String getStatementRemoveAll() {
        return "DELETE FROM "+NAME_TABLE;
    }
}
