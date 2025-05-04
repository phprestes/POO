import java.util.Arrays;

public class ArvBin {
    protected String[] tree;

    public ArvBin(int len) {
        tree = new String[len];
    }

    public boolean find(String v) {
        return Arrays.asList(tree).contains(v);
    }

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

    public int len() {
        return tree.length;
    }

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
                return true;
            }
        }

        int successor1 = nodeLeft(index);
        while (nodeRight(successor1) < tree.length && tree[nodeRight(successor1)] != null) {
            successor1 = nodeRight(successor1);
        }

        int successor = nodeRight(index);
        while (nodeLeft(successor) < tree.length && tree[nodeLeft(successor)] != null) {
            successor = nodeLeft(successor);
        }

        if (Math.abs(index - successor1) < Math.abs(index - successor)) 
            successor = successor1;

        String successorValue = tree[successor];

        hasntLeft = nodeLeft(successor) >= tree.length || tree[nodeLeft(successor)] == null;
        hasntRight = nodeRight(successor) >= tree.length || tree[nodeRight(successor)] == null;

        if (hasntLeft || hasntRight) {
            if (hasntLeft) {
                while (!(hasntLeft && hasntRight)) {
                    tree[successor] = nodeRight(successor) < tree.length ? tree[nodeRight(successor)] : null;
                    tree[nodeLeft(successor)] = nodeLeft(nodeRight(successor)) < tree.length ? tree[nodeLeft(nodeRight(successor))] : null;
                    successor = nodeRight(successor);

                    hasntLeft = nodeLeft(successor) >= tree.length || tree[nodeLeft(successor)] == null;
                    hasntRight = nodeRight(successor) >= tree.length || tree[nodeRight(successor)] == null;
                }
            }

            if (hasntRight) {
                while (!(hasntLeft && hasntRight)) {
                    tree[successor] = nodeLeft(successor) < tree.length ? tree[nodeLeft(successor)] : null;
                    tree[nodeRight(successor)] = nodeRight(nodeLeft(successor)) < tree.length ? tree[nodeRight(nodeLeft(successor))] : null;
                    successor = nodeLeft(successor);

                    hasntLeft = nodeLeft(successor) >= tree.length || tree[nodeLeft(successor)] == null;
                    hasntRight = nodeRight(successor) >= tree.length || tree[nodeRight(successor)] == null;
                }
            }
        }
        
        tree[successor] = null;
        tree[index] = successorValue;
        return true;
    }

    protected int countNodes(int i) {
        if (i < tree.length && tree[i] != null)
            return countNodes(nodeLeft(i)) + countNodes(nodeRight(i)) + 1;
        else
            return 0;
    }

    protected String getNode(int i) {
        if (i < tree.length)
            return tree[i];
        else
            return null;
    }

    protected boolean isBalance() {
        return isBalance(0);
    }

    protected boolean isBalance(int i) {
        if (i < tree.length && tree[i] != null) {
            return Math.abs(countNodes(nodeLeft(i)) - countNodes(nodeRight(i))) <= 1
            && isBalance(nodeLeft(i))
            && isBalance(nodeRight(i));
        } else
            return true;
    }

    protected int nodeLeft(int i) {
        return 2*i + 1;
    }

    protected int nodeRight(int i) {
        return 2*i + 2;
    }

    protected void setNode(int i, String v) {
        tree[i] = v;
    }

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