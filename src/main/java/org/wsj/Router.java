package org.wsj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.wsj.Route.Method;

public class Router {
	
	private static List<Route> routes = new ArrayList<>();
	
	private static void createRoute(String path, Method method, WebHandler callback) {
		Route route = routes.stream().filter(r -> r.getPath().equalsIgnoreCase(path)).findFirst().orElse(null);
		
		if(route != null) 
			route.putHandler(method, callback);
		else
			routes.add(new Route(path).putHandler(method, callback));
		
		
	}
	
	public static void get(String path, WebHandler callback) {
		createRoute(path, Route.Method.GET, callback);
	}
	
	public static void post(String path, WebHandler callback) {
		createRoute(path, Route.Method.POST, callback);
	}
	
	public static void put(String path, WebHandler callback) {
		createRoute(path, Route.Method.PUT, callback);
	}
	
	public static void delete(String path, WebHandler callback) {
		createRoute(path, Route.Method.DELETE, callback);
	}
	
	public static List<Route> getRoutes() {
		return Collections.unmodifiableList(routes);
	}
}
