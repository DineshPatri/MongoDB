package com.home.mongodb;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by DP052402 on 11/25/2018.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args){
        Spark.get("/",new Route(){// default port for spark is 4567 - http://localhost:4567
            @Override
            public Object handle(final Request request, final Response response){
                return "Hello World From Spark";
            }
        });
    }
}
