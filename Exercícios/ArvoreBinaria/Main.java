import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\A");
        String entradaCompleta = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        
        ArvBin binTree = new ArvBin(1000);
        ArvBal balTree = new ArvBal(1000);
        ArvAVL avlTree = new ArvAVL(1000);

        String[] linhas = entradaCompleta.split("\n");
        int i = 1;
        for (String linha : linhas) {
            String[] partes = linha.split(" ", 2);
            char comando = partes[0].charAt(0);
            String nome = partes[1].trim();

            if (comando == 'i') {
                binTree.insert(nome);
                balTree.insert(nome);
                avlTree.insert(nome);
            } else {
                binTree.remove(nome);
                balTree.remove(nome);
                avlTree.remove(nome);
            }
            System.out.println(i++);
            System.out.println(binTree);
        }

        System.out.println(binTree);
        System.out.println(balTree);
        System.out.println(avlTree);
    }
}