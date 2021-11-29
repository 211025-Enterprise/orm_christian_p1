package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.service.ConnectionService;
import com.revature.annotations.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Dao<Class>{
    /**
     Executes a sql statement to create a table for a class.
     **/
    public void createTable(String sql) {
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.println("Created table in given database...");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     Executes a sql statement to create a record for a class.
     **/
    public Object createRecord(String sql, Object o) {
        int index = 1;
        Field[] fields = o.getClass().getDeclaredFields();

        try (Connection connection = ConnectionService.getConnection()) {
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (Field field : fields) {
                if(!field.isSynthetic()){
                    field.setAccessible(true);
                    Object value = field.get(o);
                    stmt.setObject(index++, value);
                }
            }
            stmt.executeUpdate();
            return o;

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return o;
    }
    /**
     Executes a sql statement to read a record from a table.
     **/
    public Object readRecord(String sql, Object o){
        int index = 1;
        Field[] fields = o.getClass().getDeclaredFields();

        try (Connection connection = ConnectionService.getConnection()) {
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (Field field : fields) {
                if(field.isAnnotationPresent(PrimaryKey.class) && !field.isSynthetic()){
                    field.setAccessible(true);
                    Object value = field.get(o);
                    stmt.setObject(1, value);
                }
            }
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Object record = getInstance(o.getClass());
                for (Field field : fields) {
                    if(!field.isSynthetic()) {
                        field.setAccessible(true);
                        field.set(record, rs.getObject(index++));
                    }
                }
                return record;
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return getInstance(o.getClass());
    }
    /**
     Executes a sql statement to read all records from a table.
     **/
    public ArrayList readAll(String sql, Object o){
        int index = 1;
        Field[] fields = o.getClass().getDeclaredFields();
        ArrayList<Object> records = new ArrayList<Object>();

        try (Connection connection = ConnectionService.getConnection()) {
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Object record = getInstance(o.getClass());
                for (Field field : fields) {
                    if(!field.isSynthetic()) {
                        field.setAccessible(true);
                        field.set(record, rs.getObject(index++));
                    }
                }
                records.add(record);
                index = 1;
            }
            return records;

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return records;
    }
    /**
     Executes a sql statement to update a record in a table.
     **/
    public boolean updateRecord(String sql, Object Change, Object primary_key) {
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, Change);
            stmt.setObject(2, primary_key);
            return stmt.executeUpdate() != 0;

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     Executes a sql statement to delete a record in a table.
     **/
    public boolean deleteById(String sql, Object primary_key) {
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, primary_key);
            return stmt.executeUpdate() != 0;

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     Creates an instance of an object given a class type.
     **/
    public static Object getInstance(java.lang.Class<?> clazz) {
        Constructor<?> noArgsConstructor;

        noArgsConstructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter( c -> c.getParameterCount() == 0)
                .findFirst().orElse(null);

        if(noArgsConstructor != null){
            try {
                return noArgsConstructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}