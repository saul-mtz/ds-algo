package common.tree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SuffixTreeGraphviz extends SuffixTree {

    private static String GRAPHVIZ_FILENAME;
    private ArrayList<TreeNode> lastModified;
    private int subGraphIndex = 0;
    private TreeNode pending;

    public SuffixTreeGraphviz(String src) {
        super(src);
    }

    @Override
    protected void init(String src) {
        super.init(src);
        lastModified = new ArrayList<>();
        GRAPHVIZ_FILENAME = getGraphvizFileName() + ".gv";
    }

    @Override
    protected void build() {
        System.out.printf("Input String S=\"%s\", |S|=%d ...\n", text.toString().replace(SENTINEL, '$'), text.length());
        boolean error = false;
        try {
            String header = "digraph suffixTree {\n" +
                    "  labelloc=\"t\";\n" +
                    "  label=\"Suffix Tree for \\\"" + text.toString().replace(SENTINEL, '$') + "\\\"\";\n" +
                    "  rankdir=LR;\n" +
                    "  size=\"8,5\"\n" +
                    "  node [margin=0 fontcolor=blue fontsize=1 width=0.1 shape=circle style=filled];\n\n";
            new FileWriter(GRAPHVIZ_FILENAME);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(GRAPHVIZ_FILENAME, true))) {
                writer.write(header);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            super.build();
        } catch (AssertionError ae) {
            ae.printStackTrace();
            error = true;
        } catch (Exception e) {
            e.printStackTrace();
            error = true;
        } finally {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(GRAPHVIZ_FILENAME, true))) {
                writer.write("}");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            if (error) {
                System.exit(1);
            }
        }
    }

    @Override
    protected boolean processCharacter() {
        System.out.printf("Processing S[%d]='%c' ...\n", currentIndex, SENTINEL == text.charAt(currentIndex) ? '$' : text.charAt(currentIndex));
        return super.processCharacter();
    }

    @Override
    protected void ignoreChar() {
        super.ignoreChar();
        if (null != activePoint.getEdge()) {
            // highlight the active edge
            pending = (TreeNode) activePoint.getNode().getChild(activePoint.getEdge());
        }
        appendToGraph();
        pending = null;
    }

    @Override
    protected TreeNode addNode() {
        TreeNode node = (TreeNode) super.addNode();
        lastModified.add(node);
        appendToGraph();
        lastModified.clear();
        return node;
    }

    @Override
    protected void adjustActivePoint(boolean ignored, boolean split) {
        boolean graphAfter = false;
        if (ignored) {
            SuffixTree.TreeNode node = activePoint.getNode().getChild(activePoint.getEdge());
            int length1 = node.getLength();
            int length2 = activePoint.getLength();
            int length3 = length();
            // do we reach the end of the active edge?
            if (length2 >= length1) {
                graphAfter = true;
            }
        }

        super.adjustActivePoint(ignored, split);
        if (graphAfter) {
            appendToGraph();
        }
    }

    @Override
    protected SuffixTree.TreeNode createNode(SuffixTree.TreeNode parent, int from) {
        return new TreeNode(parent, from);
    }

    @Override
    protected SuffixTree.ActivePoint createActivePoint() {
        return new ActivePoint();
    }

    private void appendToGraph() {
        StringBuilder str = new StringBuilder();
        ArrayDeque<SuffixTree.TreeNode> queue = new ArrayDeque<>();
        HashMap<TreeNode, String> nodes = new HashMap<>();
        queue.add(root);
        int step = currentIndex + 1;

        String labelCluster = "Step " + step + ": S[" + currentIndex + "]=" + text.charAt(currentIndex) + ", AP=" + activePoint.toString() + ", remainder=" + remainder;
        str.append("  subgraph cluster_" + step + " {\n");
        str.append("    label=\"" + labelCluster.replace(SENTINEL, '$') + "\"\n\n");

        while (!queue.isEmpty()) {
            TreeNode node = (TreeNode) queue.remove();
            String nodeId = node.getId(step);

            if (!nodes.containsKey(node)) {
                nodes.put((TreeNode) node, nodeId);
            }

            if (!node.isRoot()) {
                String edgeLabel = node.getEdgeLabel();
                String props = "label = \"" + edgeLabel + "\"";

                if (lastModified.contains(node)) {
                    props += " color=blue";
                } else if (pending == node) {
                    props += " color=red";
                }

                str.append("    " + nodes.get(node.getParent()) + " -> " + nodes.get(node) + " [" + props + "];\n");


                if (node.suffixLink != null) {
                    if (!nodes.containsKey(node.suffixLink)) {
                        nodes.put((TreeNode) node.suffixLink, ((TreeNode)node.suffixLink).getId(step));
                    }
                    str.append("    " + nodes.get(node) + " -> " + nodes.get(node.suffixLink) + " [ style=dotted ];\n");
                }
            }

            if (!node.isLeaf()) {
                queue.addAll(node.getChildNodes());
            }
        }

        str.append('\n');

        // append all the nodes information
        Iterator<TreeNode> it = nodes.keySet().iterator();
        while (it.hasNext()) {
            SuffixTree.TreeNode node = it.next();
            String nodeId = nodes.get(node);

            if (node.isRoot() || node.isLeaf()) {
                String props = "";
                if (node.isRoot()) {
                    props += "width=0.15 ";
                } else if (node.isLeaf()) {
                    props += "shape=point ";
                }

                if (node == activePoint.node) {
                    props += " fillcolor=red ";
                }

                str.append("    " + nodeId + " [ " + props + "]\n");
            } else if (node == activePoint.node) {
                str.append("    " + nodeId + " [width=0.15 fillcolor=red]\n");
            }
        }

        nodes.clear();
        str.append("  }\n\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GRAPHVIZ_FILENAME, true))) {
            writer.write(str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        subGraphIndex ++;
    }

    public String getGraphvizFileName() {
        return "src/test/resources/graph_" + text.substring(0, length()-1).replace(' ', '_');
    }

    public String toString() {
        if (text.length() < 80) {
            return text.toString().substring(0, text.length()-1);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(text.substring(0, 10));
            sb.append("...");
            sb.append(text.substring(text.length()-10));
            return sb.toString();
        }
    }

    class TreeNode extends SuffixTree.TreeNode {

        TreeNode(SuffixTree.TreeNode parent, int from) {
            super(parent, from);
        }

        @Override
        public Collection<SuffixTree.TreeNode> split() {
            Collection<SuffixTree.TreeNode> nodes = super.split();
            for (SuffixTree.TreeNode node : nodes) {
                lastModified.add((TreeNode) node);
            }
            appendToGraph();
            lastModified.clear();
            return nodes;
        }

        @Override
        public String toString() {
            int to = -1 == this.to ? text.length() : this.to;
            return null == parent ? "root" : "node" + lengthPath + from + to;
        }

        public String getId(int step) {
            return toString() + step + '_' + subGraphIndex;
        }

        public String getEdgeLabel() {
            int to = from + getLength() - 1;
            return "" + '[' + from + ',' + to + ']' + ' ' + text.substring(from, to+1).replace(SENTINEL, '$');
        }
    }

    class ActivePoint extends SuffixTree.ActivePoint {

        public String toString() {
            String e = null == edge ? "\\\\0x" : getEdge().toString();
            return "(" + node + ",'" + e + "'," + length + ')';
        }

    }
}