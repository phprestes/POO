import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Loja loja = new Loja(100);
        
        while (scanner.hasNextLine()) {
            System.out.println();
            
            String comando = scanner.nextLine();
            String[] input = comando.split(",");

            switch (input[0]) {
                case "I":
                    loja.inserirProduto(input);
                    break;
                case "A":
                    loja.adicionarProduto(input);
                    break;
                case "V":
                    loja.venderProduto(input);
                    break;
                case "P":
                    loja.printarProduto(input);
                    break;
                case "S":
                    loja.sumarioProduto(input);
                    break;
            }
        }

        scanner.close();
    }
}