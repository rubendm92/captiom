package captiom.server.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Route;

interface Controller extends Route {

	Gson GSON = new Gson();

	static JsonObject bodyFrom(Request request) {
		return GSON.fromJson(request.body(), JsonElement.class).getAsJsonObject();
	}
}
