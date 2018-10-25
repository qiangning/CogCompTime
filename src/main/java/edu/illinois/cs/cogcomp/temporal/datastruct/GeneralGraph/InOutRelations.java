package edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph;

public class InOutRelations <Node extends AugmentedNode> {
    /*private Node node;
    private HashMap<String,BinaryRelation<Node>> in_rels = new HashMap<>();
    private HashMap<String,BinaryRelation<Node>> out_rels = new HashMap<>();

    public InOutRelations(Node node) {
        this.node = node;
    }

    private boolean addInRel(BinaryRelation<Node> newrel){
        in_rels.put(newrel.getSourceNode().getUniqueId(),newrel);
        return true;
    }
    private boolean addOutRel(BinaryRelation<Node> newrel){
        out_rels.put(newrel.getTargetNode().getUniqueId(),newrel);
        return true;
    }
    private boolean addRel(BinaryRelation<Node> newrel){
        // assume newrel has at least one node matching to current node
        if(newrel.getSourceNode().isEqual(node))
            return addOutRel(newrel)&&addInRel(newrel.getInverse());
        return addInRel(newrel)&&addOutRel(newrel.getInverse());
    }
    public boolean addRelNoDup(BinaryRelation<Node> newrel){
        if(newrel==null||newrel.isNull())
            return false;
        if(newrel.getSourceNode().isEqual(node)||newrel.getTargetNode().isEqual(node)){
            if(exists(newrel)){
                System.out.println("newrel already exists; return false.");
                return false;
            }
            else{
                return addRel(newrel);
            }
        }
        else{
            System.out.println("newrel does not match to the node; return false.");
            System.out.printf("newrel=%s, node=%s\n",newrel.toString(),node.toString());
            return false;
        }
    }
    protected boolean exists(BinaryRelation<Node> newrel){
        // case 1: doesn't match to node
        if(!newrel.getSourceNode().isEqual(node)&&!newrel.getTargetNode().isEqual(node))
            return false;
        // case 2: match to source node
        if(newrel.getSourceNode().isEqual(node)){
            if(out_rels.containsKey(newrel.getTargetNode().getUniqueId())){
                BinaryRelation<Node> outrel = out_rels.get(newrel.getTargetNode().getUniqueId());
                if(!outrel.isConsistent(newrel))
                    System.out.println("WARNING: Trying to insert an inconsistent new BinaryRelation<Node>.");
                return true;
            }
        }
        // case 3: match to target node
        if(newrel.getTargetNode().isEqual(node)){
            if(in_rels.containsKey(newrel.getSourceNode().getUniqueId())){
                BinaryRelation<Node> inrel = in_rels.get(newrel.getSourceNode().getUniqueId());
                if(!inrel.isConsistent(newrel))
                    System.out.println("WARNING: Trying to insert an inconsistent new BinaryRelation<Node>.");
                return true;
            }
        }
        return false;
    }

    public int getDeg_in() {
        return in_rels.size();
    }

    public int getDeg_out() {
        return out_rels.size();
    }

    public int getDeg() {
        return getDeg_in()+getDeg_out();
    }

    public Node getNode() {
        return node;
    }

    public HashMap<String,BinaryRelation<Node>> getIn_rels() {
        return in_rels;
    }

    public HashMap<String,BinaryRelation<Node>> getOut_rels() {
        return out_rels;
    }*/
}
