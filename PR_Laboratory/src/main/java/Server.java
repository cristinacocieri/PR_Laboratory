import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Server {
    static Logger logger = Logger.getLogger (Server.class.getName ( ));
    private ServerSocket serverSocket;
    public void start(int port) {
        logger.info ("TCP Server Started");
        try {
            serverSocket = new ServerSocket (port);
            while (true) {
                new TCPServer(serverSocket.accept ( )).start ( );
            }
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    private static class TCPServer extends Thread {
        static Logger logger = Logger.getLogger (TCPServer.class.getName ( ));
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public TCPServer(Socket socket) {
            this.clientSocket = socket;
            logger.info ("New Client connected to server");
        }

        public void run() {
            try {
                out = new PrintWriter (clientSocket.getOutputStream ( ), true);
                in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ( )));
                String inputLine;
                out.println ("Welcome To TCP Server");
                out.println ("\t MENU \n1. Enter ALL to see all collected data \n2.Enter which field's values you want to see \n3.Search which object contain key-value.Please follow the format: KEY IS VALUE \n4.Enter STOP to close connection \n ATTENTION: Don't forget the magic word\n");
                in.close ( );
                out.close ( );
                clientSocket.close ( );
                logger.info ("Session is Finished");
            } catch (IOException e) {
                e.printStackTrace ( );
            }

        }

        private boolean processInput(String input) {
            DataManager dataManager = new DataManager ( );
            ArrayList <String> words = new ArrayList <> (Arrays.asList (input.split ("\\s+")));
            switch (words.size ( )) {
                case 2: {
                    if (Pattern.compile (Pattern.quote ("stop"), Pattern.CASE_INSENSITIVE).matcher (input).find ( )) {
                        out.println ("Session is Finished");
                        return false;
                    } else if (Pattern.compile (Pattern.quote ("all"), Pattern.CASE_INSENSITIVE).matcher (input).find ( )) {
                        for (String str : Request.dataList) {
                            out.println (str);
                        }
                    }
                    break;
                }
                case 4: {
                    String key = "";
                    String value = "";
                    for (int i = 0; i < words.size ( ); i++) {
                        if (words.get (i).equals ("is")) {
                            key = words.get (i - 1);
                            value = words.get (i + 1);
                        }
                    }
                    List <String> results = dataManager.getJsonObjectByKeyAndValue (key, value, Request.dataList);
                    if (results.isEmpty ( )) {
                        out.println ("WARN: NO RESULTS FOUND \n");
                    } else {
                        for (String rs : results) {
                            out.println (rs);
                        }
                    }

                    break;
                }
                default:{
                    out.println ("ERROR: Such option doesn't exist");
                }
            }
            return true;
        }
    }
}
