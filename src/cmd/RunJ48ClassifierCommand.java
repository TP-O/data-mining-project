package cmd;

import java.util.Random;

import util.Loader;
import util.Printer;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class RunJ48ClassifierCommand implements Command {

	public void exec() {
		Instances dataset = Loader.loadArff(CleanDataCommand.CLEAN_ARFF_DATASET);
		dataset.setClassIndex(0);

		try {
			J48 tree = new J48();
			tree.setOptions(new String[] { "-C", "0.25", "-M", "2" });

			Evaluation eval = new Evaluation(dataset);
			eval.crossValidateModel(tree, dataset, 10, new Random(1));
			tree.buildClassifier(dataset);

			System.out.println("=== J48 Model ===\n");
			System.out.println(tree);
			System.out.println(tree.graph());
			Printer.printConfusionMatrix(eval);

			SerializationHelper.write("bin/j48.bin", tree);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new RunJ48ClassifierCommand();
		cmd.exec();
	}
}
