package edu.illinois.cs.cogcomp.temporal.utils.CoDL;

import edu.illinois.cs.cogcomp.core.io.IOUtils;
import edu.illinois.cs.cogcomp.lbjava.learn.Learner;
import edu.illinois.cs.cogcomp.temporal.utils.IO.mySerialization;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class CoDLWrapper_LBJ<LearningStruct, LearningAtom> {
    protected List<LearningStruct> trainStructs_partial;
    protected int maxRound, seed;
    protected boolean OneMdlOrTwoMdl;// true: 1-model. false: 2-model
    protected MultiClassifiers<LearningAtom> multiClassifiers;// this could also be single classifier (i.e., length=1)
    protected String modelDir, modelNamePrefix;
    protected String cacheDir;
    protected boolean forceUpdate = false, saveCache = false;
    protected mySerialization serializer = new mySerialization(true);

    // needed only if using 1-model
    protected List<LearningStruct> trainStructs_full;

    // needed only if using 2-model
    protected double lambda;// must be in [0,1]

    public CoDLWrapper_LBJ(boolean OneMdlOrTwoMdl, boolean saveCache, boolean forceUpdate,
                           double lambda,int maxRound, int seed, String modelDir, String modelNamePrefix) throws Exception{
        this.OneMdlOrTwoMdl = OneMdlOrTwoMdl;
        this.lambda = lambda;
        this.maxRound = maxRound;
        this.seed = seed;
        this.saveCache = saveCache;

        setForceUpdate(forceUpdate);
        modelNamePrefix += "_sd"+seed;
        setModelDirAndPrefix(modelDir,modelNamePrefix);// this should be before setDefaultCacheDir()
        if(saveCache)
            setCacheDir();
    }

    private void setModelDirAndPrefix(String modelDir,String modelNamePrefix){
        IOUtils.mkdir(modelDir);
        this.modelDir = modelDir;
        this.modelNamePrefix = modelNamePrefix;
    }

    public void initModel() throws Exception{
        Learner cls0;
        if(OneMdlOrTwoMdl) {
            cls0 = loadBaseCls();
            multiClassifiers = new MultiClassifiers<>(cls0,-1,true);
        }
        else {
            cls0 = loadBaseCls();
            multiClassifiers = new MultiClassifiers<>(cls0,lambda,true);
            multiClassifiers.addClassifier(cls0);// this is just a placeholder
        }
    }


    public void CoDL_LoadData() throws Exception{
        if(OneMdlOrTwoMdl) {
            loadData_1model();
        }
        else {
            loadData_2model();
        }
    }

    public abstract void loadData_1model() throws Exception;// load trainStructs_partial and trainStructs_full

    public abstract void loadData_2model() throws Exception;// load trainStructs_partial and lambda

    public abstract Learner loadBaseCls() throws Exception;

    public abstract Learner loadSavedCls() throws Exception;

    private String modelPrefixPlusParams(){
        if(OneMdlOrTwoMdl)
            return String.format("%s_1mdl_round%d",modelNamePrefix,maxRound);
        else
            return String.format("%s_2mdl_lambda%.2f_round%d",modelNamePrefix,lambda,maxRound);
    }

    protected void setDefaultCacheDir(){
        setCacheDir("serialization"+File.separator+"CoDL_cache"+File.separator + modelPrefixPlusParams());
    }

    public abstract void setCacheDir();// implementation can simply be returning setDefaultCacheDir()

    public abstract LearningStruct inference(LearningStruct doc);//have to make a deep copy of the input

    public abstract Learner learn(List<LearningStruct> slist, int curr_round);

    public abstract String getStructId(LearningStruct st);//used for cache purpose

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
        if(saveCache)
            IOUtils.mkdir(this.cacheDir);
        else
            System.out.println("[WARNING] Setting cacheDir while saveCache==false.");
    }

    private void setForceUpdate(boolean forceUpdate){
        System.out.printf("[CoDLWrapper_LBJ] forceUpdate=%s\n",forceUpdate);
        this.forceUpdate = forceUpdate;
    }

    public void CoDL(){
        if(!forceUpdate&&modelExists()){
            String[] modelandlexpath = modelAndLexPath();
            System.out.printf("Model exists:\n%s\n%s\n Do not do CoDLWrapper_LBJ::CoDL(). Load them and return.\n",modelandlexpath[0],modelandlexpath[1]);
            try {
                Learner cls = loadSavedCls();
                multiClassifiers.dropClassifier();
                multiClassifiers.addClassifier(cls);
            }
            catch (Exception e){
                e.printStackTrace();
                System.exit(-1);
            }
            return;
        }
        for(int iter=0;iter<maxRound;iter++){
            System.out.println("----------------");
            System.out.printf("----Round%d/%d----\n",iter+1,maxRound);
            System.out.println("----------------");
            List<LearningStruct> trainStructs_pseudo_full = new ArrayList<>();
            for(LearningStruct st:trainStructs_partial){
                System.out.printf("----Inference %d/%d\n",trainStructs_partial.indexOf(st)+1,trainStructs_partial.size());
                boolean inferenceNeeded = true;
                if(!forceUpdate&&cacheExists(st,iter)){
                    inferenceNeeded = false;
                    try {
                        String cachepath = cachePathPerDoc(st, iter);
                        trainStructs_pseudo_full.add((LearningStruct)serializer.deserialize(cachepath));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        System.out.println("[WARNING] failed to load serialized cache in CoDL. Switching to using provided inference function");
                        inferenceNeeded = true;
                    }
                }
                if(inferenceNeeded) {
                    LearningStruct st_inf = inference(st);
                    trainStructs_pseudo_full.add(st_inf);
                    if(saveCache) {
                        try {
                            serializer.serialize(st_inf, cachePathPerDoc(st_inf, iter));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            System.out.println("[WARNING] failed to serialize CoDL cache: " + cachePathPerDoc(st_inf, iter));
                        }
                    }
                }
            }

            if(OneMdlOrTwoMdl){// 1-model
                trainStructs_pseudo_full.addAll(trainStructs_full);
            }
            // else 2-model: do nothing

            Learner cls = learn(trainStructs_pseudo_full, iter);
            seed++;
            multiClassifiers.dropClassifier();
            multiClassifiers.addClassifier(cls);
        }
    }

    private String cachePathPerDoc(LearningStruct st, int iter){
        return String.format("%s%s%s_iter%d.ser",cacheDir,File.separator,getStructId(st),iter);
    }

    private boolean cacheExists(LearningStruct st, int iter){
        return IOUtils.isFile(cachePathPerDoc(st,iter));
    }

    protected String[] modelAndLexPath (){
        String tmp = modelDir+File.separator+modelPrefixPlusParams();
        if(!OneMdlOrTwoMdl){
            tmp+="_2ndCls";
        }
        return new String[]{tmp+".lc",tmp+".lex"};
    }
    protected boolean modelExists(){
        String[] modelandlexpath = modelAndLexPath();
        return IOUtils.isFile(modelandlexpath[0]) && IOUtils.isFile(modelandlexpath[1]);
    }
    public void saveClassifiers(){
        String[] modelandlexpath = modelAndLexPath();
        if(OneMdlOrTwoMdl){
            multiClassifiers.classifiers.get(0).write(modelandlexpath[0],modelandlexpath[1]);
        }
        else{
            multiClassifiers.classifiers.get(1).write(modelandlexpath[0],modelandlexpath[1]);
        }
    }
}
