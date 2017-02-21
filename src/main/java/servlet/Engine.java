package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
/**
 * Servlet implementation class Engine
 */
@WebServlet("/Engine")
public class Engine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Engine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST - Engine at "+System.currentTimeMillis());
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		/* find specific property details */
		if(request.getParameter("oper").equals("findHouse")) {
			String address = request.getParameter("address");
			System.out.println(address);
			String replaceAddress = address.replace(' ', '+');
			System.out.println(replaceAddress);
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			final String APIKey = "X1-ZWz19dqoxl2cjv_7x5fi";
			System.out.println("Running JSOUP and Zillow API....");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id="+APIKey+"&address="+replaceAddress+"&citystatezip="+city+" "+state+"";
				System.out.println(url);
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
				    Document xmlDoc = Jsoup.parse(responseSoup.body(), url, Parser.xmlParser());
				    System.out.println(xmlDoc.toString());
				    String estimateAmount = xmlDoc.select("amount").first().text();
				    System.out.println(estimateAmount);
				    response.getWriter().write(estimateAmount);
				}

				
			} catch (IOException e) {
				e.printStackTrace();
			}
			/* get chart for specific property */
		} else if (request.getParameter("oper").equals("getChartEstimate")) {
			String address = request.getParameter("address");
			System.out.println(address);
			String replaceAddress = address.replace(' ', '+');
			System.out.println(replaceAddress);
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			final String APIKey = "X1-ZWz19dqoxl2cjv_7x5fi";
			System.out.println("Running JSOUP and Zillow API....");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id="+APIKey+"&address="+replaceAddress+"&citystatezip="+city+" "+state+"";
				System.out.println(url);
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
				    //Document doc = Jsoup.parse(responseSoup.body(),url);
				    Document xmlDoc = Jsoup.parse(responseSoup.body(), url, Parser.xmlParser());
				    System.out.println(xmlDoc.toString());
				    String zpid = xmlDoc.select("zpid").first().text();
				    System.out.println(zpid);
				    String chartURL = "http://www.zillow.com/app?chartDuration=1year&chartType=partner&height=150&page=webservice%2FGetChart&service=chart&showPercent=true&width=300&zpid="+zpid;
				    System.out.println(chartURL);
				    response.getWriter().write(chartURL);
				}

				
			} catch (IOException e) {
				e.printStackTrace();
			}
			/* get top recently sold houses in area via JSOUP */
		} else if(request.getParameter("oper").equals("getHousesSold")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			final String APIKey = "X1-ZWz19dqoxl2cjv_7x5fi";
			System.out.println("Running JSOUP and Zillow API....");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/"+city+"-"+state+"/sold/";
				System.out.println(url);
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
					//Get Document object after parsing the html from given url.
					document = Jsoup.connect(url).get();
					JSONArray jsonArray = new JSONArray();
					JSONObject soldValues = new JSONObject();
					JSONObject houseInfo = new JSONObject();
					JSONObject nearInfo = new JSONObject();
					Elements nearByCities = document.select(".zsg-g_gutterless:matches([$-])");
					for(int i=1; i < nearByCities.size(); i++) {
						String nearByCity = nearByCities.get(i).text().replace("-", "");
						nearInfo.append("nearByInfo", nearByCity);
					}
					Elements specs = document.select(".zsg-photo-card-spec"); //get specs
					for (int i=0; i < specs.size(); i++) {
						//System.out.println(specs.get(i).text());
						if(specs.get(i).text().contains("SOLD")) {
							soldValues.append("soldValues", specs.get(i).text());
						} else if(specs.get(i).text().startsWith("Price")) {
							houseInfo.append("houseInfo", specs.get(i).text());
						} 
					}
					jsonArray.put(soldValues);
					jsonArray.put(houseInfo);
					jsonArray.put(nearInfo);
					response.getWriter().write(jsonArray.toString());
				}			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
