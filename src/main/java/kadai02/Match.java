package kadai02;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {
	public static Set<String> set = new HashSet<String>();

	public static Set<String> getUrl(String html){
		Pattern linkPtn = Pattern.compile("<a.*?href=\"(.*?)\".*?>", Pattern.DOTALL);
		Matcher matcher2 = linkPtn.matcher(html);
		while (matcher2.find()) {
			String href = matcher2.group(1).replace("¥¥s", "");
			if (href.contains("https://no1s.biz/")) {
				System.out.println(href);
			}
		}
		return set;
	}

	public static Set<String>getTitle(String html){
		Pattern titlePtn = Pattern.compile("<title>(.*?)</title>");
		Matcher matcher1 = titlePtn.matcher(html);

		if (matcher1.find()) {
			String title = matcher1.group(1);
			System.out.println(title);
		}
		return set;
	}
}
