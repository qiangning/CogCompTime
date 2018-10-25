package edu.illinois.cs.cogcomp.temporal.configurations;

public class ParamLBJ {
    public static class EventDetectorPerceptronParams{
        public static double learningRate = 0.001;
        public static double thickness = 1;
    }
    public static class EETempRelClassifierPerceptronParams{
        public static double learningRate = 0.001;
        public static double thickness = 1;

        public static double[] LEARNRATE = new double[]{0.001};
        public static double[] THICKNESS = new double[]{0,1};
        public static double[] SAMRATE = new double[]{0.1,0.2,0.3};
        public static double[] ROUND = new double[]{50,100,200};
    }
    public static class ETTempRelClassifierPerceptronParams{
        public static double learningRate = 0.001;
        public static double thickness = 1;

        public static double[] LEARNRATE = new double[]{0.001};
        public static double[] THICKNESS = new double[]{0,1};
        public static double[] SAMRATE = new double[]{0.4,0.5,0.6};
        public static double[]  ROUND = new double[]{50,100};
    }
}