package activiti;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {  
	  
    private static final long serialVersionUID = 361866001729020143L;  
    //请假天数  
    private int id;  
    //请假人  
    private String name;  
    //请假原因  
    private String note;  
    //请假时间  
    private Date date;  
    public Date getDate() {  
        return date;  
    }  
    public void setDate() {  
        this.date = new Date();  
    }  
    public String getNote() {  
        return note;  
    }  
    public void setNote(String note) {  
        this.note = note;  
    }  
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
}  
