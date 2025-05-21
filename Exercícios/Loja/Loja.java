import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Loja {
    private Produto[] produtos;
    int items = 0;

    public Loja (int size) {
        produtos = new Produto[size];
    }

    public void inserirProduto (String[] input) {
        switch (input[1]) {
            case "Livro":
                System.out.println("Operação inserir livro: " + Long.parseLong(input[2]));
                
                if (procurarProduto(input[2]) != -1) {
                    System.out.println("***Erro: Código já cadastrado: " + Long.parseLong(input[2]));
                    return;
                }

                produtos[items] = new Produto.Livro(input);
                items++;
                break;

            case "CD":
                System.out.println("Operação inserir CD: " + Long.parseLong(input[2]));

                if (procurarProduto(input[2]) != -1) {
                    System.out.println("***Erro: Código já cadastrado: " + Long.parseLong(input[2]));
                    return;
                }

                produtos[items] = new Produto.CD(input);
                items++;
                break;

            case "DVD":
                System.out.println("Operação inserir DVD: " + Long.parseLong(input[2]));

                if (procurarProduto(input[2]) != -1) {
                    System.out.println("***Erro: Código já cadastrado: " + Long.parseLong(input[2]));
                    return;
                }

                produtos[items] = new Produto.DVD(input);
                items++;
                break;
        }

        System.out.println("Operação realizada com sucesso");
    }

    public int procurarProduto(String chave) {
        try {
            long codigo = Long.parseLong(chave);
            for (int i = 0; i < items; i++) {
                if (codigo == produtos[i].codigo)
                    return i;
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < items; i++) {
                if (chave.equals(produtos[i].titulo))
                    return i;
            }
        }

        return -1;
    }

    public void adicionarProduto (String[] input) {
        System.out.println("Operação de compra: " + Long.parseLong(input[1]));

        int index = procurarProduto(input[1]);
        if (index != -1) {
            produtos[index].qtd += Integer.parseInt(input[2]);
            System.out.println("Operação realizada com sucesso: " + Long.parseLong(input[1]));
        } else
            System.out.println("***Erro: Código inexistente: " + Long.parseLong(input[1]));
    }

    public void venderProduto (String[] input) {
        System.out.println("Operação de venda: " + Long.parseLong(input[1]));

        int index = procurarProduto(input[1]);
        int qtd = Integer.parseInt(input[2]);
        if (index != -1) {
            if (produtos[index].qtd >= qtd) {
               System.out.println("Operação realizada com sucesso: " + Long.parseLong(input[1]));
               produtos[index].qtd -= qtd;
            } else
                System.out.println("***Erro: Estoque insuficiente: " + Long.parseLong(input[1]) + " Quantidade: " + qtd); 
        } else
            System.out.println("***Erro: Código inexistente: " + Long.parseLong(input[1]));
    }

    public void printarProduto (String[] input) {
        System.out.println("Procurando: " + input[1]);

        int index = procurarProduto(input[1]);
        if (index != -1) {
            System.out.println("Encontrado:");
            System.out.println(produtos[index]);
        } else 
            System.out.println("Produto não encontrado: " + input[1]);
    }

    public void sumarioProduto (String[] input) {
        System.out.println("Operação de sumarização: ");

        int qtdTotal = 0;
        String[] tipos = {"Livro", "CD", "DVD"};

        for (String tipo : tipos) {
            for (int i = 0; i < items; i++) {
                String tipoAtual = produtos[i].getClass().getSimpleName();

                if (tipoAtual.equals(tipo)) {
                    System.out.println(produtos[i]);
                    System.out.println("Quantidade: " + produtos[i].qtd + "\n");
                    qtdTotal += produtos[i].qtd;
                }
            }

            System.out.println("Quantidade de " + tipo + "s: " + qtdTotal + "\n");
            qtdTotal = 0;
        }
    }
}