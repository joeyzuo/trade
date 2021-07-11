import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;




public class ts {


    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    public static void main(String[] args) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        byte[] buffer = new byte[4096];
        String str_return = "";
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                    new java.security.SecureRandom());
            URL console = new URL(
                    "https://api.hbtc.com/openapi/v1/brokerInfo");
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestMethod("get");
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String inpputs="username=arcgis&password=arcgis123&client=requestip&f=json";
            OutputStream os = conn.getOutputStream();
            os.write(inpputs.getBytes());
            os.close();
            conn.connect();

            InputStream is = conn.getInputStream();
//  // DataInputStream indata = new DataInputStream(is);
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while (ret != null) {
                ret = reader.readLine();
                if (ret != null && !ret.trim().equals("")) {
                    str_return = str_return
                            + ret;
                }



            }

            conn.disconnect();
        } catch (ConnectException e) {
            System.out.println("ConnectException");
            System.out.println(e);
            throw e;

        } catch (IOException e) {
            System.out.println("IOException");
            System.out.println(e);
            throw e;

        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
            try {
                out.close();
            } catch (Exception e) {
            }
        }


    }

}