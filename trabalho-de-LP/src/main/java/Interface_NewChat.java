import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Interface_NewChat extends JFrame {

    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioLogado;
    private JTextField emailField, senhaField;
    private JButton loginButton, logoutButton, cadastrarButton, enviarMensagemButton, adicionarAmigoButton, removerAmigoButton;
    private JTextArea amigosList;
    private JComboBox<Usuario> amigoComboBox;
    private JTextField mensagemField;

    public Interface_NewChat() {
        setTitle("NEW-CHAT");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2));
        loginPanel.setBackground(Color.pink);
        loginPanel.add(new JLabel("E-mail: "));
        emailField = new JTextField(20);
        loginPanel.add(emailField);
        loginPanel.add(new JLabel("Senha: "));
        senhaField = new JPasswordField(20);
        loginPanel.add(senhaField);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        loginPanel.add(loginButton);
        logoutButton = new JButton("Logout");
        logoutButton.setEnabled(false);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        loginPanel.add(logoutButton);
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
            }
        });
        loginPanel.add(cadastrarButton);
        add(loginPanel, BorderLayout.NORTH);

        amigosList = new JTextArea(10, 30);
        amigosList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(amigosList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel amigoPanel = new JPanel();
        amigoPanel.setLayout(new GridLayout(3, 2));
        amigoPanel.add(new JLabel("Amigo: "));
        amigoComboBox = new JComboBox<>();
        amigoPanel.add(amigoComboBox);
        enviarMensagemButton = new JButton("Enviar");
        enviarMensagemButton.setEnabled(false);
        enviarMensagemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        });
        amigoPanel.add(enviarMensagemButton);

        amigoPanel.add(new JLabel("Mensagem: "));
        mensagemField = new JTextField();
        amigoPanel.add(mensagemField);

        adicionarAmigoButton = new JButton("Adicionar Amigo");
        adicionarAmigoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaAdicionarAmigo();
            }
        });
        amigoPanel.add(adicionarAmigoButton);

        removerAmigoButton = new JButton("Remover Amigo");
        removerAmigoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerAmigo();
            }
        });
        amigoPanel.add(removerAmigoButton);

        add(amigoPanel, BorderLayout.SOUTH);
    }

    private void abrirTelaCadastro() {
        CadastroDialog cadastroDialog = new CadastroDialog(this);
        cadastroDialog.setVisible(true);
    }

    private void abrirTelaAdicionarAmigo() {
        AdicionarAmigos dialog = new AdicionarAmigos(this);
        dialog.setVisible(true);
    }

    // Adicione este método à classe Interface_NewChat



    public void cadastrar(String nome, String email, String senha) {
        if (nome.trim().isEmpty() || email.trim().isEmpty() || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!validarFormatoEmail(email)) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um endereço de e-mail válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (senha.length() < 6 || senha.length() > 8) {
            JOptionPane.showMessageDialog(this, "A senha deve ter entre 6 e 8 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Usuario novoUsuario = new Usuario(nome, email, senha);
            usuarios.add(novoUsuario);
            JOptionPane.showMessageDialog(this, "Cadastro bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validarFormatoEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(regex);
    }

    public void login() {
        String inputEmail = emailField.getText();
        String inputSenha = senhaField.getText();

        boolean loginValido = false;
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(inputEmail) && user.getSenha().equals(inputSenha)) {
                usuarioLogado = user;
                loginValido = true;
                break;
            }
        }

        if (loginValido) {
            loginButton.setEnabled(false);
            logoutButton.setEnabled(true);
            enviarMensagemButton.setEnabled(true);
            adicionarAmigoButton.setEnabled(true);
            atualizarComboBoxAmigos();
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciais inválidas. Tente novamente.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void logout() {
        usuarioLogado = null;
        loginButton.setEnabled(true);
        logoutButton.setEnabled(false);
        enviarMensagemButton.setEnabled(false);
        adicionarAmigoButton.setEnabled(false);
        amigoComboBox.removeAllItems();
        amigosList.setText("");
    }

    public void enviarMensagem() {
        if (usuarioLogado != null) {
            if (mensagemField != null && !mensagemField.getText().isEmpty() && amigoComboBox.getSelectedItem() != null) {
                Usuario amigoSelecionado = (Usuario) amigoComboBox.getSelectedItem();
                if (amigoSelecionado != null && usuarioLogado.getPossuiAmigo(amigoSelecionado)) {
                    String mensagem = mensagemField.getText();
                    if (mensagem.length() <= 90) {
                        JOptionPane.showMessageDialog(this, "Mensagem enviada para " + amigoSelecionado.getNome() + ": " + mensagem, "Mensagem Enviada", JOptionPane.INFORMATION_MESSAGE);
                        mensagemField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "A mensagem excede o limite de 90 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Você só pode enviar mensagem para amigos adicionados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um amigo e digite uma mensagem válida.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Você precisa estar logado para enviar uma mensagem.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void adicionarAmigo(String emailAmigo) {
        Usuario amigoParaAdicionar = null;

        // Verifica se o amigo a ser adicionado está cadastrado
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(emailAmigo)) {
                amigoParaAdicionar = usuario;
                break;
            }
        }

        if (amigoParaAdicionar != null) {
            // Verifica se o amigo já está na lista de amigos
            if (!usuarioLogado.getPossuiAmigo(amigoParaAdicionar)) {
                usuarioLogado.adicionarAmigo(amigoParaAdicionar);
                atualizarComboBoxAmigos();
                JOptionPane.showMessageDialog(this, amigoParaAdicionar.getNome() + " foi adicionado aos seus amigos.", "Amigo Adicionado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Este usuário já está na sua lista de amigos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "O amigo não está cadastrado no sistema.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removerAmigo() {
        if (usuarioLogado != null) {
            if (amigoComboBox.getSelectedItem() != null) {
                Usuario amigoSelecionado = (Usuario) amigoComboBox.getSelectedItem();

                if (usuarioLogado.getPossuiAmigo(amigoSelecionado)) {
                    usuarioLogado.removerAmigo(amigoSelecionado);
                    atualizarComboBoxAmigos();
                    JOptionPane.showMessageDialog(this, amigoSelecionado.getNome() + " foi removido dos seus amigos.", "Amigo Removido", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Esse usuário não está na sua lista de amigos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um amigo para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Você precisa estar logado para remover um amigo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarComboBoxAmigos() {
        amigoComboBox.removeAllItems();
        for (Usuario amigo : usuarioLogado.getAmigos()) {
            amigoComboBox.addItem(amigo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Interface_NewChat sistema = new Interface_NewChat();
                sistema.setVisible(true);
            }
        });
    }

    public List<Usuario> getUsuariosLogados() {
        return usuarios;
    }

    public class Usuario {
        private String nome;
        private String email;
        private String senha;
        private List<Usuario> amigos;

        public Usuario(String nome, String email, String senha) {
            this.nome = nome;
            this.email = email;
            this.senha = senha;
            this.amigos = new ArrayList<>();
        }

        public void adicionarAmigo(Usuario amigo) {
            amigos.add(amigo);
        }

        public List<Usuario> getAmigos() {
            return amigos;
        }

        public boolean getPossuiAmigo(Usuario amigo) {
            return amigos.contains(amigo);
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public String getSenha() {
            return senha;
        }

        public void removerAmigo(Usuario amigo) {
            amigos.remove(amigo);
        }
    }
}