package blomberg.tistou.androidnewsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by umyhblomti on 2016-11-09.
 */
public class JsonNewsConverter {

    public static ArrayList<NewsItem> JsonToNewsItems(String jsonString) {
        ArrayList newsItemsList = new ArrayList();

        try {
            JSONArray newsArray = new JSONArray(jsonString);
            int numberOfNews = newsArray.length();
            for (int newsIndex = 0; newsIndex < numberOfNews; newsIndex++){
                JSONObject newsObject = (JSONObject) newsArray.get(newsIndex);

                String newsTitle = newsObject.getString("news_title");
                String newsContent = newsObject.getString("news_content");
                URL newsImage = new URL(newsObject.getString("news_image"));

                NewsItem newsItem = new NewsItem(newsTitle, newsContent, newsImage);
                newsItemsList.add(newsItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return newsItemsList;
    }

}
