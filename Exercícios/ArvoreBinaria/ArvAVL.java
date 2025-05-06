import java.util.Arrays;

public class ArvAVL extends ArvBin {

    // Construtor
    public ArvAVL(int len) {
        super(len);
    }

    /**
     * Faz o balanceamento da sub-árvore de raiz de índice i
     * @param i - Índice da raiz da sub-árvore
     */
    private void balanceTree(int i) {
        if (i >= tree.length || tree[i] == null)
            return;

        int balanceFactor = calculateBalance(i);

        // Desbalanceamento para a direita
        if (balanceFactor == -2) {
            if (calculateBalance(nodeRight(i)) <= 0)
                rotateLeft(i);
            else {
                rotateRight(nodeRight(i));
                rotateLeft(i);
            }
        
        // Desbalanceamento para a esquerda
        } else if (balanceFactor == 2) {
            if (calculateBalance(nodeLeft(i)) >= 0)
                rotateRight(i);
            else {
                rotateLeft(nodeLeft(i));
                rotateRight(i);
            }
        }

        // Balanceia recursivamente até a raiz
        if (i != 0)
            balanceTree(parent(i));
    }

    /**
     * Faz a rotação para a direita da sub-árvore de raiz de índice i
     * @param i - Índice da raiz da sub-árvore
     */
    private void rotateRight(int i) {
        int l = nodeLeft(i);
        if (l >= tree.length || tree[l] == null) return;

        String[] temp = Arrays.copyOf(tree, tree.length);

        String oldRoot = tree[i];
        String newRoot = tree[l];

        int ll = nodeLeft(l);   // filho esquerdo do filho esquerdo (L)
        int lr = nodeRight(l);  // filho direito do filho esquerdo (L)
        int r = nodeRight(i);   // subárvore direita do antigo nó i

        // Limpa a subárvore atual
        clearSubtree(i);

        // Define a nova raiz
        tree[i] = newRoot;

        // Define o antigo root como filho direito
        int newRight = nodeRight(i);
        if (newRight < tree.length)
            tree[newRight] = oldRoot;

        // Copia subárvore direita de L (lr) para filho esquerdo do novo right
        if (lr < temp.length && temp[lr] != null)
            copySubtreeFrom(temp, lr, nodeLeft(newRight));

        // Copia subárvore direita antiga de i (r) para filho direito do novo right
        if (r < temp.length && temp[r] != null)
            copySubtreeFrom(temp, r, nodeRight(newRight));

        // Copia subárvore esquerda de L (ll) para filho esquerdo da nova raiz
        if (ll < temp.length && temp[ll] != null)
            copySubtreeFrom(temp, ll, nodeLeft(i));
    }

    /**
     * Faz a rotação para a esquerda da sub-árvore de raiz de índice i
     * @param i - Índice da raiz da sub-árvore
     */
    private void rotateLeft(int i) {
        int r = nodeRight(i);
        if (r >= tree.length || tree[r] == null) return;

        String[] temp = Arrays.copyOf(tree, tree.length);

        String oldRoot = tree[i];
        String newRoot = tree[r];

        int rl = nodeLeft(r);   // filho esquerdo do filho direito
        int rr = nodeRight(r);  // filho direito do filho direito
        int l = nodeLeft(i);    // filho esquerdo da raiz original

        // Limpa o espaço atual
        clearSubtree(i);

        // Coloca nova raiz
        tree[i] = newRoot;

        // Filho esquerdo da nova raiz: antigo root
        int newLeft = nodeLeft(i);
        if (newLeft < tree.length)
            tree[newLeft] = oldRoot;

        // Copia subárvore esquerda original para filho esquerdo do novo left
        if (l < temp.length && temp[l] != null)
            copySubtreeFrom(temp, l, nodeLeft(newLeft));

        // Copia subárvore esquerda de R (rl) para filho direito do novo left
        if (rl < temp.length && temp[rl] != null)
            copySubtreeFrom(temp, rl, nodeRight(newLeft));

        // Copia subárvore direita de R (rr) para filho direito da nova raiz
        if (rr < temp.length && temp[rr] != null)
            copySubtreeFrom(temp, rr, nodeRight(i));
    }

    /**
     * Copia a sub-árvore de um índice para outro
     * @param source - Cópia da árvore
     * @param from - Índice de origem
     * @param to - Índice de destino
     */
    private void copySubtreeFrom(String[] source, int from, int to) {
        if (from >= source.length || source[from] == null || to >= tree.length)
            return;

        tree[to] = source[from];
        copySubtreeFrom(source, nodeLeft(from), nodeLeft(to));
        copySubtreeFrom(source, nodeRight(from), nodeRight(to));
    }

    /**
     * Limpa a sub-árvore de índice i
     * @param i - Índice da raiz da sub-árvore
     */
    private void clearSubtree(int i) {
        if (i >= tree.length || tree[i] == null)
            return;

        clearSubtree(nodeLeft(i));
        clearSubtree(nodeRight(i));
        tree[i] = null;
    }

    /**
     * Calcula o fator de balanceamento de um nó
     * @param i - Índice do nó.
     * @return - Inteiro do fator de balanceamento.
     */
    protected int calculateBalance(int i) {
        return calculateHeight(nodeLeft(i)) - calculateHeight(nodeRight(i));
    }

    /**
     * Calcula a altura do nó.
     * @param i - Índice do nó.
     * @return - Inteiro da altura do nó.
     */
    protected int calculateHeight(int i) {
        if (i < tree.length && tree[i] != null)
            return Math.max(calculateHeight(nodeLeft(i)), calculateHeight(nodeRight(i))) + 1;
        else
            return -1;
    }

    /**
     * Checa se uma sub-árvore é balanceada
     * @param i - Índice do nó raiz.
     * @return - Booleano se a árvore está balanceada ou não.
     */
    @Override
    protected boolean isBalance(int i) {
        if (i < tree.length && tree[i] != null) {
            return Math.abs(calculateHeight(nodeLeft(i)) - calculateHeight(nodeRight(i))) <= 1
            && isBalance(nodeLeft(i))
            && isBalance(nodeRight(i));
        } else
            return true;
    }

    // Insere e balanceia.
    @Override
    public void insert(String value) {
        super.insert(value);
        balanceTree(Arrays.asList(tree).indexOf(value));
    }
    
    // Remoção com a mesma lógica da superclasse, com a diferença de que não faz a verificação do sucessor.
    @Override
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

        if (hasntLeft) {
            adjust(nodeRight(index), nodeRight(index) - index); // Ajusta a subárvore da direita
            return true;
        }
        if (hasntRight) {
            adjust(nodeLeft(index), nodeLeft(index) - index); // Ajusta a subárvore da esquerda
            return true;
        }

        // Encontra o maior da sub-árvore esquerda
        int successor = nodeLeft(index);
        while (nodeRight(successor) < tree.length && tree[nodeRight(successor)] != null)
            successor = nodeRight(successor);

        // Pega o valor do sucessor e remove ele da árvore
        String successorValue = tree[successor];

        hasntLeft = nodeLeft(successor) >= tree.length || tree[nodeLeft(successor)] == null;
        hasntRight = nodeRight(successor) >= tree.length || tree[nodeRight(successor)] == null;

        if (hasntLeft) {
            adjust(nodeRight(successor), nodeRight(successor) - successor);
            return true;
        }
        if (hasntRight) {
            adjust(nodeLeft(successor), nodeLeft(successor) - successor);
            return true;
        }
        
        // Sucessor original é removido e o índice do valor removido recebe o antigo sucessor
        tree[index] = successorValue;
        return true;
    }
}