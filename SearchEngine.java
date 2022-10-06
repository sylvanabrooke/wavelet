import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String temp ="";
            for(int i = 0; i<list.size(); i++){
                temp += list.get(i) + ", ";
            }
            return temp;
        } else if (url.getPath().contains("/add")){
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            list.add(parameters[1]);
            String temp ="";
            for(int i = 0; i<list.size(); i++){
                temp += list.get(i) + ", ";
            }
            return parameters[1] + " added! List is now "+ temp;
        }
        else if(url.getPath().contains("/search")){
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            String temp ="";
            for(int i = 0; i<list.size(); i++){
                if(list.get(i).contains(parameters[1])){
                    temp += list.get(i) + "   ";
                }
            }
            return temp;
        }
        return "404 Not Found!";
    }
}