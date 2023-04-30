package cmd;

import util.Loader;
import util.Saver;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class CleanDataCommand implements Command {
	public final static String CLEAN_CSV_DATASET = "data/clean_HepatitisCdata.csv";

	public final static String CLEAN_ARFF_DATASET = "data/clean_HepatitisCdata.arff";

	public void exec() {
		Instances dataset = Loader.loadArff(RemoveOutlierAndExtremeDataCommand.REMOVED_OUTLIER_ARFF_DATASET);

		try {
			Remove remove = new Remove();
			remove.setOptions(new String[] { "-R", "14,15" });
			remove.setInputFormat(dataset);
			Instances cleanData = Filter.useFilter(dataset, remove);

			Saver.saveArff(CLEAN_ARFF_DATASET, cleanData);
			Saver.saveCsv(CLEAN_CSV_DATASET, cleanData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new CleanDataCommand();
		cmd.exec();
	}
}
