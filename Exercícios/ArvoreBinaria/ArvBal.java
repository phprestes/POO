import java.util.Arrays;
import java.util.Objects;

public class ArvBal extends ArvBin {
    // Construtor
    public ArvBal(int len) {
        super(len);
    }

    /**
     * Balanceia a árvore reconstruindo ela completamente.
     */
    private void balanceTree() {
        if (!isBalance()) {
            String[] nonNullArray = Arrays.stream(tree)
                                        .filter(Objects::nonNull)
                                        .sorted()
                                        .toArray(String[]::new);
            
            Arrays.fill(tree, null);
            rebuildTree(nonNullArray, 0, nonNullArray.length - 1, 0);
        }
    }

    /**
     * Função de reconstrução da árvore.
     * @param array - Array de elementos a serem inseridos
     * @param start - Começo da partição do array para recursão.
     * @param end - Fim da partição do array para recursão.
     */
    private void rebuildTree(String[] array, int start, int end) {
        if (start > end || treeIndex >= tree.length)
            return;
        
        int mid = start + (end - start) / 2;
        
        super.insert(array[mid]);
        
        rebuildTree(array, start, mid - 1);
        rebuildTree(array, mid + 1, end); 
    }

    // Mesma lógica da inserção da superclasse, mas faz o balanceamento
    @Override
    public void insert(String value) {
        super.insert(value);
        balanceTree();
    }

    // Mesma lógica da remoção da superclasse, mas faz o balanceamento
    @Override
    public boolean remove(String v) {
        if (super.remove(v)) {
            balanceTree();
            return true;
        }

        return false;
    }
}