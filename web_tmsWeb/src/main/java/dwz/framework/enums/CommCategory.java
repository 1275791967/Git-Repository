package dwz.framework.enums;

public enum CommCategory {
    tcp("TCP通信"), udp("UDP通信");

    private String name;

    private CommCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

}
