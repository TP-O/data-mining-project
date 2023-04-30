package cmd;

import util.Loader;
import util.Saver;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class HandleMissingDataCommand implements Command {
	public final static String HANDLE_MISSING_CSV_DATASET = "data/handle_missing_mxmh_survey_results.csv";

	public final static String HANDLE_MISSING_ARFF_DATASET = "data/handle_missing_mxmh_survey_results.arff";

	public void exec() {
		Instances dataset = Loader.loadArff(RemoveUselessAttributesCommand.REMOVED_REDUNDANCY_ARFF_DATASET);

		try {
			// Remove dirty row (mentioned in report)
			dataset.remove(dataset.get(568));

			// Filter missing values
			ReplaceMissingValues missingValues = new ReplaceMissingValues();
			missingValues.setInputFormat(dataset);
			Instances newData = Filter.useFilter(dataset, missingValues);

			Saver.saveArff(HANDLE_MISSING_ARFF_DATASET, newData);
			Saver.saveCsv(HANDLE_MISSING_CSV_DATASET, newData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new HandleMissingDataCommand();
		cmd.exec();
	}
}
