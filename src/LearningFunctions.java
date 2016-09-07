public class LearningFunctions {
    public static double calculateEntropy(double[] probabilities) {
        double summation = 0;

        for (int i = 0; i < probabilities.length; i++) {
            double prob = probabilities[i];
            summation += -prob * LearningFunctions.log2(prob);
        }

        return summation;
    }

    public static double informationGain(double parentEntropy, double childEntropy) {
        return parentEntropy - childEntropy;
    }

    public static double log2(double x) {
        if (x == 0) {
            return 0;
        }

        return Math.log10(x) / Math.log10(2);
    }
}
