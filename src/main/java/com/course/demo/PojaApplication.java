package com.course.demo;

import com.course.demo.db.DatabaseConfig;
import java.sql.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PojaApplication {

  public static void main(String[] args) {
    try {
      System.out.println("=== TEST DE CONNEXION PRÉ-DÉMARRAGE ===");
      try (Connection conn = DatabaseConfig.getConnection()) {
        System.out.println("✅ Connexion réussie !");
        System.out.println("Base de données: " + conn.getMetaData().getDatabaseProductName());
        System.out.println("Version: " + conn.getMetaData().getDatabaseProductVersion());
      }
    } catch (Exception e) {
      System.err.println("❌ Erreur de connexion: " + e.getMessage());
      e.printStackTrace();
    }

    SpringApplication.run(PojaApplication.class, args);
  }
}
