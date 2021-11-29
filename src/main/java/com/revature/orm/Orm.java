package com.revature.orm;

import com.revature.annotations.*;
import java.lang.reflect.*;

public class Orm {
    /**
     Creates a sql string to create a table for a class.
     **/
    public static String createTable(Class<?> clazz){
        String  sql = "CREATE TABLE " + clazz.getSimpleName() + " " + "(";
        Field[] fields = clazz.getDeclaredFields();
        String primary_key = "";

        if(fields.length == 0){
        } else {
            for(Field field : fields){
                if(!field.isSynthetic()){
                    sql += field.getName() + " ";
                    sql += sqlToJavaType(field.getType().toString());
                    if (field.isAnnotationPresent(Unique.class)){
                        sql += " UNIQUE";
                    }
                    else if(field.isAnnotationPresent(NotNull.class)){
                        sql += " not NULL";
                    }
                    sql += ", ";
                    if(field.isAnnotationPresent(PrimaryKey.class)){
                        primary_key = "PRIMARY KEY ( " + field.getName() + " )";
                    }
                }
            }
        }

        if (primary_key.equals("")){
            sql = sql.substring(0, sql.lastIndexOf(",")) + ")";
        } else{
            sql += primary_key + ")";
        }
        return sql;
    }
    /**
     Creates a sql string to create a record for a table.
     **/
    public static String createRecord(Class<?> clazz) {
        String sql = "insert into " + clazz.getSimpleName() + "(";
        Field[] fields = clazz.getDeclaredFields();
        int length = 0;

        if(fields.length == 0){
        } else {
            for(Field field : fields){
                if(!field.isSynthetic()){
                    sql += field.getName() + ", ";
                    length += 1;
                }
            }
        }

        sql = sql.substring(0, sql.lastIndexOf(", ")) + ") ";
        sql += returnValueString(length);
        return sql;
    }
    /**
     Creates a sql string to read a record from a table.
     **/
    public static String readRecord(Class<?> clazz){
        String sql = "select * from " + clazz.getSimpleName() + " where ";
        Field[] fields = clazz.getDeclaredFields();

        if(fields.length == 0){
        } else {
            for(Field field : fields){
                if(field.isAnnotationPresent(PrimaryKey.class) && !field.isSynthetic()){
                    sql += field.getName();
                }
            }
        }
        sql += " =?";
        return sql;
    }
    /**
     Creates a sql string to read all records from a table.
     **/
    public static String readAll(Class<?> clazz){
        return "select * from " + clazz.getSimpleName();
    }
    /**
     Creates a sql string to update a table.
     **/
    public static String updateRecord(Class<?> clazz, String property){
        String sql = "update " + clazz.getSimpleName() + " set " + property + "=? where ";
        Field[] fields = clazz.getDeclaredFields();

        if(fields.length == 0){
        } else {
            for(Field field : fields){
                if(field.isAnnotationPresent(PrimaryKey.class) && !field.isSynthetic()){
                    sql += field.getName();
                }
            }
        }
        sql += "=?";
        return sql;
    }
    /**
     Creates a sql string to delete a record from a table.
     **/
    public static String deleteRecord(Class<?> clazz){
        String sql = "delete from " + clazz.getSimpleName() + " where ";
        Field[] fields = clazz.getDeclaredFields();

        if(fields.length == 0){
        } else {
            for(Field field : fields){
                if(field.isAnnotationPresent(PrimaryKey.class) && !field.isSynthetic()){
                    sql += field.getName();
                }
            }
        }
        sql += "=?";
        return sql;
    }
    /**
     Converts SQL type to Java type.
     **/
    private static String sqlToJavaType(String java_type){
        switch (java_type) {
            case "byte":
                return "BLOB";
            case "short":
                return "SMALLINT";
            case "int":
            case "class java.lang.Integer":
                return "INTEGER";
            case "long":
                return "INT8";
            case "double":
            case "float":
                return "FLOAT";
            case "char":
                return "VARCHAR";
            case "boolean":
                return "BOOLEAN";
            case "class java.lang.String":
                return "VARCHAR(255)";
        }
        System.out.println(java_type);
        return "VARCHAR(255)";
    }
    /**
     Helper function to create the Value(?,?,?) part of a string.
     **/
    private static String returnValueString(int length){
        String value_string = "values(";
        for (int x = 0;x < length;x++){
            value_string += "?,";
        }
        value_string = value_string.substring(0, value_string.lastIndexOf(",")) + ")";

        return value_string;
    }

}