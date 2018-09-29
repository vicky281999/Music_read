package spider.task_4;

public class Tracks {
    private String Tname,Tpic,Trating;

    public Tracks(String tname, String tpic, String trating) {
        this.Tname = tname;
        this.Tpic = tpic;
        this.Trating = trating;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }

    public String getTpic() {
        return Tpic;
    }

    public void setTpic(String tpic) {
        Tpic = tpic;
    }

    public String getTrating() {
        return Trating;
    }

    public void setTrating(String trating) {
        Trating = trating;
    }
}
