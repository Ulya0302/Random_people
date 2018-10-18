import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.varia.NullAppender;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiWorker {

    public ApiWorker() { BasicConfigurator.configure(new NullAppender());}

    public MyRow callApi() {
        try {
            JSONObject mainJson = Unirest.get("https://randus.org/api.php").asJson().getBody().getObject();
            String country = Unirest.get("https://uinames.com/api/").asJson().getBody().getObject()
                    .getString("region");
            int index = mainJson.getInt("postcode");
            mainJson.put("country", country);
            String region = Unirest.get("http://api.print-post.com/api/index/v2/").queryString("index", index)
                    .asJson().getBody().getObject().getString("region");
            mainJson.put("region", region);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(MyRow.class, new JsonConventer())
                    .create();
            return gson.fromJson(mainJson.toString(), MyRow.class);
        }
        catch (JSONException | UnirestException ex) {
            return callApi();
        }
    }
}
