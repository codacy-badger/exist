
package samples.xmlrpc;

import org.apache.xmlrpc.*;
import java.util.Vector;
import java.io.*;

/**
 * store a document to the repository using
 * XML-RPC.
 */
public class Store {

	protected final static String uri = "http://localhost:8080/exist/xmlrpc";

	protected static void usage() {
		System.out.println("usage: samples.Store xmlFile [ docName ]");
		System.exit(0);
	}

	public static void main(String args[]) throws Exception {
		if(args.length < 2)
			usage();
		String docName = (args.length == 2) ? args[1] : args[0];

		XmlRpcClient xmlrpc = new XmlRpcClient(uri);

		// read the file into a string
		BufferedReader f = new BufferedReader(new FileReader(args[0]));
		String line;
		StringBuffer xml = new StringBuffer();
		while((line = f.readLine()) != null)
			xml.append(line);
		f.close();

		// set parameters for XML-RPC call
		Vector params = new Vector();
		params.addElement(xml.toString());
		params.addElement(args[1]);

		// execute the call
		Boolean result = (Boolean)xmlrpc.execute("parse", params);

		// check result
		if(result.booleanValue())
			System.out.println("document stored.");
		else
			System.out.println("could not store document.");
	}
}
