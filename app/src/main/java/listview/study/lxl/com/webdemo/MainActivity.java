package listview.study.lxl.com.webdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class MainActivity extends Activity {
	TextView tv;int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(TextView)findViewById(R.id.tv);
		(findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.this,"be clicked",Toast.LENGTH_SHORT).show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						httpcilent();

					}
				}).start();
			}
		});
	}


	private  void httpcilent() {
		Message message = new Message();
		HttpGet hg = new HttpGet("http://192.168.0.109/android/mytest.xml");
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(hg);
			Log.d("httpcilent",response.getStatusLine().getStatusCode()+"");
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity= response.getEntity();
				String sresponse= EntityUtils.toString(entity,"utf-8");
				lxlXMLwithpull(sresponse);
			}
			message.what=1;
			handler.sendMessage(message);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void parseXMLwithpull(String sresponse) throws IOException {
		try {
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser xmlpullparser=factory.newPullParser();
			xmlpullparser.setInput(new StringReader(sresponse));
			String id="";
			String name="";
			String version="";
			int eventType = xmlpullparser.getEventType();
			while (eventType!=xmlpullparser.END_DOCUMENT){
				String nodeName=xmlpullparser.getName();
				switch (eventType){
					case  XmlPullParser.START_TAG:{
						if ("id".equals(nodeName)) {
							id = xmlpullparser.nextText();
						} else if ("name".equals(nodeName)) {
							name = xmlpullparser.nextText();
						} else if ("version".equals(nodeName)) {
							version = xmlpullparser.nextText();
						}
						break;
					}
					case XmlPullParser.END_TAG: {
						if ("app".equals(nodeName)) {
							Log.d("MainActivity", "id is " + id);
							Log.d("MainActivity", "name is " + name);
							Log.d("MainActivity", "version is " + version);
						}
						break;
					}
					default:
						break;
				}
				eventType = xmlpullparser.next();
			}


		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

	}

	private  void lxlXMLwithpull(String s){
		try {
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser=factory.newPullParser();
			xmlPullParser.setInput(new StringReader(s));
			int eventype=xmlPullParser.getEventType();
			String name="";
			String	age="";
			String height="";
			String nodeName;
			while (eventype!=XmlPullParser.END_DOCUMENT){
				nodeName=xmlPullParser.getName();
				switch (eventype){
					case XmlPullParser.START_TAG:
						if("name".equals(nodeName)){
							name=xmlPullParser.nextText();
						}
						if("age".equals(nodeName)){
							age=xmlPullParser.nextText();
						}
						if("height".equals(nodeName)){
						height=xmlPullParser.nextText();
					}
						break;
					case  XmlPullParser.END_TAG:
						if ("app".equals(nodeName)) {
							Log.d("mytestXMl", name);
							Log.d("mytestXMl", age);
							Log.d("mytestXMl", height);
						}break;
					default:
						break;
				}eventype=xmlPullParser.next();

			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					Log.d("response",""+i);
					tv.setText("BAIDUSHABI'");
					break;
			}
		}
	};


	private  void test() throws IOException {
		//记得把tomcat的监听端口改为80 否则报refuse错误 真机测试可行

	HttpGet hg=new HttpGet("http://192.168.0.109");

		HttpClient hc=new DefaultHttpClient();

		HttpResponse hr=hc.execute(hg);
		Message msg=new Message();
		if (hr.getStatusLine().getStatusCode()==200){
			msg.what=1;
			handler.sendMessage(msg);

		}
	}

}
