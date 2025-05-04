import java.util.Arrays;

public class ArvAVL extends ArvBin {
    public ArvAVL(int len) {
        super(len);
    }

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
            balanceTree((i - 1)/2);
    }

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


    private void rotateLeft(int i) {
        int r = nodeRight(i);
        if (r >= tree.length || tree[r] == null) return;

        String[] temp = Arrays.copyOf(tree, tree.length);

        String oldRoot = tree[i];
        String newRoot = tree[r];

        int rl = nodeLeft(r);
        int rr = nodeRight(r);
        int l = nodeLeft(i);

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

    private void copySubtreeFrom(String[] source, int from, int to) {
        if (from >= source.length || source[from] == null || to >= tree.length)
            return;

        tree[to] = source[from];
        copySubtreeFrom(source, nodeLeft(from), nodeLeft(to));
        copySubtreeFrom(source, nodeRight(from), nodeRight(to));
    }

    private void clearSubtree(int i) {
        if (i >= tree.length || tree[i] == null)
            return;

        clearSubtree(nodeLeft(i));
        clearSubtree(nodeRight(i));
        tree[i] = null;
    }

    protected int calculateBalance(int i) {
        return calculateHeight(nodeLeft(i)) - calculateHeight(nodeRight(i));
    }

    protected int calculateHeight(int i) {
        if (i < tree.length && tree[i] != null)
            return Math.max(calculateHeight(nodeLeft(i)), calculateHeight(nodeRight(i))) + 1;
        else
            return -1;
    }

    @Override
    protected boolean isBalance(int i) {
        if (i < tree.length && tree[i] != null) {
            return Math.abs(calculateHeight(nodeLeft(i)) - calculateHeight(nodeRight(i))) <= 1
            && isBalance(nodeLeft(i))
            && isBalance(nodeRight(i));
        } else
            return true;
    }

    @Override
    public void insert(String value) {
        super.insert(value);
        balanceTree(Arrays.asList(tree).indexOf(value));
    }

    @Override
    public boolean remove(String v) {
        int index = Arrays.asList(tree).indexOf(v);
        if (index == -1) return false;

        boolean hasntLeft = nodeLeft(index) >= tree.length || tree[nodeLeft(index)] == null;
        boolean hasntRight = nodeRight(index) >= tree.length || tree[nodeRight(index)] == null;

        if (hasntLeft || hasntRight) {
            if (hasntLeft) {
                while (!(hasntLeft && hasntRight)) {
                    tree[index] = nodeRight(index) < tree.length ? tree[nodeRight(index)] : null;
                    tree[nodeLeft(index)] = nodeLeft(nodeRight(index)) < tree.length ? tree[nodeLeft(nodeRight(index))] : null;
                    index = nodeRight(index);

                    hasntLeft = nodeLeft(index) >= tree.length || tree[nodeLeft(index)] == null;
                    hasntRight = nodeRight(index) >= tree.length || tree[nodeRight(index)] == null;
                }

                tree[index] = null;
                balanceTree(index);
                return true;
            }

            if (hasntRight) {
                while (!(hasntLeft && hasntRight)) {
                    tree[index] = nodeLeft(index) < tree.length ? tree[nodeLeft(index)] : null;
                    tree[nodeRight(index)] = nodeRight(nodeLeft(index)) < tree.length ? tree[nodeRight(nodeLeft(index))] : null;
                    index = nodeLeft(index);

                    hasntLeft = nodeLeft(index) >= tree.length || tree[nodeLeft(index)] == null;
                    hasntRight = nodeRight(index) >= tree.length || tree[nodeRight(index)] == null;
                }

                tree[index] = null;
                balanceTree(index);
                return true;
            }
        }

        int successor = nodeLeft(index);
        while (nodeRight(successor) < tree.length && tree[nodeRight(successor)] != null) {
            successor = nodeRight(successor);
        }

        String successorValue = tree[successor];

        hasntLeft = nodeLeft(successor) >= tree.length || tree[nodeLeft(successor)] == null;
        hasntRight = nodeRight(successor) >= tree.length || tree[nodeRight(successor)] == null;

        if (hasntRight) {
            while (!(hasntLeft && hasntRight)) {
                tree[successor] = nodeLeft(successor) < tree.length ? tree[nodeLeft(successor)] : null;
                tree[nodeRight(successor)] = nodeRight(nodeLeft(successor)) < tree.length ? tree[nodeRight(nodeLeft(successor))] : null;
                successor = nodeLeft(successor);

                hasntLeft = nodeLeft(successor) >= tree.length || tree[nodeLeft(successor)] == null;
                hasntRight = nodeRight(successor) >= tree.length || tree[nodeRight(successor)] == null;
            }
        }
        
        tree[successor] = null;
        tree[index] = successorValue;
        balanceTree(index);
        return true;
    }
}