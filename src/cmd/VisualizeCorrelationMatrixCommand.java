package cmd;

import util.Loader;
import weka.attributeSelection.CorrelationAttributeEval;
import weka.core.Instances;

public class VisualizeCorrelationMatrixCommand implements Command {
	public void exec() {
		Instances dataset = Loader.loadArff(CleanDataCommand.CLEAN_ARFF_DATASET);

		try {
			CorrelationAttributeEval cEval = new CorrelationAttributeEval();
			System.out.println();
			for (int i = dataset.numAttributes() - 1; i >= 0; i--) {
				System.out.print("A[" + i + "]" + " ");
			}

			System.out.println();
			for (int i = dataset.numAttributes() - 1; i >= 0; i--) {
				dataset.setClassIndex(i);
				cEval.buildEvaluator(dataset);
				for (int j = 0; j <= i; j++) {
					if (j == 0) {
						System.out.print(
								"A[" + i + "]" + " " + String.format("%.2f", cEval.evaluateAttribute(j)) + " ");
					} else {
						System.out.print(String.format("%.2f", cEval.evaluateAttribute(j)) + " ");
					}
				}
				System.out.println("\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new VisualizeCorrelationMatrixCommand();
		cmd.exec();
	}
}
