package kadai02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsClient {
	public static String getContents(String targetUrl) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		// 取得したテキストを格納する変数
		final StringBuilder result = new StringBuilder();

			URL url = new URL(targetUrl);

			HttpsURLConnection connection = makeHttpsURLConnection(url);

	        connection.setRequestMethod("POST");
	        connection.setDoOutput(true);

	        BufferedWriter requestBody = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
	        requestBody.write("{\"key\":\"value\"}");
	        requestBody.flush();
	        requestBody.close();

			connection.connect();

			// HTTPレスポンスコード
			final int status = connection.getResponseCode();
			if (status == HttpsURLConnection.HTTP_OK) {
				// 通信に成功した
				// テキストを取得する
				final InputStream in = connection.getInputStream();
				final String encoding = connection.getContentEncoding();
				final InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
				final BufferedReader bufReader = new BufferedReader(inReader);
				String line = null;
				// 1行ずつテキストを読み込む
				while ((line = bufReader.readLine()) != null) {
					result.append(line);
				}
				bufReader.close();
				inReader.close();
				in.close();

			if (connection != null) {
				// コネクションを切断
				connection.disconnect();
			}
		}
		return result.toString();
	}

	private static HttpsURLConnection makeHttpsURLConnection(URL url)
			throws IOException, NoSuchAlgorithmException, KeyManagementException {
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		// 証明書に書かれているCommon NameとURLのホスト名が一致していることの検証をスキップ
		connection.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession sslSession) {
				return true;
			}
		});

		// 証明書チェーンの検証をスキップ
		KeyManager[] keyManagers = null;
		TrustManager[] transManagers = { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		} };
		SSLContext sslcontext = SSLContext.getInstance("SSL");
		sslcontext.init(keyManagers, transManagers, new SecureRandom());
		connection.setSSLSocketFactory(sslcontext.getSocketFactory());
		return connection;
	}
}