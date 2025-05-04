import java.util.Scanner;
import java.util.Arrays;

public class Tabuleiro {
    private String strlinha = "+------";
    private int[][] tabuleiro; // Matriz representando o tabuleiro
    private int tamanho; // Tamanho de cada linha do tabuleiro
    private int[] pos_zero = new int[2]; // Posição do zero no tabuleiro

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada_nums = scanner.nextLine(); // Lê a ordem dos números no tabuleiro
        String entrada_cmds = scanner.nextLine(); // Lê os comandos que serão feitos no jogo.

        int[] nums = Arrays.stream(entrada_nums.split(" ")).mapToInt(Integer::parseInt).toArray(); // Converte a string para um array de números

        Tabuleiro jogo = new Tabuleiro(nums, (int) Math.sqrt(nums.length)); // Inicializa o jogo

        for (char cmd : entrada_cmds.toCharArray()) // Converte a String em um array de chars
            jogo.mover(cmd); // Faz os movimentos baseado nos comandos
        
        System.out.println("Posicao final: " + jogo.checar()); // Checa se a posição final é a posição resolvida.
        scanner.close();
    }

    /* Construtor do tabuleiro
     * Recebe uma matriz de input para converter para uma matriz e o tamanho de cada linha */
    public Tabuleiro(int[] nums, int tamanho) {
        this.tabuleiro = new int[tamanho][tamanho];
        this.tamanho = tamanho;

        int k = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = nums[k];
                k++;

                if (tabuleiro[i][j] == 0) {
                    pos_zero[0] = i;
                    pos_zero[1] = j;
                }
            }
        }

        printar();
    }

    /* Faz um movimento nas peças do tabuleiro dependendo do comando: 'd' 'u' 'l' 'r' */
    public void mover(char cmd){
        switch (cmd) { // Move respectiva peça para a posição do zero
            case 'd': // Pega uma peça acima e move para baixo 
                if(pos_zero[0] > 0)
                    tabuleiro[pos_zero[0]][pos_zero[1]] = tabuleiro[--pos_zero[0]][pos_zero[1]]; 
                break;
            case 'u': // Pega uma peça abaixo e move para cima
                if(pos_zero[0] < tamanho - 1)
                    tabuleiro[pos_zero[0]][pos_zero[1]] = tabuleiro[++pos_zero[0]][pos_zero[1]]; 
                break;
            case 'l': // Pega uma peça a direita e move para a esquerda
                if(pos_zero[1] < tamanho - 1)
                    tabuleiro[pos_zero[0]][pos_zero[1]] = tabuleiro[pos_zero[0]][++pos_zero[1]]; 
                break;
            case 'r': // Pega uma peça a esquerda e move para a direita 
                if(pos_zero[1] > 0)
                    tabuleiro[pos_zero[0]][pos_zero[1]] = tabuleiro[pos_zero[0]][--pos_zero[1]]; 
                break;
        }

        tabuleiro[pos_zero[0]][pos_zero[1]] = 0; // Atualiza a posição do zero.

        printar();
    }

    /* Checa se o tabuleiro está na posição resolvida. 
     * Retorna true se sim e false se não.              */
    public boolean checar(){
        int k = 0; // Intera sobre a matriz como se fosse uma lista, checando se as posições estão corretas
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] != k)
                    return false;
                
                k++;
            }
        }

        return true;
    }

    /* Printa o tabuleiro na formatação correta */
    public void printar(){
        for (int i = 0; i < tamanho; i++){
            for (int j = 0; j < tamanho; j++){
                System.out.print(strlinha);
            }

            System.out.print("+\n");

            for (int j = 0; j < tamanho; j++){
                System.out.print("|");
                if (tabuleiro[i][j] / 10 == 0)
                     System.out.print("   ");
                else
                    System.out.print("  ");

                System.out.print(tabuleiro[i][j] + "  ");
            }

            System.out.print("|\n");
        }

        for (int j = 0; j < tamanho; j++){
            System.out.print(strlinha);
        }

        System.out.println("+\n");
    }
}