package captiom.server.controllers;

import com.google.gson.Gson;
import spark.Route;

interface Controller extends Route {

	Gson GSON = new Gson();
}
