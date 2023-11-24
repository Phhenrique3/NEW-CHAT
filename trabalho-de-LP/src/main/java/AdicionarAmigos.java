import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdicionarAmigos extends JDialog {
    private JTextField pesquisarAmigos;
    private JButton adicionarButton;
    private Interface_NewChat parent;

    public AdicionarAmigos(Frame owner) {
        super(owner, "Adicionar Amigos", true);
        parent = (Interface_NewChat) owner;

        setLayout(new BorderLayout());

        JPanel adicionarPanel = new JPanel();
        adicionarPanel.setBackground(Color.pink);
        adicionarPanel.setLayout(new GridLayout(2, 1));

        pesquisarAmigos = new JTextField(20);
        adicionarPanel.add(pesquisarAmigos);

        adicionarButton = new JButton("Adicionar Amigo");
        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarAmigo();
            }
        });
        adicionarPanel.add(adicionarButton);

        add(adicionarPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(owner);
    }

    private void adicionarAmigo() {
        String emailAmigo = pesquisarAmigos.getText();

        // Obtém a lista de usuários logados
        List<Interface_NewChat.Usuario> usuariosLogados = parent.getUsuariosLogados();

        // Verifica se o amigo a ser adicionado está na lista de usuários logados
        boolean amigoEncontrado = false;
        for (Interface_NewChat.Usuario usuario : usuariosLogados) {
            if (usuario.getEmail().equals(emailAmigo)) {
                amigoEncontrado = true;
                break;
            }
        }

        // Se o amigo foi encontrado, adiciona o amigo
        if (amigoEncontrado) {
            parent.adicionarAmigo(emailAmigo);
            JOptionPane.showMessageDialog(this, "Amigo adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "O amigo não está cadastrado ou não está logado no sistema.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Fecha o diálogo após adicionar o amigo
        dispose();
    }
}