package  it.polimi.ingswPSP35.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProvaJson {
    public static void main(String[] args) {

        String object,nome;
        int eta;

        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject.addProperty("Username", "Nome1");
        jsonObject.addProperty("Age", 22);
        object = jsonObject.toString();
        System.out.println(object);
        jsonObject2 = new JsonParser().parse(object).getAsJsonObject();
        nome = jsonObject2.get("Username").getAsString();
        eta = jsonObject2.get("Age").getAsInt();
        System.out.println(nome);
        System.out.println(eta);
    }
}