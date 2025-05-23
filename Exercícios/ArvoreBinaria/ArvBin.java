import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

public class ArvBin {
    protected String[] tree;
    
    /**
     * Construtor da classe Árvore Binária
     * @param len - Tamanho do vetor da árvore a ser criada.
     */
    public ArvBin(int len) {
        tree = new String[len];
    }

    /**
     * Diz se um dado elemento está presente na árvore ou não.
     * @param v - Elemento a ser procurado.
     * @return True se o elemento está presente e False se não.
     */
    public boolean find(String v) {
        return Arrays.asList(tree).contains(v); // 
    }

    /**
     * Insere no vetor da ABB, comparando com os valores na árvore e adiciona na posição correta
     * @param value - Valor a ser adicionado na árvore
     */
    public void insert(String value) {
        int i = 0;
        
        while (i < tree.length && tree[i] != null) {
            int res = value.compareTo(tree[i]);
            if (res == 0) return;
            i = res < 0 ? nodeLeft(i) : nodeRight(i);
        }

        if (i < tree.length)
            tree[i] = value;
    }

    /**
     * Retorna a quantidade de elementos da árvore.
     * @return Inteiro da quantidade de elementos da árvore.
     */
    public int len() {
        return countNodes(0);
    }

    /**
     * Remove um nó dado um valor da árvore
     * @param v Valor do nó a ser removido
     * @return booleano que indica se a remoção foi bem sucedida.
     */
    public boolean remove(String v) {
        int index = Arrays.asList(tree).indexOf(v);
        if (index == -1) return false;

        boolean hasntLeft = nodeLeft(index) >= tree.length || tree[nodeLeft(index)] == null; // Indica se não tem o filho da esquerdo
        boolean hasntRight = nodeRight(index) >= tree.length || tree[nodeRight(index)] == null; // Indica se não tem o filho da direita

        if (hasntLeft && hasntRight) {
            tree[index] = null;
            return true;
        }
        else if (hasntLeft || hasntRight) {
            int subTreeRootOld = hasntLeft ? nodeRight(index) : nodeLeft(index); // Posição original do filho
            int subTreeRootNew = index; // Nova posição (substitui o nó removido)

            // Promove o filho para a posição do pai
            tree[subTreeRootNew] = tree[subTreeRootOld];
            tree[subTreeRootOld] = null;

            // Fila armazena pares [posição antiga, nova posição]
            Queue<int[]> queue = new LinkedList<>();

            // Adiciona os filhos do subTreeRootOld à fila, com suas novas posições esperadas
            int oldLeft = nodeLeft(subTreeRootOld);
            int newLeft = nodeLeft(subTreeRootNew);
            if (oldLeft < tree.length && tree[oldLeft] != null) {
                queue.add(new int[]{oldLeft, newLeft});
            }

            int oldRight = nodeRight(subTreeRootOld);
            int newRight = nodeRight(subTreeRootNew);
            if (oldRight < tree.length && tree[oldRight] != null) {
                queue.add(new int[]{oldRight, newRight});
            }

            // Processa cada nó da subárvore
            while (!queue.isEmpty()) {
                int[] positions = queue.poll();
                int currentOld = positions[0];
                int currentNew = positions[1];

                // Move o nó para a nova posição
                tree[currentNew] = tree[currentOld];
                tree[currentOld] = null;

                // Calcula as novas posições dos filhos baseado na currentNew
                int nextOldLeft = nodeLeft(currentOld);
                int nextNewLeft = nodeLeft(currentNew);
                if (nextOldLeft < tree.length && tree[nextOldLeft] != null) {
                    queue.add(new int[]{nextOldLeft, nextNewLeft});
                }

                int nextOldRight = nodeRight(currentOld);
                int nextNewRight = nodeRight(currentNew);
                if (nextOldRight < tree.length && tree[nextOldRight] != null) {
                    queue.add(new int[]{nextOldRight, nextNewRight});
                }
            }
            
            return true;
        }
        // Encontra o maior da sub-árvore esquerda
        int successor_esq = nodeLeft(index);
        while (nodeRight(successor_esq) < tree.length && tree[nodeRight(successor_esq)] != null)
            successor_esq = nodeRight(successor_esq);

        // Encontra o menor da sub-árvore direita
        int successor = nodeRight(index);
        while (nodeLeft(successor) < tree.length && tree[nodeLeft(successor)] != null)
            successor = nodeLeft(successor);

        // Usa o sucessor mais perto
        if (Math.abs(index - successor_esq) < Math.abs(index - successor)) 
            successor = successor_esq;
        
        // Pega o valor do sucessor e remove ele da árvore
        String successorValue = tree[successor];

        hasntLeft = nodeLeft(successor) >= tree.length || tree[nodeLeft(successor)] == null;
        hasntRight = nodeRight(successor) >= tree.length || tree[nodeRight(successor)] == null;

        if (hasntLeft && hasntRight) {
            tree[successor] = null;
        }
        else if (hasntLeft) {
            String[] temp = Arrays.copyOf(tree, tree.length);
            adjust(nodeRight(successor), nodeRight(successor) - successor, temp);
        }
        else if (hasntRight) {
            String[] temp = Arrays.copyOf(tree, tree.length);
            adjust(nodeLeft(successor), nodeLeft(successor) - successor, temp);
        }
        
        // Sucessor original é removido e o índice do valor removido recebe o antigo sucessor
        tree[index] = successorValue;
        return true;
    }

