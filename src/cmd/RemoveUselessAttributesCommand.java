package cmd;

import util.Loader;
import util.Saver;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class RemoveUselessAttributesCommand implements Command {
	public final static String REMOVED_REDUNDANCY_CSV_DATASET = "data/removed_redundancy_HepatitisCdata.csv";

	public final static String REMOVED_REDUNDANCY_ARFF_DATASET = "data/removed_redundancy_HepatitisCdata.arff";

	public void exec() {
		Instances dataset = Loader.loadArff(OriginalCsv2ArffCommand.ORIGINAL_ARFF_DATASET);

		try {
			Remove remove = new Remove();
			remove.setOptions(new String[] { "-R", "1" });
			remove.setInputFormat(dataset);
			Instances newData = Filter.useFilter(dataset, remove);

			Saver.saveArff(REMOVED_REDUNDANCY_ARFF_DATASET, newData);
			Saver.saveCsv(REMOVED_REDUNDANCY_CSV_DATASET, newData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new RemoveUselessAttributesCommand();
		cmd.exec();
	}
}
