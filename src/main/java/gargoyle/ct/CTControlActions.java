package gargoyle.ct;

public interface CTControlActions {
	void arm(CTConfig config);

	void exit();

	CTConfigs getConfigs();

	void help();

	void unarm();
}
