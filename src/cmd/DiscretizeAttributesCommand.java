package cmd;

import util.Loader;
import util.Saver;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

public class DiscretizeAttributesCommand implements Command {
	public final static String DISCRETIZED_CSV_DATASET = "data/discretized_HepatitisCdata.csv";

	public final static String DISCRETIZED_ARFF_DATASET = "data/discretized_HepatitisCdata.arff";

	public void exec() {
		Instances dataset = Loader.loadArff(CleanDataCommand.CLEAN_ARFF_DATASET);

		try {
			Discretize discretize = new Discretize();
			discretize
					.setOptions(new String[] {
							"-O",
							"-B", "10",
							"-M", "-1.0",
							"-R", "first-last",
							"-precision", "6",
					});
			discretize.setInputFormat(dataset);
			Instances discretizedData = Filter.useFilter(dataset, discretize);

			Saver.saveArff(DISCRETIZED_ARFF_DATASET, discretizedData);
			Saver.saveCsv(DISCRETIZED_CSV_DATASET, discretizedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new DiscretizeAttributesCommand();
		cmd.exec();
	}
}
