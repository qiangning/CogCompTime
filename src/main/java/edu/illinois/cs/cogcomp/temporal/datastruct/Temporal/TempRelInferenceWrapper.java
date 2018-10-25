package edu.illinois.cs.cogcomp.temporal.datastruct.Temporal;

import edu.illinois.cs.cogcomp.infer.ilp.GurobiHook;
import edu.illinois.cs.cogcomp.temporal.utils.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempRelInferenceWrapper {
    private int n_entitiy, n_relation;
    // all first two dimensions are upper triangle
    private double[][][] local_score;//n_entitiy x n_entitiy x n_relation
    private boolean[][] ignoreMap;//n_entitiy x n_entitiy
    private List<int[][][]> constraintMap;// A list of {n_entitiy x n_entitiy x (n_relation+1)} //+1dim is the "1" in x1+x2+x3=1
    private List<Triplet<Integer,Integer,List<Integer>>> transitivityMap;

    private int[][] result;//n_entitiy x n_entitiy
    private int[][][] variables;//n_entitiy x n_entitiy x n_relation
    private GurobiHook solver = new GurobiHook();

    public TempRelInferenceWrapper(int n_entitiy, int n_relation, double[][][] local_score, boolean[][] ignoreMap, int[][][] constraintMap, List<Triplet<Integer, Integer, List<Integer>>> transitivityMap) {
        this(n_entitiy,n_relation,local_score,ignoreMap,new ArrayList<int[][][]>(){{add(constraintMap);}},transitivityMap);
    }
    public TempRelInferenceWrapper(int n_entitiy, int n_relation, double[][][] local_score, boolean[][] ignoreMap, List<int[][][]> constraintMap, List<Triplet<Integer, Integer, List<Integer>>> transitivityMap) {
        this.n_entitiy = n_entitiy;
        this.n_relation = n_relation;
        this.local_score = local_score;
        this.ignoreMap = ignoreMap;
        this.constraintMap = constraintMap;
        this.transitivityMap = transitivityMap;
        result = new int[n_entitiy][n_entitiy];
        for(int i=0;i<n_entitiy;i++)
            Arrays.fill(result[i], TemporalRelType.getNullTempRel().getReltype().getIndex());
    }

    private boolean isInIgnoreMap(int i, int j){
        if(i<j)
            return ignoreMap[i][j];
        else if(i>j)
            return isInIgnoreMap(j,i);
        else
            return true;
    }
    private void initVariables(){
        variables = new int[n_entitiy][n_entitiy][n_relation];
        for(int i=0;i<n_entitiy;i++)
            for(int j=0;j<n_entitiy;j++)
                for(int k=0;k<n_relation;k++)
                    variables[i][j][k] = -1;
    }
    private void addVariables(){
        initVariables();
        for(int i=0;i<n_entitiy;i++) {
            for (int j = i + 1; j < n_entitiy; j++) {
                if (ignoreMap[i][j]) {
                    continue;
                }
                for(int k=0;k<n_relation;k++){
                    int var = solver.addBooleanVariable(local_score[i][j][k]);
                    variables[i][j][k] = var;
                }
            }
        }
    }
    private void addConstraints(){
        applyConstraintMap();
        enforceTransitivity();
    }
    private void applyConstraintMap(){
        for(int i=0;i<n_entitiy;i++){
            for(int j=i+1;j<n_entitiy;j++){
                if(isInIgnoreMap(i,j))
                    continue;
                for(int[][][] constraintMap_single : constraintMap) {
                    int[] vars = variables[i][j];
                    double[] coefs = new double[n_relation];
                    for (int k = 0; k < n_relation; k++) {
                        coefs[k] = constraintMap_single[i][j][k];
                        if (vars[k] < 0)
                            System.out.printf("Variable id less than 0: %d\n", vars[k]);
                    }
                    solver.addEqualityConstraint(vars, coefs, constraintMap_single[i][j][n_relation]);
                }
            }
        }
    }
    private void enforceTransitivity(){
        for(int i=0;i<n_entitiy;i++) {
            for (int j = i + 1; j < n_entitiy; j++) {
                if (isInIgnoreMap(i,j)) {
                    continue;
                }
                for(int k=i+1;k<n_entitiy;k++){
                    if (k == j|| isInIgnoreMap(i,j)|| isInIgnoreMap(j,k)|| isInIgnoreMap(i,k)) {
                        continue;
                    }
                    for (Triplet<Integer,Integer,List<Integer>> triplet : transitivityMap) {
                        int m = triplet.getThird().size();
                        double[] coefs = new double[m + 2];
                        int[] vars = new int[m + 2];
                        coefs[0] = 1;
                        coefs[1] = 1;
                        if(j<k){//i<j<k
                            vars[0] = variables[i][j][triplet.getFirst()];
                            vars[1] = variables[j][k][triplet.getSecond()];
                            for (int p = 0; p < m; p++) {
                                coefs[p + 2] = -1;
                                vars[p + 2] = variables[i][k][triplet.getThird().get(p)];
                            }
                        }
                        else{//i<k<j
                            vars[0] = variables[i][k][triplet.getFirst()];
                            vars[1] = variables[k][j][triplet.getSecond()];
                            for (int p = 0; p < m; p++) {
                                coefs[p + 2] = -1;
                                vars[p + 2] = variables[i][j][triplet.getThird().get(p)];
                            }
                        }
                        for(int p=0;p<m+2;p++){
                            if(vars[p]<0)
                                System.out.printf("Variable id less than 0: %d\n",vars[p]);
                        }
                        solver.addLessThanConstraint(vars, coefs, 1);
                    }
                }
            }
        }
    }
    private void retrieveResult(){
        for(int i=0;i<n_entitiy;i++){
            for(int j=i+1;j<n_entitiy;j++){
                if (isInIgnoreMap(i,j)) {
                    continue;
                }
                int cnt = 0;
                for(int k=0;k<n_relation;k++){
                    if(solver.getBooleanValue(variables[i][j][k])){
                        cnt++;
                        result[i][j] = k;
                    }
                }
                if(cnt>1)
                    System.out.println("[WARNING] More than one label in result. Please make sure this is expected.");
            }
        }
    }
    private void gurobiSolve(){
        solver.setMaximize(true);
        try {
            solver.solve();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void solve(){
        addVariables();
        addConstraints();
        gurobiSolve();
        retrieveResult();
    }

    public int[][] getResult() {
        return result;
    }

    public GurobiHook getSolver() {
        return solver;
    }
}
