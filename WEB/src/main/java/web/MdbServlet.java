/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Definition of the two JMS destinations used by the quickstart
 * (one queue and one topic).
 */
@JMSDestinationDefinitions(
    value = {
        @JMSDestinationDefinition(
            name = "java:/queue/SampleQueue",
            interfaceName = "javax.jms.Queue",
            destinationName = "EJBExempleQueue"
        )
    }
)

/**
 * <p>
 * A simple servlet 3 as client that sends several messages to a queue or a topic.
 * </p>
 *
 * <p>
 * The servlet is registered and mapped to /HelloWorldMDBServletClient using the {@linkplain WebServlet
 * @HttpServlet}.
 * </p>
 *
 * @author Serge Pagop (spagop@redhat.com)
 *
 */
 // Autre manière de déclarer le chemin d'accès du servlet plutôt que d'utiliser le fichier web.xml
@WebServlet("/MdbServlet")
public class MdbServlet extends HttpServlet {

    private static final long serialVersionUID = -8314035702649252239L;

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/queue/SampleQueue")
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int rnd = (int) Math.floor(Math.random()*90)+10;
        Long rndAmount = (long) (Math.floor(Math.random()*1000));

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<h1>Création d'un compte avec l'identifiant:"+rnd+" et le solde:"+rndAmount+"</h1>");
        try {
            final Destination destination = queue;

            out.write("<p>Sending messages to <em>" + destination + "</em></p>");
            out.write("<h2>The following messages will be sent to the destination:</h2>");

            	String title = rnd+":"+rndAmount;
                context.createProducer().send(destination, title);

            out.write("<p><i>Go to your Home page to see the result of messages processing.</i></p>");
            out.println("<form action=\"/WEB\" method=\"get\">");
            out.println("      <div><input type=\"submit\" value=\"Home\"/></div>");
            out.println("    </form>");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
