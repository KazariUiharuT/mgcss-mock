package com.acme.bnb;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;

public class CreateWebJson {
    public static void main(String[] args){
        ObjectMapper objectMapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);
        StringBuilder buffer = new StringBuilder();
        Class<?> objClass;
        Object obj;
        String className, line;
        boolean continuar = true;
        
        while(continuar){
            try {
                System.out.print("Introduzca el nombre de la clase: ");
                className = scanner.nextLine();
                objClass = Class.forName((className.contains(".")?"":"com.acme.bnb.model.")+className);
                System.out.println("Introduzca el JSON: ");
                line = "";
                while(!line.equals("EOF")){
                    buffer.append(line);
                    line = scanner.nextLine();
                }
                obj = objectMapper.readValue(buffer.toString(), objClass);
                System.out.println("Objeto leido. Escribiendo");
                System.out.println(objectMapper.writeValueAsString(obj));
            } catch (Exception e) {
                System.out.println("Error inesperado: "+e.getMessage());
            }
            buffer.setLength(0);
            System.out.println("¿Continuar? y/n");
            continuar = scanner.nextLine().toLowerCase().trim().equals("y");
        }
    }
}
