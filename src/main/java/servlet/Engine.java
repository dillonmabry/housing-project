package servlet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
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
@WebServlet("/Houses")
public class Engine extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final String APIKey = "X1-ZWz19dqoxl2cjv_7x5fi";
    Date date;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Engine() {
        super();
        date = new Date();
        System.out.println("START - Running Engine Servlet at "+dateFormat.format(date));
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("oper").equals("getVersion")) {
	    	String version, environment = null;
			try {
				ServletContext application = getServletConfig().getServletContext();
				InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF");
				Manifest manifest = new Manifest(inputStream);
				Attributes attr = manifest.getMainAttributes();
				version = attr.getValue("Manifest-Version");
				environment = attr.getValue("Environment");
				response.getWriter().write(environment+" "+version);
			} catch (IOException e) {
			  e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST - Engine at "+dateFormat.format(date));
		System.out.println("Running JSOUP and Zillow API....");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		/* find specific property details */
		if(request.getParameter("oper").equals("getHousesAll")) {
			response.setContentType("text/csv");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			try {
				//Get Document object after parsing the html from given url.
				Document document;
				String url = "https://www.zillow.com/"+city+"-"+state+"/for-sale/";
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
					document = Jsoup.connect(url).get();
					Elements price = document.select(".zsg-photo-card-price:contains($)"); //Get price
					Elements address = document.select("span[itemprop]:contains(Charlotte NC)"); //Get address		
					Elements ahref = document.select("a[href*=homedetails]");
					String outputResult = "";
					for (int i=0; i < price.size()-2; i++) {
						outputResult += address.get(i).text() + "," + price.get(i).text()+"\n";
						System.out.println(address.get(i).text() + "," + price.get(i).text());
					}
					for (int j=0; j < ahref.size(); j++) {
						System.out.println(ahref.get(j).toString().substring(9,ahref.get(j).toString().indexOf("class")-2));
					}
					//TODO: contact with another loop for ahref
				    response.getWriter().write(outputResult);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			/* get chart for specific property */
		} else if(request.getParameter("oper").equals("findHouse")) {
			String address = request.getParameter("address");
			String replaceAddress = address.replace(' ', '+');
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id="+APIKey+"&address="+replaceAddress+"&citystatezip="+city+" "+state+"";
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
				    //Document doc = Jsoup.parse(responseSoup.body(),url);
				    Document xmlDoc = Jsoup.parse(responseSoup.body(), url, Parser.xmlParser());
				    String estimateAmount = xmlDoc.select("amount").first().text();
				    response.getWriter().write(estimateAmount);
				}

				
			} catch (IOException e) {
				e.printStackTrace();
			}
			/* get chart for specific property */
		} else if (request.getParameter("oper").equals("getChartEstimate")) {
			String address = request.getParameter("address");
			String replaceAddress = address.replace(' ', '+');
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id="+APIKey+"&address="+replaceAddress+"&citystatezip="+city+" "+state+"";
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
				    //Document doc = Jsoup.parse(responseSoup.body(),url);
				    Document xmlDoc = Jsoup.parse(responseSoup.body(), url, Parser.xmlParser());
				    String zpid = xmlDoc.select("zpid").first().text();
				    String chartURL = "http://www.zillow.com/app?chartDuration=5years&chartType=partner&height=250&page=webservice%2FGetChart&service=chart&showPercent=true&width=450&zpid="+zpid;
				    response.getWriter().write(chartURL);
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
			/* get top recently sold houses in area via JSOUP */
		}  else if(request.getParameter("oper").equals("getHousesSold")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/"+city+"-"+state+"/sold/";
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
		}  else if(request.getParameter("oper").equals("getHousesSale")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/"+city+"-"+state+"/for-sale/";
				//System.out.println(url);
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
					//Get Document object after parsing the html from given url.
					document = Jsoup.connect(url).get();
					JSONArray jsonArray = new JSONArray();
					JSONObject soldValues = new JSONObject();
					JSONObject nearInfo = new JSONObject();
					Elements nearByCities = document.select(".zsg-g_gutterless:matches([$-])");
					for(int i=1; i < nearByCities.size(); i++) {
						String nearByCity = nearByCities.get(i).text().replace("-", "");
						nearInfo.append("nearByInfo", nearByCity);
					}
					Elements price = document.select(".zsg-photo-card-price:contains($)"); //Get price	
					Elements items = document.select(".zsg-aspect-ratio-content");
					Elements links = document.select(".zsg-aspect-ratio-content a[href*=homedetails]");
					for (int i=0; i < items.size(); i++) {
						try {
							System.out.println(items.get(i).text());
							String address = items.get(i).text().substring(0, items.get(i).text().indexOf(state)+6);
							String patternAddress = items.get(i).text().substring(0, items.get(i).text().indexOf(state));
							String subAddress = patternAddress.replace("#", "").replace(" ", "-").replace("--", "-").trim();
							String itemInfo = items.get(i).text().substring(items.get(i).text().indexOf("$"),items.get(i).text().lastIndexOf("·"));
							for(int j=0; j < links.size(); j++) {
								if(links.get(j).toString().contains(subAddress)) {
									String link = "https://www.zillow.com"+links.get(j).toString().substring(9,links.get(j).toString().indexOf("class")-2);
									soldValues.append("soldValues", address+","+itemInfo+","+link);
								}
							}
						} catch (StringIndexOutOfBoundsException e) {
							System.out.println("Cleaning invalid data...");
							continue;
						}
					}
					jsonArray.put(soldValues);
					jsonArray.put(nearInfo);
					response.getWriter().write(jsonArray.toString());
				}			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(request.getParameter("oper").equals("getSchools")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/"+city+"-"+state+"/schools/";
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
					//Get Document object after parsing the html from given url.
					document = Jsoup.connect(url).get();
					JSONObject nearBySchools = new JSONObject();
					Elements schools = document.select(".school-cards-target");
					String[] arr = null;
					for(int i=0; i < schools.size(); i++) {
						String schoolItem = schools.get(i).text();
						arr = schoolItem.trim().split("/teacher ");
					}
					//System.out.println(Arrays.toString(arr));
					for(int i=0; i < arr.length; i++) {		
						String rank = arr[i].substring(0,arr[i].indexOf(" "));
						String name = arr[i].substring(arr[i].indexOf(" "),arr[i].indexOf("•")-1);
						String gradeLevel =  arr[i].substring(arr[i].indexOf("•")+1, arr[i].indexOf("•")+13);
						String number =arr[i].substring(arr[i].indexOf("•")+13,arr[i].lastIndexOf("students")-1);
						nearBySchools.append("schoolName",name);
						nearBySchools.append("schoolRank",rank);
						nearBySchools.append("schoolGrade",gradeLevel);
						nearBySchools.append("schoolNumStudents",number);
					}
					response.getWriter().write(nearBySchools.toString());
				}			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(request.getParameter("oper").equals("getSafety")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				//Get Document object after parsing the html from given url.
				String url = "https://www.neighborhoodscout.com/"+state.toLowerCase()+"/"+city.toLowerCase().replace(" ", "-")+"/crime/";
				Connection.Response responseSoup = Jsoup.connect(url)
				        .userAgent("Mozilla/5.0")
				        .timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if(statusCode == 200){
					try {
					//Get Document object after parsing the html from given url.
					document = Jsoup.connect(url).get();
					JSONObject safetyInfo = new JSONObject();
					Elements safestPlaces = document.select("div:contains(Safest "+city+" neighborhoods)");
					Elements crimeIndex = document.select("div:contains(Crime Index)");
					for(int i=2; i < crimeIndex.size(); i++) {
						String item = crimeIndex.get(i).text();
						if(item.contains("of U.S. Cities ")) {
							String index = item.substring(0,item.indexOf(")")+1);
							safetyInfo.append("crimeIndex", index);
						}
					}
					String[] arr = null;
					for(int i=2; i < safestPlaces.size(); i++) {
						String item = safestPlaces.get(i).text();
						//System.out.println(item);
						//arr = item.split("/");
						arr = item.substring(item.indexOf("neighborhoods")+14, item.length()).split(" /");
					}
					for(int i=0; i < arr.length; i++) {		
						String street = arr[i];
						safetyInfo.append("safestPlaces", street);
					}
						response.getWriter().write(safetyInfo.toString());
					} catch (NullPointerException e) {
						e.printStackTrace();
						response.getWriter().write("No safety information found!");
					}			
				}			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