    /**
     * Ajusta as subárvores após remoção
     * @param filho - Filho do nó que foi removido.
     * @param diff - Diferença entre o pai e o filho, duplica a cada iteração.
     * @param temp - Param auxiliar da árvore
     */
    protected void adjust(int filho, int diff, String[] temp) {
        // System.out.println(Arrays.toString(tree)); // debug
        // System.out.println("temp" + Arrays.toString(temp)); // debug
        if (filho >= tree.length || temp[filho] == null)
            return;
        // System.out.printf("Movendo %s de %d para %d%n", temp[filho], filho, filho - diff); // debug
        
        tree[filho - diff] = temp[filho];
        if (tree[filho - diff].equals(tree[filho])) 
            tree[filho] = null;
        
        adjust(nodeLeft(filho), 2 * diff, temp);
        adjust(nodeRight(filho), 2 * diff, temp);
    }

    /**
     * Conta a quantidade de nós da árvore
     * @param i - Índice da raiz da sub-árvore
     * @return Inteiro da quantidade de nós da árvore.
     */
    protected int countNodes(int i) {
        if (i < tree.length && tree[i] != null)
            return countNodes(nodeLeft(i)) + countNodes(nodeRight(i)) + 1;
        else
            return 0;
    }

    /**
     * Dado um índice, retorna o valor do nó.
     * @param i - Índice fornecido.
     * @return Retorna o valor do nó.
     */
    protected String getNode(int i) {
        if (i < tree.length)
            return tree[i];
        else
            return null;
    }

    /**
     * Checa se a árvore é balanceada.
     * @return Retorna um booleano se a árvore é balanceada ou não.
     */
    protected boolean isBalance() {
        return isBalance(0);
    }

    /**
     * Checa se a sub-árvore é balanceada.
     * @param i - O indíce da raiz da sub-árvore a ser avaliada.
     * @return Retorna um booleano se a sub-árvore é balanceada ou não.
     */
    protected boolean isBalance(int i) {
        if (i < tree.length && tree[i] != null) {
            // Checa se a diferença da contagem de nós das sub-árvores é menor ou igual a 1 e checa as sub-árvores.
            return Math.abs(countNodes(nodeLeft(i)) - countNodes(nodeRight(i))) <= 1
            && isBalance(nodeLeft(i))
            && isBalance(nodeRight(i));
        } else
            return true;
    }

    // Retorna o índice filho esquerdo de um dado índice. 
    protected int nodeLeft(int i) {
        return 2*i + 1;
    }

    // Retorna o índice filho direito de um dado índice. 
    protected int nodeRight(int i) {
        return 2*i + 2;
    }

    // Retorna o índice pai de um dado índice. 
    protected int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Seta o valor de um nó de um dado índice para um valor fornecido
     * @param i - Índice do nó a ser mudado.
     * @param v - Valor que será inserido no nó.
     */
    protected void setNode(int i, String v) {
        tree[i] = v;
    }

    // Printa nos padrões do Graphviz
    @Override
    public String toString() {
        // System.out.println(Arrays.toString(tree)); // debug
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        
        if (tree[0] != null && (tree[1] == null && tree[2] == null))
            sb.append('"').append(0).append(' ').append(tree[0]).append("\" ");
        else
            for (int i = 0; i < tree.length; i++) {
                if (tree[i] != null) {
                    if (nodeLeft(i) < tree.length && tree[nodeLeft(i)] != null) {
                        sb.append('"').append(i).append(' ').append(tree[i]).append("\" ->\"").append(nodeLeft(i)).append(' ').append(tree[nodeLeft(i)]).append("\"\n");
                    }
                    
                    if (nodeRight(i) < tree.length && tree[nodeRight(i)] != null) {
                        sb.append('"').append(i).append(' ').append(tree[i]).append("\" ->\"").append(nodeRight(i)).append(' ').append(tree[nodeRight(i)]).append("\"\n");
                    }
                }
            }
        
        sb.append("}\n");
        return sb.toString();
    }
}