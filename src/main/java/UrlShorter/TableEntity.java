package UrlShorter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

class NewUrl {
    private String Url;

    public NewUrl(String Url) {
        this.Url = Url;
    }

    public String getUrl() {
        return Url;
    }
    public void setUrl(String name) {
        this.Url = Url;
    }
}

@Entity
public class TableEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String url;

    protected TableEntity() {}

    public TableEntity(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, url='%s']", id, url);
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
