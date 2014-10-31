package com.tengen;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/17/14
 * Time: 11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get(new Route("/"){
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World from Spark";
            }
        });
    }


}

