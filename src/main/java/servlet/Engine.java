package servlet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import weka.associations.Apriori;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.clusterers.EM;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

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
		System.out.println("START - Running Engine Servlet at " + dateFormat.format(date));
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("oper").equals("getVersion")) {
			String version, environment = null;
			try {
				ServletContext application = getServletConfig().getServletContext();
				InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF");
				Manifest manifest = new Manifest(inputStream);
				Attributes attr = manifest.getMainAttributes();
				version = attr.getValue("Manifest-Version");
				environment = attr.getValue("Environment");
				response.getWriter().write(environment + " " + version);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST - Engine at " + dateFormat.format(date));
		System.out.println("Running JSOUP and Zillow API....");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		/* find specific property details */
		if (request.getParameter("oper").equals("getHousesAll")) {
			response.setContentType("text/csv");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			try {
				// Get Document object after parsing the html from given url.
				Document document;
				String url = "https://www.zillow.com/" + city + "-" + state + "/for-sale/";
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					document = Jsoup.connect(url).get();
					Elements price = document.select(".zsg-photo-card-price:contains($)"); // Get
																							// price
					Elements address = document.select("span[itemprop]:contains(Charlotte NC)"); // Get
																									// address
					Elements ahref = document.select("a[href*=homedetails]");
					String outputResult = "";
					for (int i = 0; i < price.size() - 2; i++) {
						outputResult += address.get(i).text() + "," + price.get(i).text() + "\n";
						// System.out.println(address.get(i).text() + "," +
						// price.get(i).text());
					}
					for (int j = 0; j < ahref.size(); j++) {
						// System.out.println(ahref.get(j).toString().substring(9,ahref.get(j).toString().indexOf("class")-2));
					}
					// TODO: contact with another loop for ahref
					response.getWriter().write(outputResult);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			/* get chart for specific property */
		} else if (request.getParameter("oper").equals("findHouse")) {
			String address = request.getParameter("address");
			String replaceAddress = address.replace(' ', '+');
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				// Get Document object after parsing the html from given url.
				String url = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=" + APIKey + "&address="
						+ replaceAddress + "&citystatezip=" + city + " " + state + "";
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					// Document doc = Jsoup.parse(responseSoup.body(),url);
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
				// Get Document object after parsing the html from given url.
				String url = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=" + APIKey + "&address="
						+ replaceAddress + "&citystatezip=" + city + " " + state + "";
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					// Document doc = Jsoup.parse(responseSoup.body(),url);
					Document xmlDoc = Jsoup.parse(responseSoup.body(), url, Parser.xmlParser());
					String zpid = xmlDoc.select("zpid").first().text();
					String chartURL = "http://www.zillow.com/app?chartDuration=5years&chartType=partner&height=250&page=webservice%2FGetChart&service=chart&showPercent=true&width=450&zpid="
							+ zpid;
					response.getWriter().write(chartURL);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			/* get top recently sold houses in area via JSOUP */
		} else if (request.getParameter("oper").equals("getHousesSold")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				// Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/" + city + "-" + state + "/sold/";
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					// Get Document object after parsing the html from given
					// url.
					document = Jsoup.connect(url).get();
					JSONArray jsonArray = new JSONArray();
					JSONObject soldValues = new JSONObject();
					JSONObject houseInfo = new JSONObject();
					JSONObject nearInfo = new JSONObject();
					Elements nearByCities = document.select(".zsg-g_gutterless:matches([$-])");
					for (int i = 1; i < nearByCities.size(); i++) {
						String nearByCity = nearByCities.get(i).text().replace("-", "");
						nearInfo.append("nearByInfo", nearByCity);
					}
					Elements specs = document.select(".zsg-photo-card-spec"); // get
																				// specs
					for (int i = 0; i < specs.size(); i++) {
						if (specs.get(i).text().contains("SOLD")) {
							soldValues.append("soldValues", specs.get(i).text());
						} else if (specs.get(i).text().startsWith("Price")) {
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
		} else if (request.getParameter("oper").equals("getHousesSale")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				// Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/" + city + "-" + state + "/for-sale/";
				// System.out.println(url);
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					// Get Document object after parsing the html from given
					// url.
					document = Jsoup.connect(url).get();
					JSONArray jsonArray = new JSONArray();
					JSONObject soldValues = new JSONObject();
					JSONObject nearInfo = new JSONObject();
					Elements nearByCities = document.select(".zsg-g_gutterless:matches([$-])");
					for (int i = 1; i < nearByCities.size(); i++) {
						String nearByCity = nearByCities.get(i).text().replace("-", "");
						nearInfo.append("nearByInfo", nearByCity);
					}
					Elements price = document.select(".zsg-photo-card-price:contains($)"); // Get
																							// price
					Elements items = document.select(".zsg-aspect-ratio-content");
					Elements links = document.select(".zsg-aspect-ratio-content a[href*=homedetails]");
					for (int i = 0; i < items.size(); i++) {
						try {
							// System.out.println(items.get(i).text());
							String address = items.get(i).text().substring(0, items.get(i).text().indexOf(state) + 6);
							String patternAddress = items.get(i).text().substring(0,
									items.get(i).text().indexOf(state));
							String subAddress = patternAddress.replace("#", "").replace(" ", "-").replace("--", "-")
									.trim();
							String itemInfo = items.get(i).text().substring(items.get(i).text().indexOf("$"),
									items.get(i).text().lastIndexOf("·"));
							for (int j = 0; j < links.size(); j++) {
								if (links.get(j).toString().contains(subAddress)) {
									String link = "https://www.zillow.com" + links.get(j).toString().substring(9,
											links.get(j).toString().indexOf("class") - 2);
									soldValues.append("soldValues", address + "," + itemInfo + "," + link);
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
		} else if (request.getParameter("oper").equals("getSchools")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				// Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/" + city + "-" + state + "/schools/";
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					// Get Document object after parsing the html from given
					// url.
					document = Jsoup.connect(url).get();
					JSONObject nearBySchools = new JSONObject();
					Elements schools = document.select(".school-cards-target");
					String[] arr = null;
					for (int i = 0; i < schools.size(); i++) {
						String schoolItem = schools.get(i).text();
						arr = schoolItem.trim().split("/teacher ");
					}
					// System.out.println(Arrays.toString(arr));
					for (int i = 0; i < arr.length; i++) {
						String rank = arr[i].substring(0, arr[i].indexOf(" "));
						String name = arr[i].substring(arr[i].indexOf(" "), arr[i].indexOf("•") - 1);
						String gradeLevel = arr[i].substring(arr[i].indexOf("•") + 1, arr[i].indexOf("•") + 13);
						String number = arr[i].substring(arr[i].indexOf("•") + 13, arr[i].lastIndexOf("students") - 1);
						nearBySchools.append("schoolName", name);
						nearBySchools.append("schoolRank", rank);
						nearBySchools.append("schoolGrade", gradeLevel);
						nearBySchools.append("schoolNumStudents", number);
					}
					response.getWriter().write(nearBySchools.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (request.getParameter("oper").equals("getSafety")) {
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				// Get Document object after parsing the html from given url.
				String url = "https://www.neighborhoodscout.com/" + state.toLowerCase() + "/"
						+ city.toLowerCase().replace(" ", "-") + "/crime/";
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					try {
						// Get Document object after parsing the html from given
						// url.
						document = Jsoup.connect(url).get();
						JSONObject safetyInfo = new JSONObject();
						Elements safestPlaces = document.select("div:contains(Safest " + city + " neighborhoods)");
						Elements crimeIndex = document.select("div:contains(Crime Index)");
						for (int i = 2; i < crimeIndex.size(); i++) {
							String item = crimeIndex.get(i).text();
							if (item.contains("of U.S. Cities ")) {
								String index = item.substring(0, item.indexOf(")") + 1);
								safetyInfo.append("crimeIndex", index);
							}
						}
						String[] arr = null;
						for (int i = 2; i < safestPlaces.size(); i++) {
							String item = safestPlaces.get(i).text();
							// System.out.println(item);
							// arr = item.split("/");
							arr = item.substring(item.indexOf("neighborhoods") + 14, item.length()).split(" /");
						}
						for (int i = 0; i < arr.length; i++) {
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
		} else if (request.getParameter("oper").equals("buildStats")) {
			// get request
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			// write to temp csv file
			File csvFile = File.createTempFile("output", ".csv");
			PrintWriter pw = new PrintWriter(csvFile);
			StringBuilder sb = new StringBuilder();
			// build headers
			sb.append("Price");
			sb.append(",");
			sb.append("Beds");
			sb.append(",");
			sb.append("Baths");
			sb.append(",");
			sb.append("Sqft");
			sb.append("\n");
			PrintWriter outWrite = new PrintWriter(getServletContext().getRealPath("/") + "output.txt");
			for (int z = 0; z < 20; z++) {
				try {
					// Get Document object after parsing the html from given
					// url.
					String url = "https://www.zillow.com/" + city + "-" + state + "/for-sale/" + z + "_p/";
					// System.out.println(url);
					Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
					int statusCode = responseSoup.statusCode();
					if (statusCode == 200) {
						// Get Document object after parsing the html from given
						// url.
						document = Jsoup.connect(url).get();

						Elements price = document.select(".zsg-photo-card-price:contains($)"); // Get
																								// price
						Elements items = document.select(".zsg-aspect-ratio-content");
						Elements links = document.select(".zsg-aspect-ratio-content a[href*=homedetails]");
						double[] lowestPrices = new double[5];
						ArrayList<String> prices = new ArrayList<>();
						ArrayList<String> addresses = new ArrayList<>();
						JSONObject mapPriceAddress = new JSONObject();
						for (int i = 0; i < items.size(); i++) {
							try {
								// System.out.println(items.get(i).text());
								String address = items.get(i).text().substring(0,
										items.get(i).text().indexOf(state) + 6);
								String patternAddress = items.get(i).text().substring(0,
										items.get(i).text().indexOf(state));
								String subAddress = patternAddress.replace("#", "").replace(" ", "-").replace("--", "-")
										.trim();
								String itemInfo = items.get(i).text().substring(items.get(i).text().indexOf("$"),
										items.get(i).text().lastIndexOf("·"));
								String itemPrice = itemInfo.substring(1, itemInfo.indexOf("bd") - 2).replaceAll(",", "")
										.replaceAll("\\+", "").replaceAll("·", "");
								String beds = itemInfo.substring(itemInfo.indexOf("bd") - 2, itemInfo.indexOf("bd"));
								String baths = itemInfo.substring(itemInfo.indexOf("ba") - 2, itemInfo.indexOf("ba"));
								String sqft = itemInfo
										.substring(itemInfo.indexOf("sqft") - 6, itemInfo.indexOf("sqft") - 1)
										.replaceAll(",", "");
								for (int j = 0; j < links.size(); j++) {
									if (links.get(j).toString().contains(subAddress)) {
										try {
											double doublePrice = Double.valueOf(itemPrice.replaceAll("K", "000"));
											sb.append(doublePrice);
											prices.add(doublePrice + "");
											addresses.add(address + "," + doublePrice);
										} catch (NumberFormatException e) {
											System.out.println("Cleaning invalid data...");
											sb.append(190000); // just replace
																// with avg
																// value of
																// homes in U.S.
										}
										sb.append(",");
										try {
											sb.append(Double.valueOf(beds));
										} catch (NumberFormatException e) {
											System.out.println("Cleaning invalid data...");
											sb.append(3); // just replace with
															// avg value of beds
															// in U.S.
										}
										sb.append(",");
										try {
											sb.append(Double.valueOf(baths));
										} catch (NumberFormatException e) {
											System.out.println("Cleaning invalid data...");
											sb.append(2); // just replace with
															// avg value of
															// baths in U.S.
										}
										sb.append(",");
										try {
											sb.append(Double.valueOf(sqft.replaceAll("·", "")));
										} catch (NumberFormatException e) {
											System.out.println("Cleaning invalid data...");
											sb.append(2000.00); // just replace
																// with avg sqft
																// of homes in
																// U.S.
										}
										sb.append('\n');
									}
								}
							} catch (StringIndexOutOfBoundsException e) {
								System.out.println("Cleaning invalid data...");
								continue;
							}
						}
						try {
							String[] tempArray = new String[prices.size()];
							prices.toArray(tempArray);
							Arrays.sort(tempArray);
							for (int i = 0; i < lowestPrices.length; i++) {
								lowestPrices[i] = Double.valueOf(tempArray[i]);
							}
	
							for (int j = 0; j < addresses.size(); j++) {
								for (int i = 0; i < lowestPrices.length; i++) {
									if (lowestPrices[i] == Double.valueOf((addresses.get(j)
											.substring(addresses.get(j).indexOf(",") + 1, addresses.get(j).length())))) {
										// System.out.println(lowestPrices[i]);
										try {
											mapPriceAddress.append(
													addresses.get(j).substring(0, addresses.get(j).indexOf(",")),
													lowestPrices[i]);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
							}
							System.out.println(mapPriceAddress.toString());
						} catch(ArrayIndexOutOfBoundsException e) {
							System.out.println("Finding lowest homes invalid data cleaning....");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// System.out.println(sb.toString());
			pw.write(sb.toString());
			pw.close();

			JSONObject responseOut = new JSONObject();
			// load CSV file (needs to have numeric data ONLY, i.g. sold value,
			// beds, baths attributes)
			CSVLoader loader = new CSVLoader();
			loader.setSource(csvFile);
			Instances train = loader.getDataSet();

			// split the original dataset from CSV temp file into train/test set
			int trainSize = (int) Math.round(train.numInstances() * 50 / 100);
			int testSize = train.numInstances() - trainSize;
			// trainset first half
			Instances trainSet = new Instances(train, 0, trainSize);
			// test set second half
			Instances testSet = new Instances(train, trainSize, testSize);

			// get user request/inputs for beds, baths, sq ft
			double beds = Double.valueOf(request.getParameter("beds"));
			double baths = Double.valueOf(request.getParameter("baths"));
			double sqft = Double.valueOf(request.getParameter("sqft"));

			try {
				EM cluster = buildCluster(trainSet);
				responseOut.append("modelStats", cluster.toString());
				outWrite.println(cluster.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				// start Linear Regression
				trainSet.setClassIndex(0);
				testSet.setClassIndex(0);
				// build regression
				LinearRegression lr = new LinearRegression();
				lr.buildClassifier(trainSet);
				responseOut.append("modelStats", lr.toString());
				// create attributes
				FastVector atts = new FastVector(3);
				// make class attribute
				Attribute classIndex = new Attribute("price");
				atts.addElement(classIndex);
				atts.addElement(new Attribute("beds"));
				atts.addElement(new Attribute("baths"));
				atts.addElement(new Attribute("sqft"));
				// create instances for attributes
				Instances instances = new Instances("My Instances", atts, 0);
				instances.setClass(classIndex);
				// create single instance
				Instance inst = new Instance(4);
				inst.setDataset(instances);
				inst.setValue(1, beds);
				inst.setValue(2, baths);
				inst.setValue(3, sqft);
				// add single instance to instances collection
				instances.add(inst);
				// classify instance here
				int estimateValue = Integer.valueOf((int) lr.classifyInstance(inst));
				System.out.println(lr.toString());
				responseOut.append("modelStats", String.valueOf(estimateValue));
				// print out info to log file
				outWrite.println("");
				outWrite.println("===================================");
				outWrite.println(lr.toString());
				outWrite.println("Estimate Price: " + estimateValue);
				outWrite.println("");
				Evaluation ev = new Evaluation(trainSet);
				ev.evaluateModel(lr, testSet);
				System.out.println(ev.toSummaryString());
				outWrite.println("===================================");
				outWrite.println("Evaluation Model");
				outWrite.println("");
				outWrite.println(ev.toSummaryString());
				outWrite.println("");
				outWrite.close();
				response.getWriter().write(responseOut.toString());

			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("Error! Wrong input specified or server error!");
			}

		} else if (request.getParameter("oper").equals("getLowestHomes")) {
			// get request
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			Document document;
			try {
				// Get Document object after parsing the html from given url.
				String url = "https://www.zillow.com/" + city + "-" + state + "/for-sale/";
				// System.out.println(url);
				Connection.Response responseSoup = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).execute();
				int statusCode = responseSoup.statusCode();
				if (statusCode == 200) {
					// Get Document object after parsing the html from given
					// url.
					document = Jsoup.connect(url).get();

					Elements price = document.select(".zsg-photo-card-price:contains($)"); // Get
																							// price
					Elements items = document.select(".zsg-aspect-ratio-content");
					Elements links = document.select(".zsg-aspect-ratio-content a[href*=homedetails]");
					double[] lowestPrices = new double[5];
					ArrayList<String> prices = new ArrayList<>();
					ArrayList<String> addresses = new ArrayList<>();
					JSONObject mapPriceAddress = new JSONObject();
					for (int i = 0; i < items.size(); i++) {
						try {
							// System.out.println(items.get(i).text());
							String address = items.get(i).text().substring(0, items.get(i).text().indexOf(state) + 6);
							String patternAddress = items.get(i).text().substring(0,
									items.get(i).text().indexOf(state));
							String subAddress = patternAddress.replace("#", "").replace(" ", "-").replace("--", "-")
									.trim();
							String itemInfo = items.get(i).text().substring(items.get(i).text().indexOf("$"),
									items.get(i).text().lastIndexOf("·"));
							String itemPrice = itemInfo.substring(1, itemInfo.indexOf("bd") - 2).replaceAll(",", "")
									.replaceAll("\\+", "").replaceAll("·", "");
							String beds = itemInfo.substring(itemInfo.indexOf("bd") - 2, itemInfo.indexOf("bd"));
							String baths = itemInfo.substring(itemInfo.indexOf("ba") - 2, itemInfo.indexOf("ba"));
							String sqft = itemInfo.substring(itemInfo.indexOf("sqft") - 6, itemInfo.indexOf("sqft") - 1)
									.replaceAll(",", "");
							for (int j = 0; j < links.size(); j++) {
								if (links.get(j).toString().contains(subAddress)) {
									try {
										double doublePrice = Double.valueOf(itemPrice.replaceAll("K", "000"));
										prices.add(doublePrice + "");
										addresses.add(address + "," + doublePrice);
									} catch (NumberFormatException e) {
										System.out.println("Cleaning invalid data...");
									}
								}
							}
						} catch (StringIndexOutOfBoundsException e) {
							System.out.println("Cleaning invalid data...");
							continue;
						}
					}
					String[] tempArray = new String[prices.size()];
					prices.toArray(tempArray);
					Arrays.sort(tempArray);
					for (int i = 0; i < lowestPrices.length; i++) {
						lowestPrices[i] = Double.valueOf(tempArray[i]);
					}

					for (int j = 0; j < addresses.size(); j++) {
						for (int i = 0; i < lowestPrices.length; i++) {
							if (lowestPrices[i] == Double.valueOf((addresses.get(j)
									.substring(addresses.get(j).indexOf(",") + 1, addresses.get(j).length())))) {
								// System.out.println(lowestPrices[i]);
								try {
									mapPriceAddress.append(addresses.get(j).substring(0, addresses.get(j).indexOf(",")),
											lowestPrices[i]);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					System.out.println(mapPriceAddress.toString());
					response.getWriter().write(mapPriceAddress.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Builds basic EM cluster
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static EM buildCluster(Instances data) throws Exception {
		String[] options = new String[2];
		options[0] = "-I";
		options[1] = "100";
		EM clusterer = new EM();
		clusterer.setOptions(options);
		clusterer.buildClusterer(data);
		return clusterer;
	}

}
