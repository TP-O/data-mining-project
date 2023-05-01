package cmd;

import java.util.Random;

import util.Loader;
import util.Printer;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class RunNaiveBayesClassifierCommand implements Command {
	public void exec() {
		Instances dataset = Loader.loadArff(CleanDataCommand.CLEAN_ARFF_DATASET);
		dataset.setClassIndex(0);

		try {
			NaiveBayes model = new NaiveBayes();
			model.setOptions(new String[] { "-K" });

			Evaluation eval = new Evaluation(dataset);
			eval.crossValidateModel(model, dataset, 10, new Random(1));
			model.buildClassifier(dataset);

			System.out.println("=== Naive Bayes Model ===\n");
			System.out.println(model);
			Printer.printConfusionMatrix(eval);

			SerializationHelper.write("bin/naivebayes.bin", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new RunNaiveBayesClassifierCommand();
		cmd.exec();
	}
}
