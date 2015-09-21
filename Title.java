import java.net.*;
import java.util.*;
import java.io.*;
public class Title {

	private ArrayList<String> titles,authors,stories,storyLink;
	private String region,story;
	private String link="http://www.cbc.ca/cmlink/rss-canada-";
	
	public Title(String region) {
		this.region=region;
		link+=region;
		titles=new ArrayList<String>();
		authors=new ArrayList<String>();
		stories=new ArrayList<String>();
		storyLink=new ArrayList<String>();
		readRSS(link);
	}
	
	public void readRSS(String urlAddress){
		try{

			URL rssUrl=new URL(urlAddress);
			BufferedReader in=new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			String line;
			
			while((line=in.readLine())!=null){
				if(line.contains("<title><![CDATA[")){
					int firstPos=line.indexOf("<title><![CDATA[");
					String temp=line.substring(firstPos);
					temp=temp.replace("<title><![CDATA[", "");
					int lastPos=temp.indexOf("]]></title>");
					temp=temp.substring(0,lastPos);
					titles.add(temp);
				}
				if(line.contains("<p>")){
					int firstPos=line.indexOf("<p>");
					String temp=line.substring(firstPos);
					temp=temp.replace("<p>", "");
					int lastPos=temp.indexOf("</p>");
					temp=temp.substring(0,lastPos);
					stories.add(temp);
				}
				if(line.contains("<author>")){
					int firstPos=line.indexOf("<author>");
					String temp=line.substring(firstPos);
					temp=temp.replace("<author>", "");
					int lastPos=temp.indexOf("</author>");
					temp=temp.substring(0,lastPos);
					authors.add(temp);
				}
				if(line.contains("<link>")){
					int firstPos=line.indexOf("<link>");
					String temp=line.substring(firstPos);
					temp=temp.replace("<link>", "");
					int lastPos=temp.indexOf("</link>");
					temp=temp.substring(0,lastPos);
					storyLink.add(temp);
				}
			}
			titles.remove(0);
			storyLink.remove(0);
			storyLink.remove(0);
			
			in.close();
		}catch(MalformedURLException murl){
			System.out.println("Malformed URL");
		}catch(IOException ioe){
			System.out.println("Something went wrong reading the contents");
		}
	}

	public ArrayList<String> getTitles(){
		return titles;
	}
	
	public ArrayList<String> getAuthors(){
		return authors;
	}
	public ArrayList<String> getStories(){
		return stories;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}
	
	public ArrayList<String> getStoryLink(){
		return storyLink;
	}

}
