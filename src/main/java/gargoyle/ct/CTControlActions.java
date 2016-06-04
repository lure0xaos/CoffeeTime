package gargoyle.ct;

import java.util.List;

public interface CTControlActions {
	void arm(CTConfig config);

	void exit();

	List<CTConfig> getConfigs();

	void unarm();
}
