package com.example.coffee_shop.boundary;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;

import javax.enterprise.context.ApplicationScoped;

@Health
@ApplicationScoped
public class HealthResource implements HealthCheck {

    private boolean isHealthy() {

        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://barista:9080/barista/resources/brews");
            Response response = target.request().get();
      
            int status = response.getStatus();
            if (status != 200) {
              return false;
            } else {
              System.err.println(status);
            }
            return true;
          } catch (Exception e) {
            e.printStackTrace();
            return false;
          }        
    }

    @Override
    public HealthCheckResponse call() {
        boolean up = isHealthy();

        return HealthCheckResponse.named("coffee-shop")
                                  .withData("barista", String.valueOf(up))
                                  .state(up).build();
    }
}
