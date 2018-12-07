package instruments;

import com.google.gson.*;
import table.MyRow;

import java.lang.reflect.Type;

public class JsonConventer implements JsonDeserializer<MyRow> {
    public MyRow deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        String name = jsonElement.getAsJsonObject().get("fname").getAsString().replace("\"", "");
        String surname = jsonElement.getAsJsonObject().get("lname").getAsString().replace("\"", "");
        String midname = jsonElement.getAsJsonObject().get("patronymic").getAsString().replace("\"", "");
        String sex = jsonElement.getAsJsonObject().get("gender").getAsString().replace("\"", "").equals("m")?"М":"Ж";
        String country = jsonElement.getAsJsonObject().get("country").getAsString().replace("\"", "");
        String city = jsonElement.getAsJsonObject().get("city").getAsString().replace("\"", "");
        String street = jsonElement.getAsJsonObject().get("street").getAsString().replace("\"", "");
        String region = jsonElement.getAsJsonObject().get("region").getAsString().replace("\"", "");
        int index = jsonElement.getAsJsonObject().get("postcode").getAsInt();
        return new MyRow(name, surname, midname,
                sex, country, region, city, street, index);
    }
}
