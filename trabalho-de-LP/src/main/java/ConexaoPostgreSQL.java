import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {

    public static Connection criarConexao() {
        Connection conexao = null;

        try {
            // Carregando o driver JDBC do PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Configurando os parâmetros da conexãAO
            final String url = "jdbc:postgresql://localhost/NEW-CHAT";
            final String user = "postgres";
            final String password = "123456";
            com.sun.jdi.connect.spi.Connection conn = null;

            // Estabelecendo a conexão com o banco de dados
            conexao = DriverManager.getConnection(url, user, password);

            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL não encontrado. Certifique-se de que o driver está no classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer a conexão com o banco de dados.");
            e.printStackTrace();
        }

        return conexao;
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão com o banco de dados fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco de dados.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Connection conexao = criarConexao();



        fecharConexao(conexao);
    }
}