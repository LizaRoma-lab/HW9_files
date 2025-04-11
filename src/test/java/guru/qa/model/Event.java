package guru.qa.model;

public class Event {

private Integer id;
private String ns;
private String title;

private EventInner ids;

    public EventInner getIds() {
        return ids;
    }

    public void setIds(EventInner ids) {
        this.ids = ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
