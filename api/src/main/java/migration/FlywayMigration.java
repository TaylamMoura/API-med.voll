package migration;
import org.flywaydb.core.Flyway;

public class FlywayMigration {
    public static void main(String[] args) {
        // Crie uma instância do Flyway configurando o banco de dados
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost/vollmed_api", "taylam", "c4l1st0")
                .load();

        // Execute o reparo
        flyway.repair();

        // Execute a migração
        flyway.migrate();
    }
}
