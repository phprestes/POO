import java.util.Arrays;
import java.util.Objects;

public class ArvBal extends ArvBin {
    public ArvBal(int len) {
        super(len);
    }

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

    private void rebuildTree(String[] array, int start, int end, int treeIndex) {
        if (start > end || treeIndex >= tree.length)
            return;
        
        int mid = start + (end - start) / 2;
        
        tree[treeIndex] = array[mid];
        
        rebuildTree(array, start, mid - 1, nodeLeft(treeIndex));
        rebuildTree(array, mid + 1, end, nodeRight(treeIndex)); 
    }

    @Override
    public void insert(String value) {
        super.insert(value);
        balanceTree();
    }

    @Override
    public boolean remove(String v) {
        if (super.remove(v)) {
            balanceTree();
            return true;
        }

        return false;
    }
}