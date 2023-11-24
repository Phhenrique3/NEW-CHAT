import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CadastroDialog extends JDialog {
    private JTextField nomeField, emailField, senhaField;
    private JButton cadastrarButton;
    private Interface_NewChat parent;

    public CadastroDialog(Frame owner) {
        super(owner, "Cadastro de Usuário", true);
        parent = (Interface_NewChat) owner;

        setLayout(new BorderLayout());

        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setBackground(Color.pink );
        cadastroPanel.setLayout(new GridLayout(4, 2));
        cadastroPanel.add(new JLabel("Nome: "));
        nomeField = new JTextField(20);
        cadastroPanel.add(nomeField);
        cadastroPanel.add(new JLabel("E-mail: "));
        emailField = new JTextField(20);
        cadastroPanel.add(emailField);
        cadastroPanel.add(new JLabel("Senha: "));
        senhaField = new JPasswordField(20);
        cadastroPanel.add(senhaField);
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });
        cadastroPanel.add(cadastrarButton);
        add(cadastroPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(owner);
    }

    public void cadastrar() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        parent.cadastrar(nome, email, senha);

        dispose();
    }
}