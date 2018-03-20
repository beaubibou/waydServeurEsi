package comparator;

import java.util.Comparator;

import wayde.bean.Discussion;

public class DiscussionDateComparator implements Comparator<Discussion> {

	@Override
	public int compare(Discussion arg0, Discussion arg1) {
		
		if (arg0.getDateMessage() < arg1.getDateMessage())
			return -1;
	
		return 1;

	}

}
