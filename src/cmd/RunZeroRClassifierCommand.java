package cmd;

import java.util.Random;

import util.Loader;
import util.Printer;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.rules.ZeroR;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class RunZeroRClassifierCommand implements Command {
	public void exec() {
		Instances dataset = Loader.loadArff(CleanDataCommand.CLEAN_ARFF_DATASET);
		dataset.setClassIndex(dataset.numAttributes() - 1);

		try {
			ZeroR model = new ZeroR();

			Evaluation eval = new Evaluation(dataset);
			eval.crossValidateModel(model, dataset, 10, new Random(1));
			model.buildClassifier(dataset);

			System.out.println("=== ZeroR Model ===\n");
			System.out.println(model);
			Printer.printConfusionMatrix(eval);

			SerializationHelper.write("bin/zeroR.bin", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new RunZeroRClassifierCommand();
		cmd.exec();
	}
}
