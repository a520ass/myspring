package com.hf.undertow;



/**
 * Created by krt on 2017/5/13.
 */
public class UndertowServer {

    public static final String MYAPP = "/myspring";

    public static void main(final String[] args) {
      /*  try {

            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(UndertowServer.class.getClassLoader())
                    .setContextPath(MYAPP)
                    .setDeploymentName("myspring.war");

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(MYAPP))
                    .addPrefixPath(MYAPP, servletHandler);
            Undertow server = Undertow.builder()
                    .addHttpListener(8081, "localhost")
                    .setHandler(path)
                    .build();
            server.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }*/
    }

}
