import java.util.List;

public class RemoverAmigo {

    public static void RemoverAmigo(Interface_NewChat.Usuario usuario, Interface_NewChat.Usuario amigoParaRemover) {
        if (usuario != null && amigoParaRemover != null) {
            List<Interface_NewChat.Usuario> amigos = usuario.getAmigos();
            if (amigos.contains(amigoParaRemover)) {
                amigos.remove(amigoParaRemover);
                System.out.println(amigoParaRemover.getNome() + " foi removido da lista de amigos de " + usuario.getNome());
            } else {
                System.out.println(amigoParaRemover.getNome() + " não está na lista de amigos de " + usuario.getNome());
            }
        } else {
            System.out.println("Usuário ou amigo a ser removido é nulo.");
        }
    }
}