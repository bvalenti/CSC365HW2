import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MyURLs {
    ArrayList<String> scrapedURLS;
    String path = "C:\\CSC365_BTree\\";
    String urlPath = "C:\\CSC365_BTree\\myURLs.ser";
    String URLS[] = new String[10];

    private static MyURLs instance;
    static {
        try {
            instance = new MyURLs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MyURLs() throws IOException {
        initURLs();
    }

    public static MyURLs getInstance() {
        return instance;
    }

    //===========================================
    public void initURLs() {
        scrapedURLS = new ArrayList<>();
        URLS[0] = "https://en.wikipedia.org/wiki/Oswego,_New_York";
        URLS[1] = "https://en.wikipedia.org/wiki/Japanese_language";
        URLS[2] = "https://en.wikipedia.org/wiki/Airplane";
        URLS[3] = "https://en.wikipedia.org/wiki/Earthquake";
        URLS[4] = "https://en.wikipedia.org/wiki/World_War_II";
        URLS[5] = "https://en.wikipedia.org/wiki/Leonardo_da_Vinci";
        URLS[6] = "https://en.wikipedia.org/wiki/Mathematics";
        URLS[7] = "https://en.wikipedia.org/wiki/Rock_climbing";
        URLS[8] = "https://en.wikipedia.org/wiki/History_of_the_United_States";
        URLS[9] = "https://en.wikipedia.org/wiki/Karate";
    }

    //===========================================
    public void scrape() throws IOException {
        for (int i = 0; i < URLS.length; i++) {
            System.out.println(URLS[i]);
            scrapedURLS.add(URLS[i]);
            scrapeForURLS(URLS[i]);
        }
    }

    //===========================================
    public void scrapeForURLS(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Document parent = doc;
        Elements links;
        int a;

        for (int i = 0; i < 99; i++) {
            links = doc.select("a[href]");
            doc = recurseScrape(parent, links);
        }
    }

    //===========================================
    public Document recurseScrape(Document parent, Elements links) {
        int a;
        Document doc;

        while (true) {
            if (links.size() >= 5) {
                a = ThreadLocalRandom.current().nextInt(1,links.size()-1);
            } else {
                links = parent.select("a[href]");
                a = ThreadLocalRandom.current().nextInt(1,links.size()-1);
            }
            if (links.get(a).attr("abs:href").length() < 135 && links.get(a).attr("abs:href").length() > 5
                    && links.get(a).attr("abs:href") != null
                    && !links.get(a).attr("abs:href").contains("twitter.com")
                    && !links.get(a).attr("abs:href").contains("facebook.com")
                    && !links.get(a).attr("abs:href").contains(".jpg")
                    && !links.get(a).attr("abs:href").contains(".MP3")
                    && !links.get(a).attr("abs:href").contains(".zip")
                    && checkAscII(links.get(a).attr("abs:href"))
                    && !scrapedURLS.contains(links.get(a).attr("abs:href"))) {
                try {
                    System.out.println(links.get(a).attr("abs:href"));
                    doc = Jsoup.connect(links.get(a).attr("abs:href")).get();
                    scrapedURLS.add(links.get(a).attr("abs:href"));
                    break;
                } catch (IOException e) {
                    return recurseScrape(parent, links);
                }
            }
        }
        return doc;
    }

    //===========================================
    public Boolean checkAscII(String toCheck) {
        CharsetDecoder decoder = Charset.forName("US-ASCII").newDecoder();
        try {
            CharBuffer buffer = decoder.decode(ByteBuffer.wrap(toCheck.getBytes()));
        } catch (CharacterCodingException e) {
            return false;
        }
        return true;
    }

    //===========================================
    public Path getFilePath(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
            url = url.replace("//", " ").replace("/", " ").replace(":", "").replace("<", "").replace(">", "").replace("*", "").replace("?", "");
        } else {
            url = url.replace("//", " ").replace("/", " ").replace(":", "").replace("<", "").replace(">", "").replace("*", "").replace("?", "");
        }
        url = url + ".ser";
        Path p = Paths.get(path, url);
        return p;
    }
}
