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
        /**
         * В mainJson есть практически вся необходимая информация, однако отсутствуют такие поля, как страна и регион
         * Поле страна получается из другого API и добавляется в mainJson
         * Поле регион получается из третьего API по полю index из mainJson
         * Если был сгенерирован невалидный index, генерируется новый пользователь, так как 
         * невалидный index генерируется крайне редко.
         * Затем mainJson в виде строки передается в десериализатор 
         */
        try {
            JSONObject mainJson = Unirest.get("https://randus.org/api.php").asJson().getBody().getObject();
            String country = Unirest.get("https://uinames.com/api/").asJson().getBody().getObject()
                    .getString("region");
            int index = mainJson.getInt("postcode");
            mainJson.put("country", country);
            JSONObject bufJson = Unirest.get("http://api.print-post.com/api/index/v2/")
                    .queryString("index", index)
                    .asJson().getBody().getObject();
            String region = bufJson.getString("region");
            if (region.equals(""))
                region = bufJson.getString("okrug");
            mainJson.put("region", region);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(MyRow.class, new JsonConventer())
                    .create();
            System.out.println(mainJson.toString());
            System.out.println(mainJson.getString("region"));

            return gson.fromJson(mainJson.toString(), MyRow.class);
        }
        catch (JSONException | UnirestException ex) {
            return callApi();
        }
    }
}
