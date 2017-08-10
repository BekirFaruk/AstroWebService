package cz.kibo.astrology.service;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;
import static junit.framework.TestCase.assertEquals;

public class PlanetTest {
		
	@BeforeClass
	public static void beforeClass() {
		
		Application a = new Application();
		a.init();
		
		Spark.awaitInitialization();
	}
		 
	@AfterClass
	public static void afterClass() {
	    Spark.stop();
	}
		
	@Test
	public void corruptedJSON(){
		String reguestJSON = "}";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"A JSONObject text must begin with '{' at 1 [character 2 line 1]\"}", res.getBody());	
	}
	
	@Test
	public void emptyJSON(){
		String reguestJSON = "{}";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Property event not found.Property planets not found.\"}", res.getBody());	
	}
	
	@Test
	public void invalidJSON(){
		String reguestJSON = "{\"event\":\"20120710160000\"}";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Property planets not found.\"}", res.getBody());	
	}
	
	@Test
    public void planetsGeocentricTropical(){
		
		String reguestJSON = "{\"planets\":[\"Sun\", \"Moon\", \"Jupiter\"], \"event\":\"20120710160000\"}";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"planets\":{\"Jupiter\":[66.26910729110803,0.20225584046320172],\"Moon\":[14.237275893056413,12.143956568809566],\"Sun\":[108.78610653549424,0.953491685074115]}}", res.getBody());			
	}
	
	@Test
    public void planetsTopocentricTropical(){
		
		String reguestJSON = "{\"planets\":[\"Sun\"],\"event\":\"20120710160000\", \"topo\":[48.8559107, 16.0542676, 286] }";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"planets\":{\"Sun\":[108.784004228454,0.9583402237056993]}}", res.getBody());			
	}
	
	@Test
    public void planetsGeocentricSidereal(){
		
		String reguestJSON = "{\"planets\":[\"Sun\"], \"event\":\"20120710160000\",\"zodiac\":\"Krishnamurti\" }";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"planets\":{\"Sun\":[84.8462034992208,0.9535069767959855]}}", res.getBody());					
	}
	
	@Test
    public void planetsTopocentricSidereal(){
		
		String reguestJSON = "{\"planets\":[\"Sun\"], \"event\":\"20120710160000\",\"zodiac\":\"Krishnamurti\", \"topo\":[48.8559107, 16.0542676, 286] }";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"planets\":{\"Sun\":[84.84410119218057,0.958355515382292]}}", res.getBody());					
	}
	
	@Test
    public void badParamSidereal(){
		
		String reguestJSON = "{\"planets\":[\"Sun\"], \"event\":\"20120710160000\",\"zodiac\":\"Krishnamurti123\", \"topo\":[48.8559107, 16.0542676, 286] }";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Unknown sidereal mode: Krishnamurti123\"}", res.getBody());					
	}
	
	@Test
    public void badPlanetName(){
		
		String reguestJSON = "{\"planets\":[\"Suno\"], \"event\":\"20120710160000\"}";
		
		URLResponse res = new URLResponse("POST", "/planets", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Unknown planet name: Suno\"}", res.getBody());					
	}		
}