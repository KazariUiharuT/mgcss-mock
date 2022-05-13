package com.acme.bnb;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Data
@Component
public class QueryDatabase {

    @PersistenceContext
    private EntityManager em;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AcmeBnbApplication.class, args);

        ObjectMapper objectMapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);
        String query;
        boolean continuar = true;
        QueryDatabase q = context.getBean(QueryDatabase.class);

        while (continuar) {
            try {
                System.out.print("Query: ");
                query = scanner.nextLine();
                Object result = q.execute(query);
                System.out.println(objectMapper.writeValueAsString(result));
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
            System.out.println("¿Continuar? y/n");
            continuar = scanner.nextLine().toLowerCase().trim().equals("y");
        }
        System.exit(0);
    }

    public Object execute(String query) {
        return em.createQuery(query).getResultList();
    }
}
