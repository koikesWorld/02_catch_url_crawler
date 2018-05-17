package kadai02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Title {
	public static void main(String[] args) throws Exception {
		Set<String> titleSet = new HashSet<String>();
		Set<String> urlSet = new HashSet<String>();
//		 List<String> urlList = new ArrayList<String>();
		String title = null;
		Map<String, String> map = new HashMap<String, String>();

		String html = HttpsClient.getContents("https://no1s.biz");
		urlSet = Match.getUrl(html);
		titleSet = Match.getTitle(html);

		//	再起処理でURLを取得する(未実装)

	}
}
