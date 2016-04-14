package listview.study.lxl.com.webdemo;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Administrator on 2016/4/14.
 */
public class MyHandler extends DefaultHandler {
	private  StringBuilder name;
	private  StringBuilder age;
	private  StringBuilder height;
	private  String  nodeName;
	@Override
	public void startDocument() throws SAXException {
		name=new StringBuilder();
		age=new StringBuilder();
		height=new StringBuilder();
	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		nodeName=localName;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("app".equals(localName)){
			Log.d("SAXXML",name.toString().trim());
			Log.d("SAXXML",age.toString().trim());
			Log.d("SAXXML",height.toString().trim());
			name.setLength(0);
			age.setLength(0);
			height.setLength(0);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if("name".equals(nodeName)){
			name.append(ch,start,length);
		}
		else if("age".equals(nodeName)){
			age.append(ch,start,length);
		}
		else if("height".equals(nodeName)){
			height.append(ch,start,length);
		}

	}
}
