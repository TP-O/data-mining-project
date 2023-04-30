package cmd;

import util.Loader;
import util.Saver;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.StringToNominal;

public class HandleMissingDataCommand implements Command {
	public final static String HANDLE_MISSING_CSV_DATASET = "data/handle_missing_HepatitisCdata.csv";

	public final static String HANDLE_MISSING_ARFF_DATASET = "data/handle_missing_HepatitisCdata.arff";

	public void exec() {
		Instances dataset = Loader.loadArff(RemoveUselessAttributesCommand.REMOVED_REDUNDANCY_ARFF_DATASET);

		try {
			StringToNominal s2n = new StringToNominal();
			s2n.setOptions(new String[] { "-R", "first-last" });
			s2n.setInputFormat(dataset);
			dataset = Filter.useFilter(dataset, s2n);

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
