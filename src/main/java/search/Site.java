package search;
import org.jsoup.nodes.Document;

public class Site {
	Document asDoc;
	String[] site; //Using this allows us to get away easily with serializing
	public Site(String url, String description, String text) {
		this.site= new String[3];
		this.site[0]=url;
		this.site[1]=description;
		this.site[2]=text;

	}
	public Site(String url) {
		this.site= new String[3];

		this.site[0] = url;
		this.site[1]=null;
		this.site[2]=null;
	}
	public Site() {
		this.site= new String[3];
		this.site[0] = null;
		this.site[1]=null;
		this.site[2]=null;
	}
	// Possibly move generating these things into this.
	public void setText() {
		this.site[2] = asDoc.text();
	}

	public String getUrl() {
		return this.site[0];
	}

	public void setUrl(String url) {
		this.site[0] = url;
	}

	public String getDescription() {
		return this.site[1];
	}

	public void setDescription(String description) {
		this.site[1] = description;
	}

	public String getText() {
		return this.site[2];
	}

	public void setText(String text) {
		this.site[2] = text;
	}

	public Document getAsDoc() {
		return asDoc;
	}

	public void setAsDoc(Document asDoc) {
		this.asDoc = asDoc;
	}
	@Override 
	public boolean equals(Object site) {
		return(this.getUrl().equals(((Site)site).getUrl()));
	}
	@Override
	public int hashCode() {
		return this.getUrl().hashCode();
	}
}
